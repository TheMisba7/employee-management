package org.mansar.employeemanagement.dao;

import org.mansar.employeemanagement.core.EmployeeAttribute;
import org.mansar.employeemanagement.core.EmployeeStatus;
import org.mansar.employeemanagement.dto.FilterParam;
import org.mansar.employeemanagement.model.Employee;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public interface EmployeeSpecification {

     static Specification<Employee> filterBy(FilterParam filterParam) {
         if (filterParam == null)
             return (root, query, cb) -> cb.conjunction();
        return Specification
                .where(hasEmployeeId(filterParam.getEmployeeId()))
                .and(hasDepartment(filterParam.getDepartment()))
                .and(hasJobTitle(filterParam.getJobTitle()))
                .and(hasStatus(filterParam.getStatus()))
                .and(hasDepartmentId(filterParam.getDepartmentId()))
                .and(hasHireDate(filterParam.getHireDate()));
    }

    private static Specification<Employee> hasDepartmentId(Long departmentId) {
        return (root, query, cb) -> departmentId == null
                ? cb.conjunction()
                : cb.equal(root.get(EmployeeAttribute.DEPARTMENT.getValue()).get("id"), departmentId);
    }

    private static Specification<Employee> hasEmployeeId(Long employeeId) {
        return (root, query, cb) -> employeeId == null
                ? cb.conjunction()
                : cb.equal(root.get(EmployeeAttribute.ID.getValue()), employeeId);
    }

    private static Specification<Employee> hasDepartment(String department) {
        return (root, query, cb) -> {
            if (department == null || department.isEmpty()) {
                return cb.conjunction();
            }
            String pattern = "%" + department + "%";
            return cb.like(cb.lower(root.get(EmployeeAttribute.DEPARTMENT.getValue()).get("name")), pattern.toLowerCase());
        };
    }

    private static Specification<Employee> hasJobTitle(String jobTitle) {
        return (root, query, cb) -> {
            if (jobTitle == null || jobTitle.isEmpty()) {
                return cb.conjunction();
            }
            String pattern = "%" + jobTitle + "%";
            return cb.like(cb.lower(root.get(EmployeeAttribute.JOB_TITLE.getValue())), pattern.toLowerCase());
        };
    }

    private static Specification<Employee> hasStatus(EmployeeStatus status) {
        return (root, query, cb) -> status == null
                ? cb.conjunction()
                : cb.equal(root.get(EmployeeAttribute.STATUS.getValue()), status);
    }

    private static Specification<Employee> hasHireDate(Date hireDate) {
        return (root, query, cb) -> hireDate == null
                ? cb.conjunction()
                : cb.equal(root.get(EmployeeAttribute.HIRE_DATE.getValue()), hireDate);
    }
}
