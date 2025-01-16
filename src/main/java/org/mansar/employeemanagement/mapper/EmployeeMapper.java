package org.mansar.employeemanagement.mapper;

import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
    Employee fromDTO(EmployeeRQ employeeRQ);

    void fromDTO(EmployeeRQ employeeRQ, @MappingTarget Employee employee);
    @Mapping(target = "department", source = "department.name")
    EmployeeDTO toDTO(Employee employee);
    List<EmployeeDTO> toDTO(List<Employee> employees);
}
