package org.mansar.employeemanagement.core;

import org.mansar.employeemanagement.dto.EmployeeDTO;
import org.mansar.employeemanagement.dto.request.EmployeeRQ;
import org.mansar.employeemanagement.exception.AttributeAccessDeniedException;
import org.mansar.employeemanagement.exception.PermissionDeniedException;
import org.mansar.employeemanagement.model.Employee;
import org.mansar.employeemanagement.model.Permission;
import org.mansar.employeemanagement.model.Role;
import org.mansar.employeemanagement.model.User;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * Attribute based access control engine
 */
public class ABACEngine {

    public static boolean canAccessEmployee(Employee employee, User actingUser) {
        if (actingUser.getRole().getName().equals(RoleEnum.ADMIN)
                || actingUser.getRole().getName().equals(RoleEnum.HR))
            return true;
        return employee.getDepartment().getId()
                .equals(actingUser.getDepartment().getId());
    }
    public static void performAttributeAccessCheck(EmployeeRQ employeeRQ, PermissionEnum action, Role role) {
        if (!role.getName().equals(RoleEnum.MANAGER))
            return;

        Permission userPermission = getRequiredPermission(action, role);
        if (userPermission == null)
            throw new PermissionDeniedException(action);

        for (Field field: employeeRQ.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(employeeRQ);
                if (fieldValue != null) {
                    Attribute attribute = field.getAnnotation(Attribute.class);
                    if (attribute != null) {
                        EmployeeAttribute targetAttribute = attribute.value();
                        if (!hasAccessToAttribute(targetAttribute, userPermission) &&
                                !targetAttribute.equals(EmployeeAttribute.DEPARTMENT))
                            throw new AttributeAccessDeniedException(targetAttribute);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void removeUnauthorizedAttributes(EmployeeDTO employeeRQ, Set<EmployeeAttribute> attributes) {
        if (attributes.isEmpty() || attributes.contains(EmployeeAttribute.ALL))
            return;
        for (Field field: employeeRQ.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {
                Object fieldValue = field.get(employeeRQ);
                if (fieldValue != null) {
                    Attribute attribute = field.getAnnotation(Attribute.class);
                    if (attribute == null ||  !attributes.contains(attribute.value())) {
                        field.set(employeeRQ, null);
                    }
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Permission getRequiredPermission(PermissionEnum action, Role role) {
        for (Permission permission: role.getPermissions()) {
            if (permission.getName().equals(action))
                return permission;
        }
        return null;
    }

    public static boolean hasAccessToAttribute(EmployeeAttribute attribute, Permission userPermission) {
        for (EmployeeAttribute attr: userPermission.getAttributes()) {
            if (attr.equals(attribute))
                return true;
        }
        return false;
    }
}
