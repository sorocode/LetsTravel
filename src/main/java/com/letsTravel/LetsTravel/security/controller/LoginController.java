package com.letsTravel.LetsTravel.security.controller;

import com.letsTravel.LetsTravel.security.model.LoginDto;
import com.letsTravel.LetsTravel.security.model.SignupDto;
import com.letsTravel.LetsTravel.security.model.SimpleMessageDto;
import com.letsTravel.LetsTravel.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SimpleMessageDto> login(
            @RequestBody LoginDto loginDto
    ) {

        SimpleMessageDto result = userService.login(loginDto);

        return ResponseEntity.ok(result);
    }

    @PostMapping("signup")
    public ResponseEntity<SimpleMessageDto> signup(
            @RequestBody SignupDto signupDto
    ) {
        SimpleMessageDto result = userService.signup(signupDto);

        return ResponseEntity.ok(result);
    }

}
