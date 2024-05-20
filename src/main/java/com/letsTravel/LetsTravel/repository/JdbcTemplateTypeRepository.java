package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.TypeTranslateDTO;

@Repository
public class JdbcTemplateTypeRepository implements TypeRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateTypeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int modifyTypeNameTranslated(TypeTranslateDTO typeTranslateDTO) {
		// Profiling 해보니 Type_name_translated IS NULL 확인하는 게 update 할 때 더 손해라 안 넣는 게 나을 듯
		// Short-circuit evaluation을 하기 때문인 듯
		String sql = "UPDATE Type SET Type_name_translated = ? WHERE Type_name = ?;";
		return jdbcTemplate.update(sql, typeTranslateDTO.getPrimaryTypeDisplayName(), typeTranslateDTO.getPrimaryType());
	}

	@Override
	public int addPlaceType(PlaceTypeCreateDTO placeTypeCreateDTO) {
		String sql = "INSERT IGNORE INTO Place_type VALUES(?, (SELECT Type_seq FROM Type WHERE Type_name = ?), ?) ;";
		return jdbcTemplate.update(sql, placeTypeCreateDTO.getPlaceSeq(), placeTypeCreateDTO.getType(), placeTypeCreateDTO.isPrimaryType());
	}

}
