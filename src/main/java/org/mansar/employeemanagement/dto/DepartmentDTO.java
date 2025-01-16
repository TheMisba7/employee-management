package org.mansar.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DepartmentDTO {
    private Long id;
    private String name;
    private String description;
    private EmployeeDTO leader;
}
