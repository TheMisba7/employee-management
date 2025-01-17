package org.mansar.employeemanagement.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.dto.request.PermissionRQ;
import org.mansar.employeemanagement.service.IPermissionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionAPI {
    private final IPermissionService permissionService;

    @PostMapping()
    public void addPermissionToRole(@RequestBody @Valid PermissionRQ permissionRQ) {
        permissionService.addPermissionToRole(permissionRQ);
    }

    @DeleteMapping("/{permissionId}/roles/{roleId}")
    public void removePermissionFromRole(@PathVariable Long roleId, @PathVariable Long permissionId) {
        permissionService.removePermissionFromRole(roleId, permissionId);
    }

    @PostMapping("/{permissionId}/attributes")
    public void addAttributesToPermission(@PathVariable Long permissionId, @RequestBody Set<EmployeeAttribute> attributes) {
        permissionService.addAttributesToPermission(permissionId, attributes);
    }

    @DeleteMapping("/{permissionId}/attributes")
    public void removeAttributesFromPermission(@PathVariable Long permissionId, @RequestBody Set<EmployeeAttribute> attributes) {
        permissionService.removeAttributesFromPermission(permissionId, attributes);
    }
}
