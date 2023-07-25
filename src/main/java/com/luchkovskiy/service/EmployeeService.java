package com.luchkovskiy.service;

import com.luchkovskiy.models.Employee;

import java.util.List;

public interface EmployeeService extends CRUDService<Employee> {

    List<Employee> readByProjectId(Long projectId);

    List<Employee> readByPosition(String positionName);

}
