package be.ehb.backend.daos;

import be.ehb.backend.entities.Address;
import org.springframework.data.repository.CrudRepository;

public interface AddressDAO extends CrudRepository<Address, Long> {

}
