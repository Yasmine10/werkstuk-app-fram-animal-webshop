package be.ehb.backend.controllers;

import be.ehb.backend.daos.UserDAO;
import be.ehb.backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> getUserById(@PathVariable(value = "id") Long id) {
        Optional<User> user = userDAO.findById(id);
        if(user.isPresent()) {
            return user;
        }
        return null;
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public HttpStatus saveUser(@RequestBody User user) {
        userDAO.save(user);
        return HttpStatus.OK;
    }

}
