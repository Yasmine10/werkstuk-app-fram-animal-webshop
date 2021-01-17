package be.ehb.backend.repositories;


import be.ehb.backend.entities.Address;
import be.ehb.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface AddressRepository extends CrudRepository<Address, Long> {

    public List<Address> findAllByUser(User user);

    public Optional<Address> findAddressByStreetAndHouseNrAndAdditionAndZipcodeAndCityAndCountryAndUser(String street, String houseNr, String addition, String zipcode, String city, String country, User user);

}
