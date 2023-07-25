package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.ProjectCreateRequest;
import com.luchkovskiy.controllers.requests.update.ProjectUpdateRequest;
import com.luchkovskiy.models.Project;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<Project> create(@RequestBody ProjectCreateRequest projectCreateRequest) {
        Project project = Project.builder()
                .name(projectCreateRequest.getName())
                .startTime(projectCreateRequest.getStartTime())
                .build();
        Project createdProject = projectService.create(project);
        return new ResponseEntity<>(createdProject, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> read(@PathVariable("id") Long id) {
        Project project = projectService.read(id);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Project> projects = projectService.readAll();
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Project> update(@RequestBody ProjectUpdateRequest projectUpdateRequest) {
        Project project = projectService.read(projectUpdateRequest.getId());
        project.setName(projectUpdateRequest.getName());
        project.setStartTime(projectUpdateRequest.getStartTime());
        Project updatedProject = projectService.update(project);
        return new ResponseEntity<>(updatedProject, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        projectService.delete(id);
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Object> readByEmployeeId(@PathVariable Long employeeId) {
        List<Project> projects = projectService.readByEmployeeId(employeeId);
        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

}
