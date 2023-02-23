package com.skj.skjapi.controller;

import com.skj.skjapi.models.Employee;
import com.skj.skjapi.models.EmployeePermissions;
import com.skj.skjapi.models.Product;
import com.skj.skjapi.repos.Employees;
import com.skj.skjapi.repos.Products;
import com.skj.skjapi.services.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
public class PortalController {

    @Autowired
    private Employees employeeRoster;

    @Autowired
    private Products productsList;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private EmailSenderService email;

    @GetMapping("/")
    public String landingHTML(Model model) {
        EmployeePermissions employee = (EmployeePermissions) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("user", employee.getUsername().toUpperCase());
        model.addAttribute("permissions", employee);
        return "index";
    }

    @GetMapping("/login")
    public String loginHTML() {
        List<Employee> employeeList = employeeRoster.findAll();

        List<String> alph = Arrays.asList("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
        List<String> nums = Arrays.asList("1","2","3","4","5","6","7","8","9","0");

        StringBuilder password = new StringBuilder();

        if (employeeRoster.findByUsername("admin") == null) {
            while (password.length() < 20) {
                int randomAlph = (int) Math.floor(Math.random() * alph.size());
                int randomNums = (int) Math.floor(Math.random() * nums.size());
                password.append(alph.get(randomAlph)).append(nums.get(randomNums));
            }
        }
        System.out.println(password);

        if (employeeList.size() <= 0 && password.length() == 20) {
            email.sendAdminMail(String.valueOf(password));
            employeeRoster.save(new Employee("admin", encoder.encode(String.valueOf(password)), "ADMIN"));
        }
        return "login";
    }


    @PostMapping("/login/error")
    public String loginErrorHandler(Model model) {
        model.addAttribute("notEmployee", true);
        return "login";
    }


    @GetMapping("/logout")
    public String logoutHandler(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/employee/manager")
    public String employeeManagerHTML() {
        return "employees/employee-manager";
    }

    @GetMapping("/employee/create")
    public String createEmployeeHTML() {
        return "employees/create-employee";
    }

    @PostMapping("/employee/create")
    public String createEmployee(String username, String password, String role, String confirm, Model model) {
        boolean created = false;
        boolean namedAdmin = false;
        boolean userExists = false;
        boolean badPass = false;
        System.out.println(username);
        System.out.println(password);
        System.out.println(role);

        if (!password.equals(confirm)) {
            badPass = true;
        }

        if (username.equalsIgnoreCase("admin") && password.equals(confirm)) {
            namedAdmin = true;
        }

        if (employeeRoster.findByUsername(username) != null && !username.equalsIgnoreCase("admin") && password.equals(confirm)) {
            userExists = true;
        }

        if (!username.equalsIgnoreCase("admin") && !username.equals("") && !password.equals("") && role.equals("EMPLOYEE") && password.equals(confirm) && employeeRoster.findByUsername(username) == null) {
            employeeRoster.save(new Employee(username, encoder.encode(password), role));
            created = true;
            model.addAttribute("createdEmployee", created);
            return "/employees/employee-manager";
        } else {
            model.addAttribute("badPass", badPass);
            model.addAttribute("namedAdmin", namedAdmin);
            model.addAttribute("userExists", userExists);
            return "/employees/create-employee";
        }

    }

    @GetMapping("/employee/view-all")
    public String viewEmployeeHTML(Model model) {
        model.addAttribute("employees", employeeRoster.findAll());
        return "employees/view-employees";
    }

    @GetMapping("/employee/{id}")
    public String individualEmployeePage(@PathVariable String id, Model model) {
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(id));

        String c = employee.getUsername().substring(0, 1);
        String cc = employee.getUsername().substring(1);
        String name = c.toUpperCase() + cc.toLowerCase();
        model.addAttribute("name", name);
        model.addAttribute("employee", employee);
        return "/employees/individual-employee";
    }

    @GetMapping("/employee/update-password")
    public String updateEmployeeHTML(String id, Model model) {
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(id));
        String c = employee.getUsername().substring(0, 1);
        String cc = employee.getUsername().substring(1);
        String name = c.toUpperCase() + cc.toLowerCase();
        model.addAttribute("name", name);
        model.addAttribute("employee", employee);
        return "/employees/individual-employee";
    }

