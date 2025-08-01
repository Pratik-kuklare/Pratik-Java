package com.election.dao;

import com.election.model.Candidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CandidateDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final class CandidateRowMapper implements RowMapper<Candidate> {
        @Override
        public Candidate mapRow(ResultSet rs, int rowNum) throws SQLException {
            Candidate candidate = new Candidate();
            candidate.setId(rs.getLong("id"));
            candidate.setName(rs.getString("name"));
            candidate.setParty(rs.getString("party"));
            candidate.setDescription(rs.getString("description"));
            candidate.setPhotoUrl(rs.getString("photo_url"));
            candidate.setActive(rs.getBoolean("active"));
            candidate.setVoteCount(rs.getInt("vote_count"));
            return candidate;
        }
    }
    
    public void save(Candidate candidate) {
        String sql = "INSERT INTO candidates (name, party, description, photo_url, active, vote_count) VALUES (?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, candidate.getName(), candidate.getParty(), 
                           candidate.getDescription(), candidate.getPhotoUrl(), 
                           candidate.isActive(), candidate.getVoteCount());
    }
    
    public List<Candidate> findAllActive() {
        String sql = "SELECT * FROM candidates WHERE active = true ORDER BY name";
        return jdbcTemplate.query(sql, new CandidateRowMapper());
    }
    
    public List<Candidate> findAll() {
        String sql = "SELECT * FROM candidates ORDER BY name";
        return jdbcTemplate.query(sql, new CandidateRowMapper());
    }
    
    public Candidate findById(Long id) {
        String sql = "SELECT * FROM candidates WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new CandidateRowMapper(), id);
        } catch (Exception e) {
            return null;
        }
    }
    
    public void incrementVoteCount(Long candidateId) {
        String sql = "UPDATE candidates SET vote_count = vote_count + 1 WHERE id = ?";
        jdbcTemplate.update(sql, candidateId);
    }
    
    public void update(Candidate candidate) {
        String sql = "UPDATE candidates SET name = ?, party = ?, description = ?, photo_url = ?, active = ? WHERE id = ?";
        jdbcTemplate.update(sql, candidate.getName(), candidate.getParty(), 
                           candidate.getDescription(), candidate.getPhotoUrl(), 
                           candidate.isActive(), candidate.getId());
    }
    
    public void delete(Long id) {
        String sql = "DELETE FROM candidates WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}