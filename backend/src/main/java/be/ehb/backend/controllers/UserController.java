package be.ehb.backend.controllers;

import be.ehb.backend.entities.Address;
import be.ehb.backend.repositories.AddressRepository;
import be.ehb.backend.repositories.UserRepository;
import be.ehb.backend.entities.User;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
     * GET method: /
     * to get all users
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    /*
     * GET method: /{id}
     * to get a specific user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> getUserById(@PathVariable(value = "id") Long id) {
        return userRepository.findById(id);  // if no user => returns null
    }

    /*
     * GET method: /{id}
     * to get a specific user
     */
    @RequestMapping(value = "/email={email}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<User> getUserByEmail(@PathVariable(value = "email") String email) {
        return userRepository.findUserByEmail(email);  // if no user => returns null
    }

    /*
     * GET method: /{id}
     * to get a specific user
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public User loginUser(@Valid @RequestBody User user) throws Exception {

        String jwtToken = "";

        if(user.getEmail() == null || user.getPassword() == null) {
            throw new Exception("Please fill in username and password");
        }

        String email = user.getEmail();
        String password = user.getPassword();

        Optional<User> newUser = userRepository.findUserByEmail(email);

        if(newUser == null) {
            throw new Exception("User email not found.");
        }

        String pwd = newUser.get().getPassword();

        boolean isPasswordMatch = passwordEncoder.matches(password, pwd);


        if(!isPasswordMatch) {
            throw new Exception("Invalid login. Please check your name and password.");
        }

        jwtToken = Jwts.builder().setSubject(email).claim("user","user").setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS256, "secretkey").compact();

        newUser.get().setToken(jwtToken);

        User currentUser = newUser.get();

        return currentUser;  // if no user => returns null
    }


    /*
     * GET method: /{id}/addresses
     * to get all the addresses from a user
     */
    @RequestMapping(value = "/{id}/addresses", method = RequestMethod.GET)
    @ResponseBody
    public List<Address> getAllAddressesByUserId(@PathVariable(value = "id") Long id) {
        // check if the user exists
        Optional<User> existingUser = userRepository.findById(id);
        return existingUser.map(user -> addressRepository.findAllByUser(user)).orElse(null);
    }

    /*
     * POST method: /
     * to save a new user
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public User saveUser(@Valid @RequestBody User user) throws Exception {

        // check if the user already exists, email must be unique
        Optional<User> newUser = userRepository.findUserByEmail(user.getEmail());
        if(newUser.isPresent()) {
            throw new Exception("There already is an account for this email.");
        } else {
            userRepository.save(user);
        }

        return user;
    }

    /*
     * PUT method:
     * to update an existing user
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateUser(@Valid @RequestBody User user) {
        try {
            // check if the user exists
            Optional<User> existingUser = userRepository.findById(user.getId());
            if(existingUser.isPresent()) {
                userRepository.save(user);
            } else {
                throw new Exception("User doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating the user: " + e.toString();
        }
        return "User successfully updated!";
    }

    /*
     * DELETE method: /{id}
     * to delete an existing user
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteUser(@PathVariable(value = "id") Long id) {
        try {
            // check if the user exists
            Optional<User> existingUser = userRepository.findById(id);
            if(existingUser.isPresent()) {
                List<Address> addressesUser = addressRepository.findAllByUser(existingUser.get());
                if(!addressesUser.isEmpty()) {
                    addressRepository.deleteAll(addressesUser);
                }
                userRepository.delete(existingUser.get());
            } else {
                throw new Exception("User doesn't exist.");
            }
        } catch (Exception e) {
            return "Error deleting the user: " + e.toString();
        }
        return "User successfully deleted!";
    }
}
