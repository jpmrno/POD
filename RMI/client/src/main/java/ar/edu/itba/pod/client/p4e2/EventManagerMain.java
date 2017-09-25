package ar.edu.itba.pod.client.p4e2;

import ar.edu.itba.pod.p4e2.EventService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventManagerMain {

  private static Logger logger = LoggerFactory.getLogger(EventManagerMain.class);

  public static void main(final String[] args) {

    logger.info("P4E2 Manager starting...");

    EventService service = null;
    try {
      service = (EventService) Naming.lookup("//localhost:1099/tickets");
    } catch (final NotBoundException | MalformedURLException | RemoteException exception) {
      logger.error("P4E2 Manager failed to get service", exception);
      System.exit(1);
    }
    logger.info("P4E2 Manager started.");

    try {
//      service.addEvent("Led", 2, 5, 4);
      service.cancelEvent("Led");
    } catch (final RemoteException exception) {
      logger.error("P4E2 Manager connection error", exception);
      System.exit(1);
    }

  }
}
