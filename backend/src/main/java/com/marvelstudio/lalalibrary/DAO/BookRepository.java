package com.marvelstudio.lalalibrary.DAO;

import com.marvelstudio.lalalibrary.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
