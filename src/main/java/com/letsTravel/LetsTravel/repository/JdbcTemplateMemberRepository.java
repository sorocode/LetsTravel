package com.letsTravel.LetsTravel.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.member.LoginDTO;
import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplateMemberRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public Optional<MemberBasicInfoReadDTO> findMember(LoginDTO loginDTO) {
		String sql = "SELECT Mem_seq, Nickname FROM Member WHERE Email = ? AND Password = ?;";
		List<MemberBasicInfoReadDTO> result = jdbcTemplate.query(sql, memberBasicInfoReadDTORowMapper(), loginDTO.getId(), loginDTO.getPw());
		return result.stream().findAny();
	}

	private RowMapper<MemberBasicInfoReadDTO> memberBasicInfoReadDTORowMapper(){
		return (rs, rowNum) -> {
			MemberBasicInfoReadDTO memberBasicInfoReadDTO = new MemberBasicInfoReadDTO();
			memberBasicInfoReadDTO.setMemSeq(rs.getInt("Mem_seq"));
			memberBasicInfoReadDTO.setNickName(rs.getString("Nickname"));
			return memberBasicInfoReadDTO;
		};
	}
}
