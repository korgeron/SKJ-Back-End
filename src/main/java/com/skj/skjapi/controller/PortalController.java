package com.skj.skjapi.controller;

import com.skj.skjapi.models.Employee;
import com.skj.skjapi.repos.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class PortalController {

    @Autowired
    private Employees employeeRoster;

    @Autowired
    private PasswordEncoder encoder;

    @GetMapping()
    public String landingHTML() {
        return "index";
    }

    @GetMapping("login")
    public String loginHTML() {
        List<Employee> employeeList = employeeRoster.findAll();
        if (employeeList.size() <= 0) {
            employeeRoster.save(new Employee("admin", encoder.encode("1q2w3e4r5t6y7u8i9o0p"), "ADMIN"));
        }
        return "login";
    }

    @GetMapping("logout")
    public String logoutHandler(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

}
