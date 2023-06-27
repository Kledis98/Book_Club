package com.example.book__club.repositories;

import java.util.List;


import com.example.book__club.models.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BookRepository extends CrudRepository<Book, Long> {

    List<Book> findAll();
}