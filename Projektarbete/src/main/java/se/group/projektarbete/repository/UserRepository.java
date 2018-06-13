package se.group.projektarbete.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import se.group.projektarbete.data.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("SELECT MAX(userNumber) from User")
    Optional<Long> getHighestUserNumber();

    Optional<User> findUserByuserNumber(Long id);

    List<User> findAll();

    List<User> getAllByTeamId(Long id);

    List<User> findUsersByTeamId(Long teamId);
}