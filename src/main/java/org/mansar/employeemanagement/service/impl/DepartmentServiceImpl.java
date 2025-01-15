package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dao.DepartmentDao;
import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.model.Department;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements IDepartmentService {
    private final DepartmentDao departmentDao;
    private final IUserService userService;
    @Override
    public void create(DepartmentRQ request) {
        Department department = new Department();
        department.setName(request.name());
        department.setDescription(request.description());
        if (request.leaderId() != null) {
            department.setLeader(userService.getById(request.leaderId()));
        }
        departmentDao.save(department);
    }

    @Override
    public List<Department> listDepartments() {
        return departmentDao.findAll();
    }

    @Override
    public Department getDepartment(Long id) {
        return departmentDao.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id, "department"));
    }

    @Override
    public void delete(Long dptId) {

    }
}
