package com.library.pks;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class BookRentingId implements Serializable{

    @Column(name="book_id")
    private int bookId;

    @Column(name="user_id")
    private int userId;

    private BookRentingId() {

    }

    public BookRentingId(int bookId, int userId) {
        this.bookId = bookId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookRentingId that = (BookRentingId) o;
        return bookId == that.bookId &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(bookId, userId);
    }
}
