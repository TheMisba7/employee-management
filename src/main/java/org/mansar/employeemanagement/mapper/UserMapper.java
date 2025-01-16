package org.mansar.employeemanagement.mapper;

import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EmployeeMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    void fromDTO(UserRQ userRQ, @MappingTarget User user);
}
