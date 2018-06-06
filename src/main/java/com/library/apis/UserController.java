package com.library.apis;

import com.library.model.User;
import com.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/library")
public class UserController {

    @Autowired
    private UserRepository userRepo;

    //Get all users
    @RequestMapping(value="/users", method= RequestMethod.GET)
    public List<User> getAllUsers(){
        return userRepo.findAll();
    }

    @RequestMapping(value="/users", method= RequestMethod.POST)
    public User createUser(@Valid @RequestBody User user) {
        return userRepo.save(user);
    }
    //daca nu exista user atunci returneaza -1
    //daca exista dar nu are parola corecta atunci 0
    @RequestMapping(value="/users/login", method= RequestMethod.POST)
    public Integer loginSimpleUser(@Valid @RequestBody User user) {
        User returnedUser = userRepo.findByEmail(user.getEmail());
        if(returnedUser == null)
            return -1;
        if(returnedUser.getEmail().equals("admin@upb.com"))
            return -2;
        if (returnedUser.getPassword().equals(user.getPassword()))
            return returnedUser.getId();
        return 0;
    }

    @RequestMapping(value="/users/loginGoogle", method= RequestMethod.POST)
    public Integer loginGoogleUser(@Valid @RequestBody User user) {
        //cauta userul dupa email
            // daca exista atunci intoarce customer id
            // daca nu exista atunci insereaza in userinformation si intoarce customer id

        User returnedUser = userRepo.findByEmail(user.getEmail());

        if(returnedUser == null)
            return userRepo.save(user).getId();
        return returnedUser.getId();
    }

}
