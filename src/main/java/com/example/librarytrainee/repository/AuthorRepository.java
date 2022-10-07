package com.example.librarytrainee.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.librarytrainee.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
  List<Author> findAuthorsByBooksId(Long bookId);
}
