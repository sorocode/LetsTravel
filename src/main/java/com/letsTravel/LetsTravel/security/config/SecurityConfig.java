package com.letsTravel.LetsTravel.security.config;

import com.letsTravel.LetsTravel.security.jwt.JwtTokenFilter;
import com.letsTravel.LetsTravel.security.model.UserRole;
import com.letsTravel.LetsTravel.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserService userService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin((formLogin) -> formLogin.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtTokenFilter(userService, secretKey), LogoutFilter.class)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/login/signup", "/login").permitAll()
                .requestMatchers("/login/admin/**").hasAuthority(UserRole.ROLE_ADMIN.name()).anyRequest().authenticated())

                .build();
    }

}