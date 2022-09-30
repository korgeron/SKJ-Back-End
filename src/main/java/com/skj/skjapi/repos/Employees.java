package com.skj.skjapi.repos;

import com.skj.skjapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Employees extends JpaRepository <Employee , Long> {
    Employee findByUsername(String username);
}
