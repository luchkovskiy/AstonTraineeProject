package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Employee;
import com.luchkovskiy.repository.EmployeeRepository;
import com.luchkovskiy.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee object) {
        object.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return employeeRepository.create(object);
    }

    @Override
    public Employee read(Long id) {
        idCheck(id);
        return employeeRepository.read(id);
    }

    @Override
    public Employee update(Employee object) {
        idCheck(object.getId());
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return employeeRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        idCheck(id);
        employeeRepository.delete(id);
    }

    @Override
    public List<Employee> readAll() {
        return employeeRepository.readAll();
    }

    @Override
    public List<Employee> readByProjectId(Long projectId) {
        return employeeRepository.readByProjectId(projectId);
    }

    @Override
    public List<Employee> readByPosition(String positionName) {
        return employeeRepository.readByPosition(positionName);
    }

    private void idCheck(Long id) {
        if (!employeeRepository.checkIdExist(id))
            throw new RuntimeException("Employee id does not exist");
    }
}
