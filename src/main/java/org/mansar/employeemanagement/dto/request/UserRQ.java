package org.mansar.employeemanagement.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRQ extends EmployeeRQ {
    @NotBlank(message = "username is required")
    @Email(regexp = "^(.+)@(\\S+)$", message = "username should be a valid email.")
    private String username;
    @NotBlank(message = "password is required")
    private String password;
    @NotNull(message = "role is required")
    private Long roleId;
}
