package org.mansar.employeemanagement.service;

import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.dto.request.PermissionRQ;
import org.springframework.security.access.annotation.Secured;

import java.util.Set;

public interface IPermissionService {
    @Secured("ADMIN")
    void addPermissionToRole(PermissionRQ permissionRQ);
    @Secured("ADMIN")
    void removePermissionFromRole(Long roleId, Long permissionId);
    @Secured("ADMIN")
    void addAttributesToPermission(Long permissionId, Set<EmployeeAttribute> attributes);
    @Secured("ADMIN")
    void removeAttributesFromPermission(Long permissionId, Set<EmployeeAttribute> attributes);
}
