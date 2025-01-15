package org.mansar.employeemanagement.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mansar.employeemanagement.core.EmployeeAttribute;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class EmployeeAttributesSetConverter implements AttributeConverter<Set<EmployeeAttribute>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<EmployeeAttribute> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }
        return attributes.stream()
                .map(EmployeeAttribute::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<EmployeeAttribute> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(String::trim)
                .map(EmployeeAttribute::valueOf)
                .collect(Collectors.toSet());
    }
}