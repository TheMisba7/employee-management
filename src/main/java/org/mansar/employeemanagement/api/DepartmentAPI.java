package org.mansar.employeemanagement.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.model.Department;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/departments")
public class DepartmentAPI {
    private final IDepartmentService departmentService;

    @PostMapping()
    public void create(@Valid DepartmentRQ departmentRQ) {
        departmentService.create(departmentRQ);
    }

    @GetMapping
    public List<Department> listDepartment() {
        return departmentService.listDepartments();
    }

    @GetMapping("/{dptId}")
    public Department getDepartment(@PathVariable("dptId") Long dptId) {
        return departmentService.getDepartment(dptId);
    }

    @DeleteMapping("/{dptId}")
    public void deleteDepartment(@PathVariable("dptId") Long dptId) {
        departmentService.delete(dptId);
    }

}
