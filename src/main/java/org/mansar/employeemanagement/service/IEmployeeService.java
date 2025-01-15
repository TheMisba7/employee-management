package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.model.Employee;

import java.util.List;

public interface IEmployeeService {
    //todo add admin or hr role
    void create(EmployeeRQ employeeDTO);
    List<EmployeeDTO> getEmployees();
    void update(Long employeeId, EmployeeRQ employeeRQ);
    Employee getEmployee(Long employeeId);
    //todo admin hr role
    void delete(Long employee);
}
