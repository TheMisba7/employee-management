package org.mansar.employeemanagement.mapper;


import org.mansar.employeemanagement.dto.DepartmentDTO;
import org.mansar.employeemanagement.model.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface DepartmentMapper {
    @Mapping(target = "leader.department", source = "leader.department.name")
    DepartmentDTO toDTO(Department department);

    List<DepartmentDTO> toDTO(List<Department> departments);
}