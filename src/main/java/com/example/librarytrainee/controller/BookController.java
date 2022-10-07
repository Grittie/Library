package com.example.librarytrainee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.librarytrainee.exception.ResourceNotFoundException;
import com.example.librarytrainee.model.Book;
import com.example.librarytrainee.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class BookController {

  @Autowired
  BookRepository bookRepository;

  @GetMapping("/books")
  public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String title) {
    List<Book> books = new ArrayList<Book>();

    if (title == null)
      bookRepository.findAll().forEach(books::add);
    else
      bookRepository.findByTitleContaining(title).forEach(books::add);

    if (books.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @GetMapping("/books/{id}")
  public ResponseEntity<Book> getBookById(@PathVariable("id") long id) {
    Book book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

    return new ResponseEntity<>(book, HttpStatus.OK);
  }

  @PostMapping("/books")
  public ResponseEntity<Book> createBook(@RequestBody Book book) {
    Book _book = bookRepository.save(new Book(book.getTitle(), true));
    return new ResponseEntity<>(_book, HttpStatus.CREATED);
  }

  @PutMapping("/books/{id}")
  public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
    Book _book = bookRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + id));

    _book.setTitle(book.getTitle());
    _book.setFiction(book.getFiction());
    
    return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
  }

  @DeleteMapping("/books/{id}")
  public ResponseEntity<HttpStatus> deleteBook(@PathVariable("id") long id) {
    bookRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/books")
  public ResponseEntity<HttpStatus> deleteAllBooks() {
    bookRepository.deleteAll();
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
