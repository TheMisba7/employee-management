package org.mansar.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.mansar.employeemanagement.model.Role;

@Setter @Getter
public class UserDTO extends EmployeeDTO {
    private String username;
    private Role role;
}
