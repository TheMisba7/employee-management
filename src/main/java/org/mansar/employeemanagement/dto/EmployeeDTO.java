package org.mansar.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mansar.employeemanagement.model.Address;
import org.mansar.employeemanagement.model.Contact;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmployeeDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private String jobTitle;
    private String department;
    private Contact contact;
    private Address address;
}
