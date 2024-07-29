package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.member.User;

import java.util.Optional;

public interface UserRepository {

    boolean existsByLoginId(String loginId);

    public User save(User user);

    Optional<User> findByLoginId(String loginId);

}
