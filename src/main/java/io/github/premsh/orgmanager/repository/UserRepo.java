package io.github.premsh.orgmanager.repository;

import io.github.premsh.orgmanager.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email = ?1 and u.isEnabled = true and u.isDeleted = false")
    Optional<User> findByEmail(String email);

    @Query("select u from User u where u.isDeleted = false")
    List<User> findAll();

    @Query("select (count(u) > 0) from User u where u.id = ?1 and u.isDeleted = false")
    boolean existsById(long id);

    @Query("select u from User u where upper(u.firstName) like upper(?1) and u.isDeleted = false")
    List<User> filterFirstname(String firstName);

    @Query("select u from User u where upper(u.lastName) like upper(?1) and u.isDeleted = false")
    List<User> filterLastname(String lastName);

    @Query("select u from User u where u.email = ?1 and u.isDeleted = false")
    List<User> filterByEmail(String email);

    @Query("select u from User u where upper(u.phone) like upper(?1) and u.isDeleted = false")
    List<User> filterByPhone(String phone);

    @Query("select u from User u where upper(u.address) like upper(?1) and u.isDeleted = false")
    List<User> filterByAddress(String address);


}
