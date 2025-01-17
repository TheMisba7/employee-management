package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.core.ABACEngine;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.RoleEnum;
import org.mansar.employeemanagement.core.Trail;
import org.mansar.employeemanagement.dao.EmployeeDao;
import org.mansar.employeemanagement.dao.EmployeeSpecification;
import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.FilterParam;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.exception.PermissionDeniedException;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.mapper.EmployeeMapper;
import org.mansar.employeemanagement.model.Employee;
import org.mansar.employeemanagement.model.Permission;
import org.mansar.employeemanagement.model.Role;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.mansar.employeemanagement.service.IEmployeeService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;
    private final IDepartmentService departmentService;

    public Employee mapAndSave(EmployeeRQ source, Employee target) {
        employeeMapper.fromDTO(source, target);

        if (source.getDepartmentId() != null) {
            if (target.getDepartment() == null) {
                target.setDepartment(departmentService.getDepartment(source.getDepartmentId()));
            } else if (!target.getDepartment().getId().equals(source.getDepartmentId())) {
                target.setDepartment(departmentService.getDepartment(source.getDepartmentId()));
            }
        }
        return employeeDao.save(target);
    }
    @Override
    @Trail(action = PermissionEnum.CREATE)
    public EmployeeDTO create(EmployeeRQ employeeRQ) {
        Employee employee = new Employee();
        return employeeMapper.toDTO(mapAndSave(employeeRQ, employee));
    }

    @Override
    public List<EmployeeDTO> getEmployees(FilterParam filterParam) {
        User actingUser = IUserService.getCurrentUser();
        List<Employee> employees;
        Role role = actingUser.getRole();
        Specification<Employee> sepc = EmployeeSpecification.filterBy(filterParam);
        if (role.getName().equals(RoleEnum.ADMIN) || role.getName().equals(RoleEnum.HR))
            return employeeMapper.toDTO(employeeDao.findAll(sepc));
        else {
            Permission readPermission = ABACEngine.getRequiredPermission(PermissionEnum.READ, role);
            if (readPermission == null)
                throw new PermissionDeniedException(PermissionEnum.READ);
            filterParam.setDepartmentId(actingUser.getDepartment().getId());
            employees = employeeDao.findAll(sepc);
            return employees.stream().map(employeeMapper::toDTO)
                    .peek(em -> ABACEngine.removeUnauthorizedAttributes(em, readPermission.getAttributes()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    @Trail(action = PermissionEnum.UPDATE)
    public EmployeeDTO update(Long employeeId, EmployeeRQ employeeRQ) {
        User actingUser = IUserService.getCurrentUser();
        Employee subjectEmployee = getEmployee(employeeId);
        if (!ABACEngine.canAccessEmployee(subjectEmployee, actingUser))
            throw new PermissionDeniedException(PermissionEnum.UPDATE);
        ABACEngine.performAttributeAccessCheck(employeeRQ, PermissionEnum.UPDATE, actingUser.getRole());
        return employeeMapper.toDTO(mapAndSave(employeeRQ, subjectEmployee));
    }

    @Override
    public Employee getEmployee(Long employeeId) {
        return employeeDao.findById(employeeId)
                .orElseThrow(() -> new RecordNotFoundException(employeeId, "employee"));
    }

    @Override
    @Trail(action = PermissionEnum.DELETE)
    public void delete(Long employee) {
        employeeDao.deleteById(employee);
    }
}
