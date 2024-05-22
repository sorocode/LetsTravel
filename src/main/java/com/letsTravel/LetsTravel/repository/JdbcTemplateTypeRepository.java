package com.letsTravel.LetsTravel.repository;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.letsTravel.LetsTravel.domain.PlaceTypeCreateDTO;
import com.letsTravel.LetsTravel.domain.PrimaryTypeUpdateDTO;
import com.letsTravel.LetsTravel.domain.PrimaryTypeDetailDTO;

@Repository
public class JdbcTemplateTypeRepository implements TypeRepository {

	private final JdbcTemplate jdbcTemplate;

	public JdbcTemplateTypeRepository(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public int modifyTypeNameTranslated(PrimaryTypeDetailDTO PrimaryTypeDetailDTO) {
		// Profiling 해보니 Type_name_translated IS NULL 확인하는 게 update 할 때 더 손해라 안 넣는 게 나을 듯
		// Short-circuit evaluation을 하기 때문인 듯
		// 다시 생각해 보니 primaryTypeDisplayName이 안 넘어오는 경우가 있어 번역이 수정되면 안 되니 IS NULL이 필요할 듯
		String sql = "UPDATE Type SET Type_name_translated = ? WHERE Type_name = ? AND Type_name_translated IS NULL;";
		return jdbcTemplate.update(sql, PrimaryTypeDetailDTO.getPrimaryTypeDisplayName(), PrimaryTypeDetailDTO.getPrimaryType());
	}

	@Override
	public int addPlaceType(PlaceTypeCreateDTO placeTypeCreateDTO) {
		String sql = "INSERT IGNORE INTO Place_type(Place_seq, Type_seq) VALUES(?, (SELECT Type_seq FROM Type WHERE Type_name = ?)) ;";
		return jdbcTemplate.update(sql, placeTypeCreateDTO.getPlaceSeq(), placeTypeCreateDTO.getType());
	}

	@Override
	public int modifyPrimaryType(PrimaryTypeUpdateDTO primaryTypeUpdateDTO) {
		String sql = "UPDATE Place_type SET Is_primary_type = TRUE WHERE Place_seq = ? AND Type_seq = (SELECT Type_seq FROM Type WHERE Type_name = ?);";
		return jdbcTemplate.update(sql, primaryTypeUpdateDTO.getPlaceSeq(), primaryTypeUpdateDTO.getPrimaryType());
	}

}
