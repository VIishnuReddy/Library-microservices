package com.example.library.reposiories;

import com.example.library.models.Book;
import com.example.library.models.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findBooksByBookstatus(BookStatus bookStatus);
}
