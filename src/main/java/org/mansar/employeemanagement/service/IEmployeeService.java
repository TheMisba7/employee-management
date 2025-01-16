package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.model.Employee;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface IEmployeeService {
    @Secured({"ADMIN", "HR"})
    EmployeeDTO create(EmployeeRQ employeeDTO);
    List<EmployeeDTO> getEmployees();
    EmployeeDTO update(Long employeeId, EmployeeRQ employeeRQ);
    Employee getEmployee(Long employeeId);
    @Secured({"ADMIN","HR"})
    void delete(Long employee);
}
