package com.risezhang.api.demo.service.impl;

import com.risezhang.api.demo.domain.Book;
import com.risezhang.api.demo.repository.BookRepository;
import com.risezhang.api.demo.service.BookService;
import com.risezhang.api.demo.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by liang on 09/03/2017.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void save(Book book) {
        this.bookRepository.save(book);
    }

    @Override
    public List<Book> all() {
        return Collections.makeCollection(this.bookRepository.findAll());
    }

    @Override
    public void delete(Long bookId) {
        this.bookRepository.delete(bookId);
    }

    @Override
    public Book findById(Long bookId) {
        return bookRepository.findOne(bookId);
    }
}
