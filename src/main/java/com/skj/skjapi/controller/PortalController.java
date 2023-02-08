package com.skj.skjapi.controller;

import com.skj.skjapi.models.Employee;
import com.skj.skjapi.models.EmployeePermissions;
import com.skj.skjapi.models.Product;
import com.skj.skjapi.repos.Employees;
import com.skj.skjapi.repos.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class PortalController {

    @Autowired
    private Employees employeeRoster;

    @Autowired
    private Products productsList;

    @Autowired
    private PasswordEncoder encoder;

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
//        List<Product> products = productsList.findAll();
//        if (products.size() > 0){
//            productsList.deleteAll();
//        }
        if (employeeList.size() <= 0) {
            employeeRoster.save(new Employee("admin", encoder.encode("1q2w3e4r5t6y7u8i9o0p"), "ADMIN"));
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
    public String employeeManagerHTML(){
        return "employees/employee-manager";
    }

    @GetMapping("/employee/create")
    public String createEmployeeHTML() {
        return "employees/create-employee";
    }

    @PostMapping("/employee/create")
    public String createEmployee(String username, String password, String role, String confirm) {
        System.out.println(username);
        System.out.println(password);
        System.out.println(role);
        if (!username.equals("") && !password.equals("") && role.equals("EMPLOYEE") && password.equals(confirm)) {
            employeeRoster.save(new Employee(username, encoder.encode(password), role));
            return "redirect:/";
        } else {
            return "redirect:/employee/create";
        }

    }

    @GetMapping("/employee/view-all")
    public String viewEmployeeHTML(Model model){
        model.addAttribute("employees",employeeRoster.findAll());
        return "employees/view-employees";
    }

    @GetMapping("/employee/{id}")
    public String individualEmployeePage(@PathVariable String id, Model model){
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(id));
        String c = employee.getUsername().substring(0,1);
        String cc = employee.getUsername().substring(1);
        String name = c.toUpperCase() + cc.toLowerCase();
        model.addAttribute("name", name);
        model.addAttribute("employee", employee);
        return "/employees/individual-employee";
    }

    @PostMapping("/employee/update-password")
    public String updateEmployee(String id, String password, String confirm, Model model){
        Employee employee = employeeRoster.getReferenceById(Long.parseLong(id));
        String c = employee.getUsername().substring(0,1);
        String cc = employee.getUsername().substring(1);
        String name = c.toUpperCase() + cc.toLowerCase();
        model.addAttribute("name", name);
        model.addAttribute("employee", employee);

        if (password.equals(confirm) && !password.equals("")) {
            employeeRoster.updatePassword(encoder.encode(password), Long.parseLong(id));
            return "/employees/individual-employee";
        } else {
            return "/employees/individual-employee";
        }


    }

    @GetMapping("/shop")
    public String shopLandingHTML() {
        return "shop/index";
    }

    @GetMapping("/shop/all-products")
    public String allProductsHTML() {
//        model.addAttribute("products", productsList.findAll());
        return "shop/all-products";
    }

    @GetMapping("/shop/clothing-page")
    public String clothingPageHTML(Model model) {
        model.addAttribute("products", productsList.findAll());
        return "shop/clothing-page";
    }

    @GetMapping("/shop/equipment-page")
    public String equipmentPageHTML(Model model){
        model.addAttribute("products", productsList.findAll());
        return "shop/equipment-page";
    }

    @GetMapping("/shop/add-clothing")
    public String addClothingHTML() {
        return "shop/add-clothing";
    }

    @PostMapping("/shop/add-clothing")
    public String addClothing(Model model, @RequestParam(name = "name") String name, @RequestParam(name = "size") String size, @RequestParam(name = "category") String category, @RequestParam(name = "price") String price, @RequestParam(name = "color") String color) {

        if (!category.equals("") && !name.equals("") && !size.equals("") && !price.equals("$") && !color.equals("")){
            productsList.save(new Product(category, name, size, color, price));
            return "redirect:/shop/all-products";
        }
        else{
            model.addAttribute("error", true);
            return "shop/add-clothing";
        }
    }

    @GetMapping("/shop/add-equipment")
    public String addEquipmentHTML() {
        return "shop/add-equipment";
    }

    @PostMapping("/shop/add-equipment")
    public String addEquipment(Model model, @RequestParam(name = "name") String name, @RequestParam(name = "size") String size, @RequestParam(name = "category") String category, @RequestParam(name = "price") String price, @RequestParam(name = "color") String color){

        if (!category.equals("") && !name.equals("") && !size.equals("") && !price.equals("$") && !color.equals("")){
            productsList.save(new Product(category, name, size, color, price));
            return "redirect:/shop/all-products";
        }
        else{
            model.addAttribute("error", true);
            return "shop/add-clothing";
        }

    }


    @GetMapping("/messages")
    public String messageCenterHTML() {
        return "messages/index";
    }

}