    @PostMapping("/employee/update-password")
    public String updateEmployee(String id, String password, String confirm, Model model) {
        boolean updated;
        boolean empty = false;
        boolean doesntMatch = false;
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(id));
        String c = employee.getUsername().substring(0, 1);
        String cc = employee.getUsername().substring(1);
        String name = c.toUpperCase() + cc.toLowerCase();
        model.addAttribute("name", name);
        model.addAttribute("employee", employee);

        if (password.equals("") || confirm.equals("")) {
            empty = true;
        }
        if (!password.equals(confirm)) {
            doesntMatch = true;
        }
        model.addAttribute("emptyBox", empty);
        model.addAttribute("doesntMatch", doesntMatch);
        if (password.equals(confirm) && !password.equals("")) {
            employeeRoster.updatePassword(encoder.encode(password), Long.parseLong(id));
            updated = true;
            model.addAttribute("updated", updated);
            return "/employees/individual-employee";
        } else {
            updated = false;
            model.addAttribute("updated", updated);
            return "/employees/individual-employee";
        }

    }

    @PostMapping("/employee/delete")
    public String deleteEmployee(String empID) {
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(empID));
        if (employee.getId() != 1 || !employee.getRole().equals("ADMIN")) {
            employeeRoster.delete(employee);
        }
        return "redirect:/employee/view-all";
    }

    @GetMapping("/shop")
    public String shopLandingHTML() {
        return "shop/index";
    }

    @GetMapping("/shop/all-products")
    public String allProductsHTML() {
        return "shop/all-products";
    }

    @GetMapping("/shop/clothing-page")
    public String clothingPageHTML(Model model) {
        model.addAttribute("products", productsList.findAll());
        return "shop/clothing-page";
    }

    @GetMapping("/shop/equipment-page")
    public String equipmentPageHTML(Model model) {
        model.addAttribute("products", productsList.findAll());
        return "shop/equipment-page";
    }

    @GetMapping("/shop/add-clothing")
    public String addClothingHTML() {
        return "shop/add-clothing";
    }

    @PostMapping("/shop/add-clothing")
    public String addClothing(Model model, String name,  String size, String category, String price, String color, String photo) {

        System.out.println(photo);

        boolean wasAdded = false;
        boolean photoIssue = false;
        boolean basicError = false;

        if (photo == null ) {
            model.addAttribute("contactMe", true);
            return "/login";
        }

        if (photo.equals("")) {
            System.out.println("Photo = "+ photo);
            photoIssue = true;
        }
        if (category.equals("") || name.equals("") || size.equals("") || price.equals("$") || color.equals("") && !photoIssue) basicError = true;

        if (!category.equals("") && !name.equals("") && !size.equals("") && !price.equals("$") && !color.equals("") && !photo.equals("")) {
            productsList.save(new Product(category, name, size, color, price, photo));
            wasAdded = true;
            model.addAttribute("clothAdded", wasAdded);
            return "/shop/index";
        } else {
            model.addAttribute("error", basicError);
            model.addAttribute("photoIssue", photoIssue);
            return "shop/add-clothing";
        }
    }

    @GetMapping("/shop/add-equipment")
    public String addEquipmentHTML() {
        return "shop/add-equipment";
    }

    @PostMapping("/shop/add-equipment")
    public String addEquipment(Model model, String name, String size, String category, String price, String color, String photo) {

        boolean wasAdded = false;
        boolean photoIssue = false;
        boolean basicError = false;

        if (photo == null) {
            System.out.println("Photo = "+ photo);
            photoIssue = true;
        }

        if (category.equals("") || name.equals("") || size.equals("") || price.equals("$") || color.equals("") && !photoIssue) basicError = true;

        if (!category.equals("") && !name.equals("") && !size.equals("") && !price.equals("$") && !color.equals("") && photo != null) {
            productsList.save(new Product(category, name, size, color, price, photo));
            wasAdded = true;
            model.addAttribute("clothAdded", wasAdded);
            return "/shop/index";
        } else {
            model.addAttribute("error", basicError);
            model.addAttribute("photoIssue", photoIssue);
            return "shop/add-equipment";
        }
    }


    @GetMapping("/messages")
    public String messageCenterHTML() {
        return "messages/index";
    }

}
