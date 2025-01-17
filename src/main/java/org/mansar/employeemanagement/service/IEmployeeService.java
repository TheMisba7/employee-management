package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.core.RoleEnum;
import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.FilterParam;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.model.Employee;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

/**
 * The {@code IEmployeeService} interface provides methods for managing employees.
 * <p>
 * Each method is secured with the {@link Secured} annotation, indicating the minimum role required to execute the method.
 * The roles are defined in the {@link RoleEnum} enumeration.
 * </p>
 * <p>
 * Role hierarchy:
 * <ul>
 *   <li>{@code ADMIN} > {@code HR} > {@code MANAGER} ></li>
 * </ul>
 * </p>
 */
public interface IEmployeeService {
    @Secured({"HR"})
    EmployeeDTO create(EmployeeRQ employeeDTO);
    @Secured({"MANAGER"})
    List<EmployeeDTO> getEmployees(FilterParam filterParam);
    @Secured({"MANAGER"})
    EmployeeDTO update(Long employeeId, EmployeeRQ employeeRQ);
    Employee getEmployee(Long employeeId);
    @Secured({"HR"})
    void delete(Long employee);
}
