package ar.edu.itba.pod.client.p4e2;

import ar.edu.itba.pod.p4e2.TicketNotification;
import ar.edu.itba.pod.p4e2.TicketService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventAttendantMain {

  private static Logger logger = LoggerFactory.getLogger(EventAttendantMain.class);

  public static void main(final String[] args) {

    logger.info("P4E2 Attendant starting...");

    TicketService service = null;
    try {
      service = (TicketService) Naming.lookup("//localhost:1099/tickets");
    } catch (final NotBoundException | MalformedURLException | RemoteException exception) {
      logger.error("P4E2 Attendant failed to get service", exception);
      System.exit(1);
    }

    final TicketNotification handler = new TicketNotification() {
      @Override
      public void vipConfirmed(String eventName, String ticket) throws RemoteException {
        System.out.println("---> VIP Entrance confirmed " + ticket);
      }

      @Override
      public void confirmed(String eventName, String ticket) throws RemoteException {
        System.out.println("---> BASIC Entrance confirmed " + ticket);
      }

      @Override
      public void reserved(String eventName) throws RemoteException {
        System.out.println("---> Entrance reserved");
      }

      @Override
      public void soldOut(String eventName) throws RemoteException {
        System.out.println("---> Event sold out");
      }

      @Override
      public void cancelled(String eventName) throws RemoteException {
        System.out.println("---> Event cancelled");
      }
    };
    try {
      UnicastRemoteObject.exportObject(handler, 0);
    } catch (final RemoteException exception) {
      logger.error("P4E2 Attendant notification creation error", exception);
      System.exit(1);
    }
    logger.info("P4E2 Attendant started.");

    try {
      service.request("Led", handler);
    } catch (final RemoteException exception) {
      logger.error("P4E2 Attendant connection error", exception);
      System.exit(1);
    }
  }
}
