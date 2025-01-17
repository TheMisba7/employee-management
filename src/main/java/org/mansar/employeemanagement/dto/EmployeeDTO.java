package org.mansar.employeemanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mansar.employeemanagement.core.Attribute;
import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.EmployeeStatus;
import org.mansar.employeemanagement.model.Address;
import org.mansar.employeemanagement.model.Contact;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmployeeDTO {
    @Attribute(value = EmployeeAttribute.ID)
    private Long id;
    @Attribute(value = EmployeeAttribute.FIRSTNAME)
    private String firstname;
    @Attribute(value = EmployeeAttribute.LASTNAME)
    private String lastname;
    @Attribute(value = EmployeeAttribute.JOB_TITLE)
    private String jobTitle;
    @Attribute(value = EmployeeAttribute.DEPARTMENT)
    private String department;
    @Attribute(value = EmployeeAttribute.CONTACT)
    private Contact contact;
    @Attribute(value = EmployeeAttribute.ADDRESS)
    private Address address;
    @Attribute(value = EmployeeAttribute.STATUS)
    private EmployeeStatus status;
}
