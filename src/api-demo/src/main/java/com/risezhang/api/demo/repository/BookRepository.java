package com.risezhang.api.demo.repository;

import com.risezhang.api.demo.domain.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by liang on 09/03/2017.
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Long> {
}
