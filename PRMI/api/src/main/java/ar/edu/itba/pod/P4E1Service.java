package ar.edu.itba.pod;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface P4E1Service extends Remote {

  List<Book> listBooks() throws RemoteException;

  Book lendBook(final String isbn) throws RemoteException;

  void returnBook(final Book book) throws RemoteException;
}
