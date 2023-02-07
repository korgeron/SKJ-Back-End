package com.skj.skjapi.repos;

import com.skj.skjapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface Employees extends JpaRepository <Employee , Long> {
    Employee findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update Employee e set e.password = :password where e.id = :id")
    Integer updatePassword (String password, Long id);
}
