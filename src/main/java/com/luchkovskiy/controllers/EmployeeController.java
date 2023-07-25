package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.EmployeeCreateRequest;
import com.luchkovskiy.controllers.requests.update.EmployeeUpdateRequest;
import com.luchkovskiy.models.Employee;
import com.luchkovskiy.models.EmployeeProject;
import com.luchkovskiy.service.EmployeeProjectService;
import com.luchkovskiy.service.EmployeeService;
import com.luchkovskiy.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    private final EmployeeProjectService employeeProjectService;

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Employee> create(@RequestBody EmployeeCreateRequest employeeCreateRequest) {
        Employee employee = Employee.builder()
                .name(employeeCreateRequest.getName())
                .age(employeeCreateRequest.getAge())
                .build();
        Employee createdEmployee = employeeService.create(employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> read(@PathVariable("id") Long id) {
        Employee employee = employeeService.read(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Employee> employees = employeeService.readAll();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Employee> update(@RequestBody EmployeeUpdateRequest employeeUpdateRequest) {
        Employee employee = employeeService.read(employeeUpdateRequest.getId());
        employee.setName(employeeUpdateRequest.getName());
        employee.setAge(employeeUpdateRequest.getAge());
        Employee updatedEmployee = employeeService.update(employee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        employeeService.delete(id);
    }

    @PostMapping("/projects")
    public ResponseEntity<EmployeeProject> linkEmployeeProject(@RequestParam Long employeeId, @RequestParam Long projectId) {
        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setEmployee(employeeService.read(employeeId));
        employeeProject.setProject(projectService.read(projectId));
        EmployeeProject createdEmployeeProject = employeeProjectService.create(employeeProject);
        return new ResponseEntity<>(createdEmployeeProject, HttpStatus.OK);
    }

    @DeleteMapping("/projects/{linkId}")
    public void unlinkEmployeeProject(@PathVariable Long linkId) {
        employeeProjectService.delete(linkId);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<Object> readByProject(@PathVariable Long projectId) {
        List<Employee> employees = employeeService.readByProjectId(projectId);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/positions/{positionName}")
    public ResponseEntity<Object> readByPosition(@PathVariable String positionName) {
        List<Employee> employees = employeeService.readByPosition(positionName);
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

}
