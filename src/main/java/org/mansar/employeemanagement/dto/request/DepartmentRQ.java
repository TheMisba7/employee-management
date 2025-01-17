package org.mansar.employeemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRQ(
        @NotBlank(message = "name is required")
        String name,
        String description,
        Long leaderId) {
}
