package ar.edu.itba.pod.p4e1;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryService extends Remote {

  List<Book> listBooks() throws RemoteException;

  Book lendBook(final String isbn) throws RemoteException;

  void returnBook(final Book book) throws RemoteException;
}
