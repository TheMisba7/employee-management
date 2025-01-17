package org.mansar.employeemanagement.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.PermissionEnum;

import java.util.Set;

@Getter @Setter
public class PermissionRQ {
    @NotNull(message = "role is required")
    private Long roleId;
    @NotNull(message = "permission is required")
    private PermissionEnum permission;
    @NotNull(message = "attributes are required")
    private Set<EmployeeAttribute> attributes;
}
