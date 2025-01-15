package org.mansar.employeemanagement.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DepartmentRQ(
        @NotBlank(message = "name is required")
        String name,
        String description,
        @NotNull(message = "department leader most be specified ")
        Long leaderId) {
}
