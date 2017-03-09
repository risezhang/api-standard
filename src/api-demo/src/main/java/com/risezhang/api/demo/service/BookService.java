package com.risezhang.api.demo.service;

import com.risezhang.api.demo.domain.Book;

import java.util.List;

/**
 * Created by liang on 09/03/2017.
 */
public interface BookService {

    /**
     * create or update a book
     * @param book
     */
    void save(Book book);

    /**
     * Fetch all books
     * @return all books
     */
    List<Book> all();

    /**
     * Delete a book with a specific ID
     * @param bookId
     */
    void delete(Long bookId);

    /**
     * Find a book with a specific ID
     * @param bookId
     * @return a book
     */
    Book findById(Long bookId);
}
