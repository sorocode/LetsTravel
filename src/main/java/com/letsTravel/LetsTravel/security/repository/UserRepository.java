package com.letsTravel.LetsTravel.security.repository;

import com.letsTravel.LetsTravel.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.username = :username")
    User findByUsername(String username);

}
