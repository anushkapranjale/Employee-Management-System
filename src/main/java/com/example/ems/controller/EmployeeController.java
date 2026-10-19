package com.example.ems.controller;

import com.example.ems.model.Employee;
import com.example.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) { this.service = service; }

    // READ (all roles)
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("employees", service.findAll(keyword));
        model.addAttribute("keyword", keyword);
        return "employees/list";
    }

    // CREATE form (ADMIN, HR)
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("title", "Add Employee");
        return "employees/form";
    }

    // UPDATE form (ADMIN, HR)
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.findById(id));
        model.addAttribute("title", "Edit Employee");
        return "employees/form";
    }

    // CREATE + UPDATE submit (ADMIN, HR)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("employee") Employee employee,
                       BindingResult result, Model model, RedirectAttributes ra) {
        if (!result.hasFieldErrors("email") && service.emailTaken(employee.getEmail(), employee.getId())) {
            result.rejectValue("email", "duplicate", "This email is already in use");
        }
        if (result.hasErrors()) {
            model.addAttribute("title", employee.getId() == null ? "Add Employee" : "Edit Employee");
            return "employees/form";
        }
        boolean isNew = employee.getId() == null;
        service.save(employee);
        ra.addFlashAttribute("message", isNew ? "Employee added." : "Employee updated.");
        return "redirect:/employees";
    }

    // DELETE (ADMIN only) - POST so CSRF protection applies
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        service.delete(id);
        ra.addFlashAttribute("message", "Employee deleted.");
        return "redirect:/employees";
    }
}
