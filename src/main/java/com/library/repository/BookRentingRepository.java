package com.library.repository;

import com.library.model.Book;
import com.library.model.User;
import com.library.model.BookRenting;
import com.library.pks.BookRentingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRentingRepository extends JpaRepository<BookRenting, BookRentingId> {

    @Query("select b from BookRenting b where b.user = ?1")
    public List<BookRenting> findAllByUserId(User user);

    @Query("select b from BookRenting b where b.user = ?1 and b.book = ?2")
    public BookRenting findById(User user, Book book);

}
