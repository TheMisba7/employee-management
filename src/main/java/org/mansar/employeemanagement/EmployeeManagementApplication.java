package org.mansar.employeemanagement;

import org.mansar.employeemanagement.core.EmployeeAttribute;
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
            Role manager = new Role();

            Permission  permission = new Permission();
            permission.setName(PermissionEnum.UPDATE);
            permission.getAttributes().add(EmployeeAttribute.FIRSTNAME);
            permission.getAttributes().add(EmployeeAttribute.LASTNAME);
            permission.getAttributes().add(EmployeeAttribute.CONTACT);
            permission.getAttributes().add(EmployeeAttribute.JOB_TITLE);
            manager.setName(RoleEnum.MANAGER);
            manager.addPermission(permission);
            roleDao.save(manager);

            Role admin = new Role();
            admin.setName(RoleEnum.ADMIN);
            Permission admPermission = new Permission();
            admPermission.setName(PermissionEnum.ALL);
            admPermission.getAttributes().add(EmployeeAttribute.ALL);

            Role hr = new Role();

            Permission hrPermission = new Permission();
            hrPermission.setName(PermissionEnum.ALL);
            hrPermission.getAttributes().add(EmployeeAttribute.ALL);
            hr.setName(RoleEnum.RH);
            hr.addPermission(hrPermission);
            admin.addPermission(admPermission);

            roleDao.save(admin);
            roleDao.save(hr);

        };
    }
}
