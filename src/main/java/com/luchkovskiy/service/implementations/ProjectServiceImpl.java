package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Project;
import com.luchkovskiy.repository.ProjectRepository;
import com.luchkovskiy.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public Project create(Project object) {
        object.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return projectRepository.create(object);
    }

    @Override
    public Project read(Long id) {
        idCheck(id);
        return projectRepository.read(id);
    }

    @Override
    public Project update(Project object) {
        idCheck(object.getId());
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return projectRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        idCheck(id);
        projectRepository.delete(id);
    }

    @Override
    public List<Project> readAll() {
        return projectRepository.readAll();
    }

    @Override
    public List<Project> readByEmployeeId(Long employeeId) {
        return projectRepository.readByEmployeeId(employeeId);
    }

    private void idCheck(Long id) {
        if (!projectRepository.checkIdExist(id))
            throw new RuntimeException("Project id does not exist");
    }
}
