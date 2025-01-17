package org.mansar.employeemanagement.service.impl;

import org.mansar.employeemanagement.dao.DepartmentDao;
import org.mansar.employeemanagement.dto.DepartmentDTO;
import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.mapper.DepartmentMapper;
import org.mansar.employeemanagement.model.Department;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.mansar.employeemanagement.service.IUserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements IDepartmentService {
    private final DepartmentDao departmentDao;
    private final IUserService userService;
    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentDao departmentDao,
                                 @Lazy IUserService userService,
                                 DepartmentMapper departmentMapper) {
        this.departmentDao = departmentDao;
        this.userService = userService;
        this.departmentMapper = departmentMapper;
    }

    private void mapAndSAve(DepartmentRQ departmentRQ, Department department) {
        department.setName(departmentRQ.name());
        department.setDescription(departmentRQ.description());
        if (departmentRQ.leaderId() != null)
            department.setLeader(userService.getById(departmentRQ.leaderId()));
        department = departmentDao.save(department);
    }
    @Override
    public Department create(DepartmentRQ request) {
        Department department = new Department();
        mapAndSAve(request, department);
        return department;
    }

    @Override
    public Department update(Long dptId, DepartmentRQ request) {
        Department department = getDepartment(dptId);
        mapAndSAve(request, department);
        return department;
    }

    @Override
    public List<DepartmentDTO> listDepartments() {
        return departmentMapper.toDTO(departmentDao.findAll());
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
