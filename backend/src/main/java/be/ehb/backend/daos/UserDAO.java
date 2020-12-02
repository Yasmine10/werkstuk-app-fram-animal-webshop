package be.ehb.backend.daos;

import be.ehb.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDAO extends CrudRepository<User, Long> {



}
