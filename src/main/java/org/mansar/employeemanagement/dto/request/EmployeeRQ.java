package org.mansar.employeemanagement.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mansar.employeemanagement.core.Attribute;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.model.Address;
import org.mansar.employeemanagement.model.Contact;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class EmployeeRQ {
    @NotBlank(message = "firstname is required")
    @Attribute(value = EmployeeAttribute.FIRSTNAME)
    private String firstname;
    @NotBlank(message = "lastname is required")
    @Attribute(value = EmployeeAttribute.LASTNAME)
    private String lastname;
    @NotBlank(message = "job title is required")
    @Attribute(value = EmployeeAttribute.JOB_TITLE)
    private String jobTitle;
    @NotBlank(message = "department is required")
    @Attribute(value = EmployeeAttribute.DEPARTMENT)
    private Long departmentId;
    @Valid
    @Attribute(value = EmployeeAttribute.CONTACT)
    private Contact contact;
    @Attribute(value = EmployeeAttribute.ADDRESS)
    private Address address;
}
