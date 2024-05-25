package com.letsTravel.LetsTravel.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letsTravel.LetsTravel.domain.LoginDTO;
import com.letsTravel.LetsTravel.domain.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.repository.MemberRepository;

@Service
public class MemberService {

	private MemberRepository memberRepository;
	
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public Optional<MemberBasicInfoReadDTO> findMember(LoginDTO loginDTO) {
		// 프론트에서는 입력 여부만 우선 검사
		// 서버에서 상세한 ID, PW 조건 검사 후 DB Read
		// if(~~) ~~;
		return memberRepository.findMember(loginDTO);
	}

}
