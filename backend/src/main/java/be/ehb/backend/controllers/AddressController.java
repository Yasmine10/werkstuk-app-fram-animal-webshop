package be.ehb.backend.controllers;

import be.ehb.backend.entities.Address;
import be.ehb.backend.entities.User;
import be.ehb.backend.repositories.AddressRepository;
import be.ehb.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping(value = "/api/address")
public class AddressController {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    /*
     * GET method: /
     * to get all addresses
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public List<Address> getAllAddresses() {
        ArrayList<Address> addresses = new ArrayList<>();
        addressRepository.findAll().forEach(addresses::add);
        return addresses;
    }

    /*
     * GET method: /{id}
     * to get a specific address
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Optional<Address> getAddressById(@PathVariable(value = "id") Long id) {
        return addressRepository.findById(id);  // if no address => returns null
    }

    /*
     * POST method: /
     * to save a new address
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public Address saveAddress(@Valid @RequestBody Address address) throws Exception {

        // check if the user exists
        Optional<User> existingUser = userRepository.findById(address.getUser().getId());
        if(existingUser.isPresent()) {
            // get all addresses from the user
            List<Address> addressesUser = addressRepository.findAllByUser(existingUser.get());
            // check if the address is in the list, if yes => exception, else add address
            if (addressesUser.contains(address)) {
                throw new Exception("Address already exists.");
            } else {
                addressRepository.save(address);
            }
        }
        return address;
    }

    /*
     * PUT method: /
     * to update an existing address
     */
    @RequestMapping(value = "", method = RequestMethod.PUT)
    @ResponseBody
    public String updateAddress(@Valid @RequestBody Address address) {
        try {
            // check if the address exists
            Optional<Address> existingAddress = addressRepository.findById(address.getId());
            if(existingAddress.isPresent()) {
                addressRepository.save(address);
            } else {
                throw new Exception("Address doesn't exist.");
            }
        } catch (Exception e) {
            return "Error updating address: " + e.toString();
        }
        return "Address successfully updated!";
    }

    /*
     * DELETE method: /{id}
     * to delete an existing address
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String deleteAddress(@PathVariable(value = "id") Long id) {
        try {
            // check if the address exists
            Optional<Address> existingAddress = addressRepository.findById(id);
            if(existingAddress.isPresent()) {
                addressRepository.delete(existingAddress.get());
            } else {
                throw new Exception("Address doesn't exist.");
            }
        } catch (Exception e) {
            return "Error deleting Address: " + e.toString();
        }
        return "Address successfully deleted!";
    }

}
