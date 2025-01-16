package org.mansar.employeemanagement.dao.impl;

import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.dao.EmployeeCustomDao;
import org.mansar.employeemanagement.model.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class EmployeeCustomDaoImpl implements EmployeeCustomDao {

    @Override
    public List<Employee> selectAttributes(Set<EmployeeAttribute> attributes) {
        return null;
    }

    @Override
    public Employee selectAttributesByEmployeeId(Long employeeId, Set<EmployeeAttribute> attributes) {
        return null;
    }
}
