package org.mansar.employeemanagement.mapper;

import org.mansar.employeemanagement.dto.UserDTO;
import org.mansar.employeemanagement.dto.request.UserRQ;
import org.mansar.employeemanagement.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = EmployeeMapper.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "username", ignore = true)
    void fromDTO(UserRQ userRQ, @MappingTarget User user);
    @Mapping(target = "department", source = "department.name")
    UserDTO toDTO(User user);
    List<UserDTO> toDTO(List<User> users);
}
