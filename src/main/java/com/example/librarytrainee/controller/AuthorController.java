package com.example.librarytrainee.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.librarytrainee.exception.ResourceNotFoundException;
import com.example.librarytrainee.model.Author;
import com.example.librarytrainee.model.Book;
import com.example.librarytrainee.repository.AuthorRepository;
import com.example.librarytrainee.repository.BookRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class AuthorController {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private AuthorRepository authorRepository;

  @GetMapping("/authors")
  public ResponseEntity<List<Author>> getAllAuthors() {
    List<Author> authors = new ArrayList<Author>();

    authorRepository.findAll().forEach(authors::add);

    if (authors.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(authors, HttpStatus.OK);
  }
  
  @GetMapping("/books/{bookId}/authors")
  public ResponseEntity<List<Author>> getAllAuthorsByBookId(@PathVariable(value = "bookId") Long bookId) {
    if (!bookRepository.existsById(bookId)) {
      throw new ResourceNotFoundException("Not found Book with id = " + bookId);
    }

    List<Author> authors = authorRepository.findAuthorsByBooksId(bookId);
    return new ResponseEntity<>(authors, HttpStatus.OK);
  }

  @GetMapping("/authors/{id}")
  public ResponseEntity<Author> getAuthorsById(@PathVariable(value = "id") Long id) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + id));

    return new ResponseEntity<>(author, HttpStatus.OK);
  }
  
  @GetMapping("/authors/{authorId}/books")
  public ResponseEntity<List<Book>> getAllBooksByAuthorId(@PathVariable(value = "authorId") Long authorId) {
    if (!authorRepository.existsById(authorId)) {
      throw new ResourceNotFoundException("Not found Author  with id = " + authorId);
    }

    List<Book> books = bookRepository.findBooksByAuthorsId(authorId);
    return new ResponseEntity<>(books, HttpStatus.OK);
  }

  @PostMapping("/books/{bookId}/authors")
  public ResponseEntity<Author> addAuthor(@PathVariable(value = "bookId") Long bookId, @RequestBody Author authorRequest) {
    Author author = bookRepository.findById(bookId).map(book -> {
      long authorId = authorRequest.getId();
      
      // Author is existed
      if (authorId != 0L) {
        Author _author = authorRepository.findById(authorId)
            .orElseThrow(() -> new ResourceNotFoundException("Not found Author with id = " + authorId));
        book.addAuthor(_author);
        bookRepository.save(book);
        return _author;
      }
      
      // add and create new Author
      book.addAuthor(authorRequest);
      return authorRepository.save(authorRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));

    return new ResponseEntity<>(author, HttpStatus.CREATED);
  }

  @PutMapping("/authors/{id}")
  public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author authorRequest) {
    Author author = authorRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("AuthorId " + id + "not found"));

    author.setFirst_name(authorRequest.getFirst_name());
    author.setLast_name(authorRequest.getLast_name());
    author.setBirthdate(authorRequest.getBirthdate());

    return new ResponseEntity<>(authorRepository.save(author), HttpStatus.OK);
  }
 
  @DeleteMapping("/books/{bookId}/authors/{authorId}")
  public ResponseEntity<HttpStatus> deleteAuthorFromBook(@PathVariable(value = "bookId") Long bookId, @PathVariable(value = "authorId") Long authorId) {
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Book with id = " + bookId));
    
    book.removeAuthor(authorId);
    bookRepository.save(book);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/authors/{id}")
  public ResponseEntity<HttpStatus> deleteAuthor(@PathVariable("id") long id) {
    authorRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
