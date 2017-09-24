package ar.edu.itba.pod;

import java.io.Serializable;
import java.time.LocalDate;

public class Book implements Serializable {

  private final String isbn;
  private final String title;
  private final String author;
  private final LocalDate publicationDate;

  public Book(final String isbn, final String title, String author,
      final LocalDate publicationDate) {
    this.isbn = isbn;
    this.title = title;
    this.publicationDate = publicationDate;
    this.author = author;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getTitle() {
    return title;
  }

  public String getAuthor() {
    return author;
  }

  public LocalDate getPublicationDate() {
    return publicationDate;
  }

  @Override
  public String toString() {
    return "Book{" +
        "isbn='" + isbn + '\'' +
        ", title='" + title + '\'' +
        ", author='" + author + '\'' +
        ", publicationDate=" + publicationDate +
        '}';
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof Book)) {
      return false;
    }

    final Book book = (Book) o;

    return isbn.equals(book.isbn);
  }

  @Override
  public int hashCode() {
    return isbn.hashCode();
  }
}
