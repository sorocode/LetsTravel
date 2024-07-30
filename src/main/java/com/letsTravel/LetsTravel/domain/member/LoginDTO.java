package com.letsTravel.LetsTravel.domain.member;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

	private String loginId;
	private String password;
	private String passwordCheck;

	public User toUser() {
		return User.builder()
				.loginId(this.loginId)
				.password(this.password)
				.role(UserRole.USER)
				.build();
	}

	public User toAdmin() {
		return User.builder()
				.loginId(this.loginId)
				.password(this.password)
				.role(UserRole.ADMIN)
				.build();
	}

	public User toUser(String encodedPassword) {
		return User.builder()
				.loginId(this.loginId)
				.password(encodedPassword)
				.role(UserRole.USER)
				.build();
	}

}
