package com.letsTravel.LetsTravel.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginDTO {

<<<<<<< HEAD
	private String id;
	private String pw;
=======
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

	public User toUser(String encodedPassword) {
		return User.builder()
				.loginId(this.loginId)
				.password(encodedPassword)
				.role(UserRole.USER)
				.build();
	}

>>>>>>> ff333ba (일단 커밋)
}
