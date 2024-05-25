package com.letsTravel.LetsTravel.repository;

import java.util.Optional;

import com.letsTravel.LetsTravel.domain.LoginDTO;
import com.letsTravel.LetsTravel.domain.MemberBasicInfoReadDTO;

public interface MemberRepository {

	Optional<MemberBasicInfoReadDTO> findMember(LoginDTO loginDTO);

}
