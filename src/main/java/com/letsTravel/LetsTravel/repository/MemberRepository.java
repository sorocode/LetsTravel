package com.letsTravel.LetsTravel.repository;

import java.util.List;
import java.util.Optional;

import com.letsTravel.LetsTravel.domain.member.LoginDTO;
import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;

public interface MemberRepository {

	Optional<MemberBasicInfoReadDTO> findMember(LoginDTO loginDTO);
	List<MemberBasicInfoReadDTO> findPlanShareMemberByPlanSeq(int planSeq);
}
