package com.upvote.aismpro.controller;

import com.upvote.aismpro.entity.BookEntity;
import com.upvote.aismpro.repository.BookEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class BookController {

    @Autowired
    private BookEntityRepository bookRepository;

    @PostMapping("/book")
    public BookEntity createBook(@RequestBody BookEntity bookEntity) {
        BookEntity created = bookRepository.save(bookEntity);

        return created;
    }

    @GetMapping("/book")
    public List<BookEntity> listAllBooks() {
        List<BookEntity> list = new ArrayList<>();
        Iterable<BookEntity> iterable = bookRepository.findAll();

        for (BookEntity bookEntity : iterable) {
            bookEntity.display();

            list.add(bookEntity);
        }

        return list;
    }

    @GetMapping("/book/{name}")
    public BookEntity selectBook(@PathVariable("name") String name) {
        Optional<BookEntity> optBook = bookRepository.findByName(name);
        BookEntity book = new BookEntity();

        if (optBook.isPresent()) {
            book = optBook.get();
        }
        else {
            book = null;
        }

        return book;
    }

    @GetMapping("/getBookName")
    public List<String> getBookNameList() {
        List<String> book_li = bookRepository.findBookName();

        return book_li;
    }
}
