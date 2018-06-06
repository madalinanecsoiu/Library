package com.library.apis;

import com.library.model.Book;
import com.library.model.BookRenting;
import com.library.model.User;
import com.library.repository.BookRentingRepository;
import com.library.repository.BookRepository;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/library")
public class BookController {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BookRentingRepository brenting;

    @Autowired
    private UserRepository userrepo;
    //get all books

    @RequestMapping(value="/books", method= RequestMethod.GET)
    List<Book> getAllBooks(){
        return bookRepo.findAll();
    }

    //add a new book
    @RequestMapping(value="/books", method = RequestMethod.POST)
    public Book addBook (@Valid @RequestBody Book book) {
        return bookRepo.save(book);
    }

    @RequestMapping(value="/books/{categoryId}", method= RequestMethod.GET)
    public List<Book> getBooksFromSpecificCategory(@PathVariable("categoryId") int categoryId){

        switch(categoryId) {
            case 1: return bookRepo.findByCategory("Beletristica", "AVAILABlE");
            case 2: return bookRepo.findByCategory("Cariera", "AVAILABlE");
            case 3: return bookRepo.findByCategory("Comunicare", "AVAILABlE");
            case 4: return bookRepo.findByCategory("Dezvoltare personala", "AVAILABlE");
            case 5: return bookRepo.findByCategory("Tehnologie", "AVAILABlE");
        }
        return null;

    }
    @RequestMapping(value="/books/{bookId}/{userId}", method = RequestMethod.PUT)
    public BookRenting updatingUserBook (@PathVariable("bookId") int bookId, @PathVariable("userId") int userId, @RequestBody String rating) {

        Book book = bookRepo.findById(bookId).get();
        User user = userrepo.findById(userId).get();
        BookRenting brent = brenting.findById(user, book);

        Double result;
        Integer peopleWhoRated = book.getNumberOfUsersWhoRated();

        result = ((book.getBookRating()*peopleWhoRated)+ Double.valueOf(rating))/ (peopleWhoRated + 1);

        peopleWhoRated++;
        book.setNumberOfUsersWhoRated(peopleWhoRated);
        book.setBookRating(result);

        bookRepo.save(book);

        brent.setRated(true);
        brenting.save(brent);
        return brent;
    }

}
