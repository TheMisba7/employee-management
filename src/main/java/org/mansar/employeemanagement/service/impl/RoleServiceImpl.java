package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dao.RoleDao;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.model.Role;
import org.mansar.employeemanagement.service.IRoleService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {
    private final RoleDao roleDao;
    @Override
    public Role getRoleById(Long id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id, "role"));
    }
}
