package org.mansar.employeemanagement.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.service.IEmployeeService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeAPI {
    private final IEmployeeService employeeService;

    @Operation(summary = "Create an Employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class)) })
    })
    @PostMapping
    public EmployeeDTO create(@RequestBody @Valid EmployeeRQ employeeRQ) {
        return employeeService.create(employeeRQ);
    }

    @Operation(summary = "Update an Employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class)) })
    })
    @PutMapping("/{employeeId}")
    public EmployeeDTO update(@PathVariable Long employeeId, @RequestBody @Valid EmployeeRQ employeeRQ) {
        return employeeService.update(employeeId, employeeRQ);
    }

    @Operation(summary = "Delete an Employee record")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Employee deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))})
    })
    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId) {
        employeeService.delete(employeeId);
    }

    @Operation(summary = "Get employees list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "success",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = EmployeeDTO.class))})
    })
    @GetMapping
    public List<EmployeeDTO> listEmployees() {
        return employeeService.getEmployees();
    }
}
