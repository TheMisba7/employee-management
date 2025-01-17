package org.mansar.employeemanagement.dto;

import lombok.Getter;
import lombok.Setter;
import org.mansar.employeemanagement.core.EmployeeStatus;

import java.util.Date;

@Setter @Getter
public  class FilterParam {
    private  Long employeeId;
    private  String department;
    private  String jobTitle;
    private  EmployeeStatus status;
    private  Long departmentId;
    private  Date hireDate;
}
