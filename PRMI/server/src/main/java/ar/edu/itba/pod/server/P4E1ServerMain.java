package ar.edu.itba.pod.server;

import ar.edu.itba.pod.Book;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class P4E1ServerMain {

  private static Logger logger = LoggerFactory.getLogger(P4E1ServerMain.class);

  public static void main(final String[] args) {
    logger.info("P4E1 Server starting...");

    Registry registry = null;
    try {
      registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
    } catch (final RemoteException exception) {
      logger.error("P4E1 Registry could not be started.", exception);
      System.exit(1);
    }

    final Map<Book, Integer> bookHoldings = new HashMap<>();
    bookHoldings.put(
        new Book("978-0345533487", "A Knight of the Seven Kingdoms", "Martin George R. R.",
            LocalDate.parse("2015-10-06")), 3);
    bookHoldings.put(new Book("978-0441294671", "God Emperor of Dune", "Herbert Frank",
        LocalDate.parse("1987-06-15")), 4);
    bookHoldings.put(
        new Book("978-0451210845", "The Gunslinger", "King Stephen", LocalDate.parse("2003-07-01")),
        2);

    final P4E1ServiceImpl service = new P4E1ServiceImpl(bookHoldings);
    Remote remoteService = null;
    try {
      remoteService = UnicastRemoteObject.exportObject(service, 0);
    } catch (final RemoteException exception) {
      logger.error("P4E1 Server could not be started.", exception);
      System.exit(1);
    }

    try {
      registry.rebind("library", remoteService);
    } catch (final RemoteException exception) {
      logger.error("P4E1 Service could not be started.", exception);
      System.exit(1);
    }

    logger.info("P4E1 Server started.");
  }
}
