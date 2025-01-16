package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.DepartmentDTO;
import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.model.Department;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface IDepartmentService {
    @Secured({"ADMIN"})
    Department create(DepartmentRQ request);
    @Secured({"ADMIN", "HR"})
    Department update(Long dptId, DepartmentRQ request);
    @Secured({"ADMIN", "HR"})
    List<DepartmentDTO> listDepartments();
    @Secured({"ADMIN", "HR"})
    Department getDepartment(Long id);
    @Secured({"ADMIN"})
    void delete(Long dptId);
}
