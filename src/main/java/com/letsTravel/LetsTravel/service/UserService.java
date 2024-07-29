package com.letsTravel.LetsTravel.service;

import com.letsTravel.LetsTravel.domain.member.LoginDTO;
import com.letsTravel.LetsTravel.domain.member.User;
import com.letsTravel.LetsTravel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    public boolean checkLoginId(String loginId) {
        return userRepository.existsByLoginId(loginId);
    }

    public void join(LoginDTO req) {
        userRepository.save(req.toUser());
    }
    // jdbcTemplate 구현하기
    public void join2(LoginDTO req) {
        userRepository.save(req.toUser(encoder.encode(req.getPassword())));
    }

    public User login(LoginDTO req) {
        Optional<User> optionalUser = userRepository.findByLoginId(req.getLoginId());

        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(req.getPassword())) {
            return null;
        }

        return user;
    }
/*
    // 이거는 기본키로 알려주는거
    public User getLoginUserById(Long userId) {
        if (userId == null) {
            return null;
        }

        // jdbcTemplate
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get();

    }
*/
    public User getLoginUserByLoginId(String loginId) {
        if (loginId == null) {
            return null;
        }

        Optional<User> optionalUser = userRepository.findByLoginId(loginId);
        if (optionalUser.isEmpty()) {
            return null;
        }

        return optionalUser.get();
    }
}
