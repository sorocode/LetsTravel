package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.TypeTranslateDTO;

@Repository
public class JdbcTemplateTypeRepository implements TypeRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateTypeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int modifyTypeNameTranslated(TypeTranslateDTO typeTranslateDTO) {
		String sql = "UPDATE Type SET Type_name_translated = ? WHERE Type_name = ?;";
		return jdbcTemplate.update(sql, typeTranslateDTO.getPrimaryTypeDisplayName(), typeTranslateDTO.getPrimaryType());
	}

}
