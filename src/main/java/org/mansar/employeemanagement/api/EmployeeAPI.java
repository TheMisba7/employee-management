package org.mansar.employeemanagement.api;

import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.service.IEmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeAPI {
    private final IEmployeeService employeeService;
}
