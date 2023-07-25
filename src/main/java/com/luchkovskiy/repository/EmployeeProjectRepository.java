package com.luchkovskiy.repository;

import com.luchkovskiy.models.EmployeeProject;

public interface EmployeeProjectRepository extends CRUDRepository<EmployeeProject> {

    boolean checkIdExist(Long id);

}
