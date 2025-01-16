package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.mapper.EmployeeMapper;
import org.mansar.employeemanagement.core.ABACEngine;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.RoleEnum;
import org.mansar.employeemanagement.dao.EmployeeDao;
import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.exception.PermissionDeniedException;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.model.Employee;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.mansar.employeemanagement.service.IEmployeeService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;
    private final IDepartmentService departmentService;

    private void mapAndSave(EmployeeRQ source, Employee target) {
        employeeMapper.fromDTO(source, target);

        if (source.getDepartmentId() != null) {
            if (target.getDepartment() == null) {
                target.setDepartment(departmentService.getDepartment(source.getDepartmentId()));
            } else if (!target.getDepartment().getId().equals(source.getDepartmentId())) {
                target.setDepartment(departmentService.getDepartment(source.getDepartmentId()));
            }
        }
        employeeDao.save(target);
    }
    @Override
    public void create(EmployeeRQ employeeRQ) {
        Employee employee = new Employee();
        mapAndSave(employeeRQ, employee);
    }

    @Override
    public List<EmployeeDTO> getEmployees() {
        User actingUser = IUserService.getCurrentUser();
        List<Employee> employees;
        if (!actingUser.getRole().getName().equals(RoleEnum.MANAGER))
            employees = employeeDao.findAll();
        else
            employees = employeeDao.findByDepartmentId(actingUser.getDepartment().getId());

        return employeeMapper.toDTO(employees);
    }

    @Override
    public void update(Long employeeId, EmployeeRQ employeeRQ) {
        User actingUser = IUserService.getCurrentUser();
        Employee subjectEmployee = getEmployee(employeeId);
        if (!ABACEngine.canAccessEmployee(subjectEmployee, actingUser))
            throw new PermissionDeniedException(PermissionEnum.UPDATE);
        ABACEngine.performAttributeAccessCheck(employeeRQ, PermissionEnum.UPDATE, actingUser.getRole());
        mapAndSave(employeeRQ, subjectEmployee);
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        return employeeDao.findById(employeeId)
                .orElseThrow(() -> new RecordNotFoundException(employeeId, "employee"));
    }

    @Override
    public void delete(Long employee) {
        employeeDao.deleteById(employee);
    }
}
