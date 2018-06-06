package com.library.apis;

import com.library.model.Book;
import com.library.model.DetailOfBookRenting;
import com.library.model.User;
import com.library.model.BookRenting;
import com.library.pks.BookRentingId;
import com.library.repository.BookRentingRepository;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/library")
public class BookRentingController {

    @Autowired
    private BookRentingRepository brenting;

    @Autowired
    private BookRepository bookrepo;

    @Autowired
    private UserRepository userrepo;

    @RequestMapping(value="/userBooks", method= RequestMethod.GET)
    List<BookRenting> getAllUserBooks(){
        return brenting.findAll();
    }
    /*
        User wants to rent a new book
     */
    @RequestMapping(value="/userBooks/{userId}/{bookId}", method = RequestMethod.POST)
    public BookRenting addUserBook (@Valid @RequestBody DetailOfBookRenting details,
                                    @PathVariable("userId") int userId,
                                    @PathVariable("bookId") int bookId) {


        Book book = bookrepo.findById(bookId).get();
        User user = userrepo.findById(userId).get();
        if(book.getAvailabilityStatus().equals("AVAILABLE")) {
            BookRenting newUserBook = new BookRenting(user, book);
            newUserBook.setStartOfRenting(details.getStart());
            newUserBook.setEndOfRenting(details.getEnd());
            newUserBook.setStateOfRenting(details.getStatus());
            book.setQuantity(book.getQuantity() - 1);
            if(book.getQuantity() == 0)
                book.setAvailabilityStatus("UNAVAILABLE");
            bookrepo.save(book);
            return brenting.save(newUserBook);
        }
        return  null;
    }
    /*
        User cancel the renting of the book
     */
    @RequestMapping(value="/userBooks/{userId}/{bookId}", method = RequestMethod.DELETE)
    public void removeUserBook (@PathVariable("userId") int userId,
                                       @PathVariable("bookId") int bookId) {
        Book book = bookrepo.findById(bookId).get();
        User user = userrepo.findById(userId).get();
        BookRenting brent = brenting.findById(user, book);
        book.setQuantity(book.getQuantity()+1);
        if(book.getAvailabilityStatus().equals("UNAVAILABLE"))
            book.setAvailabilityStatus("AVAILABLE");
        bookrepo.save(book);
        brenting.delete(brent);

    }
    /*
        User wants information about the book that is rented
     */
    @RequestMapping(value="/userBooks/{userId}/{bookId}", method = RequestMethod.GET)
    public Book getOneUserBook (@PathVariable("userId") int userId,
                                @PathVariable("bookId") int bookId) {



        BookRenting brent = brenting.findById(new BookRentingId(bookId, userId)).get();
        return brent.getBook();
    }

    /*
    * Librarian modifies book with certain state = READING/FINISHED
    * */

    @RequestMapping(value="/userBooks/{userId}/{bookId}", method = RequestMethod.PUT)
    public void updatingUserBook (@PathVariable("userId") int userId,
                                @PathVariable("bookId") int bookId, @RequestBody String state) {
        Book book = bookrepo.findById(bookId).get();
        User user = userrepo.findById(userId).get();
        BookRenting brent = brenting.findById(user, book);
        brent.setStateOfRenting(state);
        brenting.save(brent);
    }

    /*
        Get all books for an user
     */
    @RequestMapping(value="/userBooks/{userId}", method = RequestMethod.GET)
    public List<BookRenting> getAllBooksForUser (@PathVariable("userId") int userId) {

        return brenting.findAllByUserId(userrepo.findById(userId).get());
    }



}
