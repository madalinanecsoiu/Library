package com.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalIdCache;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name= "bookinformation")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "book_name")
    private String bookName;

    @Column(name = "authors")
    private String authors;

    @Column(name = "publishers")
    private String publishers;

    @Column(name = "publication_year")
    private Date yearOfPublication;

    @Column(name = "categories")
    private String categories;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "nr_bookings")
    private int numberOfBookings;

    @Column(name = "rating")
    private double bookRating;

    @Column(name = "nr_users_rating")
    private int numberOfUsersWhoRated;

    @Column(name = "status")
    private String availabilityStatus;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            },
            mappedBy = "book")
    @JsonIgnore
    private List<BookRenting> users = new ArrayList<>();

    public int getNumberOfUsersWhoRated() {
        return numberOfUsersWhoRated;
    }

    public void setNumberOfUsersWhoRated(int numberOfUsersWhoRated) {
        this.numberOfUsersWhoRated = numberOfUsersWhoRated;
    }

    public List<BookRenting> getUsers() {
        return users;
    }

    public void setUsers(List<BookRenting> users) {
        this.users = users;
    }

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublishers() {
        return publishers;
    }

    public void setPublishers(String publishers) {
        this.publishers = publishers;
    }

    public Date getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Date yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getNumberOfBookings() {
        return numberOfBookings;
    }

    public void setNumberOfBookings(int numberOfBookings) {
        this.numberOfBookings = numberOfBookings;
    }

    public double getBookRating() {
        return bookRating;
    }

    public void setBookRating(double bookRating) {
        this.bookRating = bookRating;
    }

    public String getAvailabilityStatus() {
        return availabilityStatus;
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus = availabilityStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
