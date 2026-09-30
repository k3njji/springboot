package org.kentut.postgretutorial.dao;

import org.kentut.postgretutorial.domain.Author;
import org.kentut.postgretutorial.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);

    Optional<Book> findOne(String s);

    List<Book> find();

    void update(String isbn, Book book);

}
