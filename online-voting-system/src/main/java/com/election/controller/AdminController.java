package com.election.controller;

import com.election.model.Candidate;
import com.election.model.User;
import com.election.service.ElectionService;
import com.election.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private ElectionService electionService;
    
    @Autowired
    private UserService userService;
    
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<Candidate> candidates = electionService.getAllCandidates();
        List<User> users = userService.getAllUsers();
        int totalVotes = electionService.getTotalVotes();
        
        model.addAttribute("candidates", candidates);
        model.addAttribute("users", users);
        model.addAttribute("totalVotes", totalVotes);
        
        return "admin/dashboard";
    }
    
    @GetMapping("/candidates")
    public String manageCandidates(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<Candidate> candidates = electionService.getAllCandidates();
        model.addAttribute("candidates", candidates);
        
        return "admin/candidates";
    }
    
    @GetMapping("/candidates/add")
    public String addCandidateForm(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        return "admin/add-candidate";
    }
    
    @PostMapping("/candidates/add")
    public String addCandidate(@ModelAttribute Candidate candidate, 
                              HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        electionService.addCandidate(candidate);
        model.addAttribute("success", "Candidate added successfully!");
        
        return "redirect:/admin/candidates";
    }
    
    @GetMapping("/candidates/edit/{id}")
    public String editCandidateForm(@PathVariable Long id, 
                                   HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        Candidate candidate = electionService.getCandidateById(id);
        model.addAttribute("candidate", candidate);
        
        return "admin/edit-candidate";
    }
    
    @PostMapping("/candidates/edit")
    public String editCandidate(@ModelAttribute Candidate candidate, 
                               HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        electionService.updateCandidate(candidate);
        return "redirect:/admin/candidates";
    }
    
    @PostMapping("/candidates/delete/{id}")
    public String deleteCandidate(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        electionService.deleteCandidate(id);
        return "redirect:/admin/candidates";
    }
    
    @GetMapping("/results")
    public String viewResults(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !"ADMIN".equals(user.getRole())) {
            return "redirect:/login";
        }
        
        List<Candidate> candidates = electionService.getAllCandidates();
        int totalVotes = electionService.getTotalVotes();
        
        model.addAttribute("candidates", candidates);
        model.addAttribute("totalVotes", totalVotes);
        
        return "admin/results";
    }
}