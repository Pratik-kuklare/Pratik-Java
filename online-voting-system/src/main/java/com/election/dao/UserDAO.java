package com.election.dao;

import com.election.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UserDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setId(rs.getLong("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setFullName(rs.getString("full_name"));
            user.setNationalId(rs.getString("national_id"));
            user.setRole(rs.getString("role"));
            user.setEnabled(rs.getBoolean("enabled"));
            user.setHasVoted(rs.getBoolean("has_voted"));
            user.setRegistrationDate(rs.getTimestamp("registration_date").toLocalDateTime());
            return user;
        }
    }
    
    public void save(User user) {
        String sql = "INSERT INTO users (username, password, email, full_name, national_id, role, enabled, has_voted, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUsername(), user.getPassword(), user.getEmail(), 
                           user.getFullName(), user.getNationalId(), user.getRole(), 
                           user.isEnabled(), user.isHasVoted(), user.getRegistrationDate());
    }
    
    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), username);
        } catch (Exception e) {
            return null;
        }
    }
    
    public User findByNationalId(String nationalId) {
        String sql = "SELECT * FROM users WHERE national_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new UserRowMapper(), nationalId);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void updateVoteStatus(Long userId) {
        String sql = "UPDATE users SET has_voted = true WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }
    
    public List<User> findAll() {
        String sql = "SELECT * FROM users ORDER BY registration_date DESC";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }
    
    public boolean existsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username);
        return count != null && count > 0;
    }
    
    public boolean existsByNationalId(String nationalId) {
        String sql = "SELECT COUNT(*) FROM users WHERE national_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, nationalId);
        return count != null && count > 0;
    }
}