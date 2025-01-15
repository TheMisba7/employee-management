package org.mansar.employeemanagement;

import org.mansar.employeemanagement.core.EmployeeAttributes;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.RoleEnum;
import org.mansar.employeemanagement.dao.RoleDao;
import org.mansar.employeemanagement.model.Permission;
import org.mansar.employeemanagement.model.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(RoleDao roleDao) {
        return  args -> {
            roleDao.deleteAll();
            Role role = new Role();

            Permission  permission = new Permission();
            permission.setName(PermissionEnum.UPDATE);
            permission.getAttributes().add(EmployeeAttributes.FIRSTNAME);
            permission.getAttributes().add(EmployeeAttributes.LASTNAME);
            permission.getAttributes().add(EmployeeAttributes.CONTACT);
            permission.getAttributes().add(EmployeeAttributes.JOB_TITLE);
            role.setName(RoleEnum.MANAGER);
            role.addPermission(permission);

            roleDao.save(role);

        };
    }
}
