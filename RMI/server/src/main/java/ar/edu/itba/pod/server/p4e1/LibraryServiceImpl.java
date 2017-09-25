package ar.edu.itba.pod.server.p4e1;

import ar.edu.itba.pod.p4e1.Book;
import ar.edu.itba.pod.p4e1.LibraryService;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryServiceImpl implements LibraryService {

  private final List<Book> booksList;
  private final Map<Book, Integer> booksHoldings;

  public LibraryServiceImpl(final List<Book> booksList) {
    this.booksList = booksList.stream().distinct().collect(Collectors.toList());
    this.booksHoldings = new HashMap<>();

    for (final Book book : booksList) {
      booksHoldings.compute(book, (b, n) -> n == null ? 1 : n + 1);
    }
  }

  public LibraryServiceImpl(final Map<Book, Integer> booksHoldings) {
    this.booksList = new ArrayList<>(booksHoldings.keySet());
    this.booksHoldings = new HashMap<>(booksHoldings);
  }

  @Override
  public List<Book> listBooks() throws RemoteException {
    return booksList;
  }

  @Override
  public Book lendBook(final String isbn) throws RemoteException {
    final Optional<Book> book = booksHoldings.keySet().stream()
        .filter(b -> b.getIsbn().equals(isbn))
        .findFirst();

    if (!book.isPresent()) {
      throw new IllegalArgumentException("Book does not exist");
    }

    Optional<Integer> holdings = book.map(booksHoldings::get).filter(h -> h > 0);
    synchronized (booksHoldings) {
      holdings.ifPresent(h -> booksHoldings.compute(book.get(), (b, n) -> n - 1));
    }

    if (holdings.isPresent()) {
      return book.orElse(null);
    } else {
      return null;
    }
  }

  @Override
  public void returnBook(final Book book) throws RemoteException {
    synchronized (booksHoldings) {
      booksHoldings.computeIfPresent(book, (b, n) -> n + 1);
    }
  }
}
