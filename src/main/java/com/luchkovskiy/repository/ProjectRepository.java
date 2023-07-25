package com.luchkovskiy.repository;

import com.luchkovskiy.models.Project;

import java.util.List;

public interface ProjectRepository extends CRUDRepository<Project> {

    boolean checkIdExist(Long id);

    List<Project> readByEmployeeId(Long employeeId);

}
