package org.mansar.employeemanagement.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.mansar.employeemanagement.core.EmployeeAttributes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class EmployeeAttributesSetConverter implements AttributeConverter<Set<EmployeeAttributes>, String> {
    private static final String SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(Set<EmployeeAttributes> attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }
        return attributes.stream()
                .map(EmployeeAttributes::name)
                .collect(Collectors.joining(SEPARATOR));
    }

    @Override
    public Set<EmployeeAttributes> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) {
            return new HashSet<>();
        }
        return Arrays.stream(dbData.split(SEPARATOR))
                .map(String::trim)
                .map(EmployeeAttributes::valueOf)
                .collect(Collectors.toSet());
    }
}