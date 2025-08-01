package com.election.dao;

import com.election.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class VoteDAO {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    private static final class VoteRowMapper implements RowMapper<Vote> {
        @Override
        public Vote mapRow(ResultSet rs, int rowNum) throws SQLException {
            Vote vote = new Vote();
            vote.setId(rs.getLong("id"));
            vote.setUserId(rs.getLong("user_id"));
            vote.setCandidateId(rs.getLong("candidate_id"));
            vote.setVoteTime(rs.getTimestamp("vote_time").toLocalDateTime());
            vote.setIpAddress(rs.getString("ip_address"));
            return vote;
        }
    }
    
    public void save(Vote vote) {
        String sql = "INSERT INTO votes (user_id, candidate_id, vote_time, ip_address) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, vote.getUserId(), vote.getCandidateId(), 
                           vote.getVoteTime(), vote.getIpAddress());
    }
    
    public boolean hasUserVoted(Long userId) {
        String sql = "SELECT COUNT(*) FROM votes WHERE user_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count != null && count > 0;
    }
    
    public int getTotalVotes() {
        String sql = "SELECT COUNT(*) FROM votes";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count != null ? count : 0;
    }
    
    public List<Vote> findAll() {
        String sql = "SELECT * FROM votes ORDER BY vote_time DESC";
        return jdbcTemplate.query(sql, new VoteRowMapper());
    }
}