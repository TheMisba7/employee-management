package org.mansar.employeemanagement;

import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.EmployeeStatus;
import org.mansar.employeemanagement.core.PermissionEnum;
import org.mansar.employeemanagement.core.RoleEnum;
import org.mansar.employeemanagement.dao.RoleDao;
import org.mansar.employeemanagement.dao.UserDao;
import org.mansar.employeemanagement.dto.request.DepartmentRQ;
import org.mansar.employeemanagement.model.Department;
import org.mansar.employeemanagement.model.Permission;
import org.mansar.employeemanagement.model.Role;
import org.mansar.employeemanagement.model.User;
import org.mansar.employeemanagement.service.IDepartmentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableMethodSecurity(
        securedEnabled = true
)
public class EmployeeManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeManagementApplication.class, args);
    }


    @Bean
    CommandLineRunner commandLineRunner(RoleDao roleDao, PasswordEncoder passwordEncoder, UserDao userService, IDepartmentService departmentService) {
        return  args -> {
            userService.findByUsername("a.mansar@gmail.com").ifPresent(us -> userService.deleteById(us.getId()));
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
            manager = roleDao.save(manager);

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

            admin = roleDao.save(admin);
            hr = roleDao.save(hr);

            DepartmentRQ departmentRQ = new DepartmentRQ("IT", null, null);
            Department department = departmentService.create(departmentRQ);


            User userRQ  = new User();
            userRQ.setStatus(EmployeeStatus.ACTIVE);
            userRQ.setPassword(passwordEncoder.encode("whynot"));
            userRQ.setUsername("a.mansar@gmail.com");
            userRQ.setFirstname("Abdeddaim");
            userRQ.setFirstname("Mansar");
            userRQ.setJobTitle("Java developer");
            userRQ.setRole(admin);
            userRQ.setDepartment(department);

            userService.save(userRQ);




        };
    }
}
