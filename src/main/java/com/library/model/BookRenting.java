package com.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.pks.BookRentingId;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="bookrenting")
public class BookRenting {

    @EmbeddedId

    private BookRentingId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("bookId")
    private Book book;

    @Column(name="start_date")
    private String startOfRenting;

    @Column(name="end_date")
    private String endOfRenting;


    @Column(name="renting_state")
    private String stateOfRenting;

    @Column(name="rated")
    private Boolean rated;

    public Boolean getRated() {
        return rated;
    }

    public void setRated(Boolean rated) {
        this.rated = rated;
    }

    public BookRentingId getId() {
        return id;
    }

    public void setId(BookRentingId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public String getStartOfRenting() {
        return startOfRenting;
    }

    public void setStartOfRenting(String startOfRenting) {
        this.startOfRenting = startOfRenting;
    }

    public String getEndOfRenting() {
        return endOfRenting;
    }

    public void setEndOfRenting(String endOfRenting) {
        this.endOfRenting = endOfRenting;
    }

    public String getStateOfRenting() {
        return stateOfRenting;
    }

    public void setStateOfRenting(String stateOfRenting) {
        this.stateOfRenting = stateOfRenting;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRenting that = (BookRenting) o;
        return Objects.equals(user, that.user) &&
                Objects.equals(book, that.book);
    }

    @Override
    public int hashCode() {

        return Objects.hash(user, book);
    }
    public BookRenting() {

    }
    public BookRenting(User user, Book book) {
        this.user = user;
        this.book = book;
        this.id = new BookRentingId(book.getId(), user.getId());
    }
}
