package com.letsTravel.LetsTravel.repository;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

	private JdbcTemplate jdbcTemplate;

	public JdbcTemplateMemberRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	private RowMapper<MemberBasicInfoReadDTO> memberBasicInfoReadDTORowMapper() {
		return (rs, rowNum) -> {
			MemberBasicInfoReadDTO memberBasicInfoReadDTO = new MemberBasicInfoReadDTO();
			memberBasicInfoReadDTO.setMemSeq(rs.getInt("M.Mem_seq"));
			memberBasicInfoReadDTO.setNickName(rs.getString("M.Nickname"));
			return memberBasicInfoReadDTO;
		};
	}

	@Override
	public List<MemberBasicInfoReadDTO> findPlanShareMemberByPlanSeq(int planSeq) {
		String sql = "SELECT M.Mem_seq, M.NickName FROM Plan P, Plan_share PS, Member M WHERE P.Plan_seq = PS.Plan_seq AND PS.Mem_seq = M.Mem_seq AND P.Plan_seq = ?;";
		return jdbcTemplate.query(sql, memberBasicInfoReadDTORowMapper(), planSeq);
	}
}
