package ar.edu.itba.pod.client.p4e1;

import ar.edu.itba.pod.p4e1.Book;
import ar.edu.itba.pod.p4e1.LibraryService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryClientMain {

  private static Logger logger = LoggerFactory.getLogger(LibraryClientMain.class);

  public static void main(final String[] args) {
    logger.info("P4E1 Client starting...");

    LibraryService service = null;
    try {
      service = (LibraryService) Naming.lookup("//localhost:1099/library");
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
