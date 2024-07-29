package com.letsTravel.LetsTravel.domain.member;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String loginId;
    private String password;

    private UserRole role;

}
