package org.mansar.employeemanagement.dao;

import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.model.Employee;

import java.util.List;
import java.util.Set;

public interface EmployeeCustomDao {
    List<Employee> selectAttributes(Set<EmployeeAttribute> attributes);
    Employee selectAttributesByEmployeeId(Long employeeId, Set<EmployeeAttribute> attributes);
}
