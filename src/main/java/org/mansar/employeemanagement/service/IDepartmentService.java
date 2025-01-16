package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.model.Department;

import java.util.List;

//todo add method level security on all methods, only admin can manage this the department
public interface IDepartmentService {
    Department create(DepartmentRQ request);
    List<Department> listDepartments();
    Department getDepartment(Long id);

    void delete(Long dptId);
}
