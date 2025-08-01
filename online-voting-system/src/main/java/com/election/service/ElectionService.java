package com.election.service;

import com.election.dao.CandidateDAO;
import com.election.dao.VoteDAO;
import com.election.dao.UserDAO;
import com.election.model.Candidate;
import com.election.model.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ElectionService {
    
    @Autowired
    private CandidateDAO candidateDAO;
    
    @Autowired
    private VoteDAO voteDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    public List<Candidate> getAllActiveCandidates() {
        return candidateDAO.findAllActive();
    }
    
    public List<Candidate> getAllCandidates() {
        return candidateDAO.findAll();
    }
    
    public void addCandidate(Candidate candidate) {
        candidateDAO.save(candidate);
    }
    
    public void updateCandidate(Candidate candidate) {
        candidateDAO.update(candidate);
    }
    
    public void deleteCandidate(Long id) {
        candidateDAO.delete(id);
    }
    
    public Candidate getCandidateById(Long id) {
        return candidateDAO.findById(id);
    }
    
    @Transactional
    public boolean castVote(Long userId, Long candidateId, String ipAddress) {
        // Check if user has already voted
        if (voteDAO.hasUserVoted(userId)) {
            return false;
        }
        
        // Create and save vote
        Vote vote = new Vote(userId, candidateId, ipAddress);
        voteDAO.save(vote);
        
        // Update candidate vote count
        candidateDAO.incrementVoteCount(candidateId);
        
        // Mark user as voted
        userDAO.updateVoteStatus(userId);
        
        return true;
    }
    
    public boolean hasUserVoted(Long userId) {
        return voteDAO.hasUserVoted(userId);
    }
    
    public int getTotalVotes() {
        return voteDAO.getTotalVotes();
    }
}