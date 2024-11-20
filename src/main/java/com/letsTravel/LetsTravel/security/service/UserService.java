package com.letsTravel.LetsTravel.security.service;

import com.letsTravel.LetsTravel.security.jwt.JwtTokenUtil;
import com.letsTravel.LetsTravel.security.model.*;
import com.letsTravel.LetsTravel.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${jwt.secret-key}")
    private String secret_key;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public SimpleMessageDto login(LoginDto loginDto) {

        User user = userRepository.findByUsername(loginDto.getUsername());

        if (user == null) {
            return SimpleMessageDto.builder().message("존재하지 않는 사용자이거나 비밀번호가 잘못되었습니다.").build();
        } else {
            if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
                return SimpleMessageDto.builder().message("존재하지 않는 사용자이거나 비밀번호가 잘못되었습니다.").build();
            }
            String token = JwtTokenUtil.createToken(user.getUsername(), secret_key, 60*60*1000L);

            return SimpleMessageDto.builder().message(token).build();
        }
    }

    public SimpleMessageDto signup(SignupDto signupDto) {

        User user = userRepository.findByUsername(signupDto.getUsername());

        if (user != null) {
            return SimpleMessageDto.builder().message("존재하는 아이디입니다.").build();
        } else {
            if (signupDto.getPassword().length() > 15) {
                return SimpleMessageDto.builder().message("비밀번호가 너무 깁니다.").build();
            } else if (signupDto.getUsername().length() > 10) {
                return SimpleMessageDto.builder().message("아이디가 너무 깁니다.").build();
            }
            userRepository.save(User.builder()
                    .username(signupDto.getUsername())
                    .password(passwordEncoder.encode(signupDto.getPassword()))
                    .role(UserRole.ROLE_USER.name()).build()
            );
            return SimpleMessageDto.builder().message("로그인 성공").build();
        }
    }

    public User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return user;
    }



}
