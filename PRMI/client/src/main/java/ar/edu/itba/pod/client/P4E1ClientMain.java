package ar.edu.itba.pod.client;

import ar.edu.itba.pod.Book;
import ar.edu.itba.pod.P4E1Service;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class P4E1ClientMain {

  private static Logger logger = LoggerFactory.getLogger(P4E1ClientMain.class);

  public static void main(final String[] args) {
    logger.info("P4E1 Client starting...");

    P4E1Service service = null;
    try {
      service = (P4E1Service) Naming.lookup("//localhost:1099/library");
    } catch (final NotBoundException | MalformedURLException | RemoteException exception) {
      logger.error("P4E1 Client failed to get service", exception);
      System.exit(1);
    }

    try {
      System.out.println(service.lendBook("978-0345533480"));
    } catch (final Exception exception) {
      // Ignore
    }

    try {
//      System.out.println(service.listBooks());
      System.out.println(service.lendBook("978-0345533487"));
      System.out.println(service.lendBook("978-0345533487"));
      System.out.println(service.lendBook("978-0345533487"));
      final Book book = service.lendBook("978-0345533487");
      System.out.println(book);
//      service.returnBook(book);
    } catch (final RemoteException exception) {
      logger.error("P4E1 Client connection error", exception);
      System.exit(1);
    }

    logger.info("P4E1 Client started.");
  }
}
