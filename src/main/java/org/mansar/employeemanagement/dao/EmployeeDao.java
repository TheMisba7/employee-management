package org.mansar.employeemanagement.dao;

import org.mansar.employeemanagement.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDao extends JpaRepository<Employee, Long>, EmployeeCustomDao {
    List<Employee> findByDepartmentId(Long departmentId);
}
