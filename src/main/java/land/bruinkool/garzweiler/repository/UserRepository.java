package land.bruinkool.garzweiler.repository;

import land.bruinkool.garzweiler.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmailAddress(String emailAddress);

    Boolean existsByEmailAddress(String username);
}