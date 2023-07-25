package com.luchkovskiy.service;

import com.luchkovskiy.models.Project;

import java.util.List;

public interface ProjectService extends CRUDService<Project> {

    List<Project> readByEmployeeId(Long employeeId);

}
