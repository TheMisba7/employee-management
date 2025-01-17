package org.mansar.employeemanagement.service.impl;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.dao.PermissionDao;
import org.mansar.employeemanagement.dao.RoleDao;
import org.mansar.employeemanagement.dto.request.PermissionRQ;
import org.mansar.employeemanagement.exception.RecordNotFoundException;
import org.mansar.employeemanagement.model.Permission;
import org.mansar.employeemanagement.model.Role;
import org.mansar.employeemanagement.service.IPermissionService;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements IPermissionService {
    private final RoleDao roleDao;
    private final PermissionDao permissionDao;
    public Role getRoleById(Long id) {
        return roleDao.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id, "role"));
    }

    public Permission getPermissionByRole(Long id) {
        return permissionDao.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(id, "permission"));
    }
    @Override
    public void addPermissionToRole(PermissionRQ permissionRQ) {
        Role role = getRoleById(permissionRQ.getRoleId());
        Permission permission = new Permission();
        permission.setName(permissionRQ.getPermission());
        permission.setAttributes(permissionRQ.getAttributes());
        role.addPermission(permission);
        roleDao.save(role);
    }

    @Override
    public void removePermissionFromRole(Long roleId, Long permissionId) {
        Role role = getRoleById(roleId);
        for (Permission permission: role.getPermissions()) {
            if (permission.getId().equals(permissionId)) {
                role.removePermission(permission);
                break;
            }
        }
        roleDao.save(role);
    }

    @Override
    public void addAttributesToPermission(Long permissionId, Set<EmployeeAttribute> attributes) {
        Permission permission = getPermissionByRole(permissionId);
        permission.addAttributes(attributes);
        permissionDao.save(permission);
    }

    @Override
    public void removeAttributesFromPermission(Long permissionId, Set<EmployeeAttribute> attributes) {
        Permission permission = getPermissionByRole(permissionId);
        attributes.forEach(permission::removeAttribute);
        permissionDao.save(permission);
    }
}
