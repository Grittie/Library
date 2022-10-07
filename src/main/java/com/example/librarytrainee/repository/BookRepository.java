package com.example.librarytrainee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librarytrainee.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
//  List<Book> findByFiction(boolean isFiction);

  List<Book> findByTitleContaining(String title);
  
  List<Book> findBooksByAuthorsId(Long tagId);
}
