package com.election.controller;

import com.election.model.Candidate;
import com.election.model.User;
import com.election.service.ElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/voter")
public class VoterController {
    
    @Autowired
    private ElectionService electionService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"VOTER".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<Candidate> candidates = electionService.getAllActiveCandidates();
        model.addAttribute("candidates", candidates);
        model.addAttribute("hasVoted", user.isHasVoted());
        model.addAttribute("user", user);
        
        return "voter/dashboard";
    }
    
    @PostMapping("/vote")
    public String vote(@RequestParam Long candidateId, 
                      HttpSession session, 
                      HttpServletRequest request,
                      Model model) {
        
        User user = (User) session.getAttribute("user");
        if (user == null || !"VOTER".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        if (user.isHasVoted()) {
            model.addAttribute("error", "You have already voted!");
            return "redirect:/voter/dashboard";
        }
        
        String ipAddress = request.getRemoteAddr();
        boolean success = electionService.castVote(user.getId(), candidateId, ipAddress);
        
        if (success) {
            // Update session user
            user.setHasVoted(true);
            session.setAttribute("user", user);
            model.addAttribute("success", "Your vote has been cast successfully!");
        } else {
            model.addAttribute("error", "Failed to cast vote. You may have already voted.");
        }
        
        return "redirect:/voter/dashboard";
    }
    
    @GetMapping("/results")
    public String results(Model model) {
        List<Candidate> candidates = electionService.getAllActiveCandidates();
        int totalVotes = electionService.getTotalVotes();
        
        model.addAttribute("candidates", candidates);
        model.addAttribute("totalVotes", totalVotes);
        
        return "voter/results";
    }
}