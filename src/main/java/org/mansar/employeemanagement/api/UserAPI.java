package org.mansar.employeemanagement.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.service.IUserService;
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
@RequestMapping("/users")
public class UserAPI {
    private final IUserService userService;

    @PostMapping
    public void create(@RequestBody @Valid UserRQ userRQ) {
        userService.save(userRQ);
    }

    @PutMapping("/{employeeId}")
    public void update(@PathVariable Long employeeId, @RequestBody @Valid UserRQ userRQ) {
        userService.update(employeeId, userRQ);
    }

    @DeleteMapping("/{employeeId}")
    public void delete(@PathVariable Long employeeId) {
        userService.delete(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> listEmployees() {
        return null;
    }
}
