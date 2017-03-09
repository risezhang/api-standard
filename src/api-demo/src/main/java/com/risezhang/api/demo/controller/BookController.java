package com.risezhang.api.demo.controller;

import com.risezhang.api.demo.domain.Book;
import com.risezhang.api.demo.exception.BookNotFound;
import com.risezhang.api.demo.service.BookService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by liang on 09/03/2017.
 */
@RestController
@RequestMapping(value="/v1/books")
public class BookController {

    private static Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    BookService bookService;

    @ApiOperation(value="获取书籍列表", notes="")
    @RequestMapping(value="/", method= RequestMethod.GET)
    public List<Book> getBookList() {
        logger.info("fetch all books");
        return bookService.all();
    }

    @ApiOperation(value="创建书籍", notes="根据Book对象创建书籍")
    @ApiImplicitParam(name = "book", value = "书籍实体对象Book", required = true, dataType = "Book")
    @RequestMapping(value="/", method=RequestMethod.POST,produces = "application/json")
    public Book createBook(@RequestBody Book book) {
        this.bookService.save(book);
        return book;
    }

    @ApiOperation(value="获取书籍详细信息", notes="根据URL中的bookId来获取书籍详细信息")
    @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public Book getBook(@PathVariable Long bookId) {
        return bookService.findById(bookId);
    }

    @ApiOperation(value="更新指定书籍的详细信息", notes="根据URL中的bookId来更新书籍的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "Long", paramType="path"),
            @ApiImplicitParam(name = "book", value = "书籍内容", required = true, dataType = "Book")
    })
    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public Book putBook(@PathVariable Long id, @RequestBody Book book) {
        Book b = this.bookService.findById(id);
        if (b == null) {
            throw new BookNotFound();
        }
        b.setTitle(book.getTitle());
        b.setAuthor(book.getAuthor());
        this.bookService.save(book);
        return book;
    }

    @ApiOperation(value="删除书籍", notes="根据URL中的bookId来指定删除书籍")
    @ApiImplicitParam(name = "id", value = "书籍ID", required = true, dataType = "Long")
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE)
    public String deleteBook(@PathVariable Long bookId) {
        this.bookService.delete(bookId);
        return "success";
    }
}
