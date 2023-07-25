package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.EmployeeProject;
import com.luchkovskiy.repository.EmployeeProjectRepository;
import com.luchkovskiy.service.EmployeeProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeProjectServiceImpl implements EmployeeProjectService {

    private final EmployeeProjectRepository employeeProjectRepository;

    @Override
    public EmployeeProject create(EmployeeProject object) {
        object.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return employeeProjectRepository.create(object);
    }

    @Override
    public EmployeeProject read(Long id) {
        idCheck(id);
        return employeeProjectRepository.read(id);
    }

    @Override
    public EmployeeProject update(EmployeeProject object) {
        idCheck(object.getId());
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return employeeProjectRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        idCheck(id);
        employeeProjectRepository.delete(id);
    }

    @Override
    public List<EmployeeProject> readAll() {
        return employeeProjectRepository.readAll();
    }

    private void idCheck(Long id) {
        if (!employeeProjectRepository.checkIdExist(id))
            throw new RuntimeException("EmployeeProject id does not exist");
    }
}
