package ar.edu.itba.pod.server.p4e2;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventTicketServerMain {

  private static Logger logger = LoggerFactory.getLogger(EventTicketServerMain.class);

  public static void main(final String[] args) {
    logger.info("ETServer Server starting...");

    Registry registry = null;
    try {
      registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    } catch (final RemoteException exception) {
      logger.error("ETServer Registry could not be started.", exception);
      System.exit(1);
    }

    EventTicketService service = new EventTicketService();
    Remote remoteService = null;
    try {
      remoteService = UnicastRemoteObject.exportObject(service, 0);
    } catch (final RemoteException exception) {
      logger.error("ETServer Server could not be started.", exception);
      System.exit(1);
    }

    try {
      registry.rebind("tickets", remoteService);
    } catch (final RemoteException exception) {
      logger.error("ETServer Service could not be started.", exception);
      System.exit(1);
    }

    logger.info("ETServer Server started.");
  }
}
