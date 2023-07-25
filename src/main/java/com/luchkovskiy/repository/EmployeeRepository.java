package com.luchkovskiy.repository;

import com.luchkovskiy.models.Employee;

import java.util.List;

public interface EmployeeRepository extends CRUDRepository<Employee> {

    boolean checkIdExist(Long id);

    List<Employee> readByProjectId(Long projectId);

    List<Employee> readByPosition(String positionName);

}
