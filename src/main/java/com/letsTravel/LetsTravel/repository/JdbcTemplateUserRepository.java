package com.letsTravel.LetsTravel.repository;

import com.letsTravel.LetsTravel.domain.member.LoginDTO;
import com.letsTravel.LetsTravel.domain.member.MemberBasicInfoReadDTO;
import com.letsTravel.LetsTravel.domain.member.User;
import com.letsTravel.LetsTravel.domain.member.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTemplateUserRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public boolean existsByLoginId(String loginId) {
        String sql = "SELECT COUNT(*) FROM user WHERE login_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{loginId}, Integer.class);
        return count != null && count > 0;
    }


    // 테이블 만들어야함
    @Override
    public Optional<User> findByLoginId(String loginId) {
        String sql = "SELECT * FROM user WHERE login_id = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper(), loginId);
        return result.stream().findAny();
    }

    private RowMapper<User> userRowMapper(){
        return (rs, rowNum) -> {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setLoginId(rs.getString("login_id"));
            user.setPassword(rs.getString("password"));
            user.setRole(UserRole.valueOf(rs.getString("role")));
            return user;
        };
    }

    @Override
    public User save(User user) {
        if (user.getLoginId() == null || !existsByLoginId(user.getLoginId())) {
            return insertUser(user);
        } else {
            return updateUser(user);
        }
    }

    private User insertUser(User user) {
        String sql = "INSERT INTO user (login_id, password, role) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getRole().name());
        // 자동 증가된 ID를 수동으로 설정해야 한다면, 여기에 코드를 추가해야 합니다.
        // 예: 사용자가 수동으로 ID를 설정하는 경우 (자동 증가 ID 사용이 아닌 경우)
        // user.setId(generatedId);
        return user;
    }

    private User updateUser(User user) {
        String sql = "UPDATE user SET login_id = ?, password = ?, role = ? WHERE id = ?";
        jdbcTemplate.update(sql, user.getLoginId(), user.getPassword(), user.getRole().name(), user.getId());
        return user;
    }

}
