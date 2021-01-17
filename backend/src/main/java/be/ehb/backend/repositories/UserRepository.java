package be.ehb.backend.repositories;

import be.ehb.backend.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    public Optional<User> findUserByEmail(String email);

    public boolean existsByEmail(String email);

    public Optional<User> findByEmailAndPassword(String email, String password);

}
