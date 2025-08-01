package com.example.employee.controller;

import com.example.employee.model.Employee;
import com.example.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmployeeController {

    @Autowired
    private EmployeeRepository repo;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("employees", repo.findAll());
        return "list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Employee employee) {
        repo.save(employee);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("employee", repo.findById(id).orElse(null));
        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }
}