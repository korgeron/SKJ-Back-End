package com.skj.skjapi.services;

import com.skj.skjapi.models.Employee;
import com.skj.skjapi.models.EmployeePermissions;
import com.skj.skjapi.repos.Employees;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeePermissionServices implements UserDetailsService {

    @Autowired
    private Employees employeeRoster;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRoster.findByUsername(username);
        return new EmployeePermissions(employee);
    }
}
