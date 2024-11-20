package com.letsTravel.LetsTravel.repository;

import java.util.List;
import java.util.Optional;

import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;

public interface MemberRepository {
	List<MemberBasicInfoReadDTO> findPlanShareMemberByPlanSeq(int planSeq);
}
