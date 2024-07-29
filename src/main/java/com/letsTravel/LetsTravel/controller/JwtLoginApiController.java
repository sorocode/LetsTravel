package com.letsTravel.LetsTravel.controller;

import com.letsTravel.LetsTravel.domain.member.LoginDTO;
import com.letsTravel.LetsTravel.domain.member.User;
import com.letsTravel.LetsTravel.security.JwtTokenUtil;
import com.letsTravel.LetsTravel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt-login")
public class JwtLoginApiController {

    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody LoginDTO loginDTO) {

        if (userService.checkLoginId(loginDTO.getLoginId())) {
            return "로그인 아이디 중복";
        }

        if (!loginDTO.getPassword().equals(loginDTO.getPasswordCheck())) {
            return "비밀번호 불일치";
        }

        userService.join(loginDTO);
        return "회원가입 성공";

    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {

        User user = userService.login(loginDTO);

        if (user == null) {
            return "로그인 아이디 또는 비밀번호 불일치";
        }

        String secretkey = "my-secret-key-123";
        long expireTime = 1000*60*60;

        String jwtToken = JwtTokenUtil.createToken(user.getLoginId(), secretkey, expireTime);

        return jwtToken;

    }

    @GetMapping("/info")
    public String userInfo(Authentication auth) {
        User loginUser = userService.getLoginUserByLoginId(auth.getName());

        return String.format("loginId : %s\nrole : %s", loginUser.getLoginId(), loginUser.getRole());
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "관리자 페이지 접근 성공";
    }

}
