package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.EmployeeProject;
import com.luchkovskiy.repository.EmployeeProjectRepository;
import com.luchkovskiy.repository.EmployeeRepository;
import com.luchkovskiy.repository.ProjectRepository;
import com.luchkovskiy.util.ConnectionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EmployeeProjectRepositoryImpl implements EmployeeProjectRepository {

    private final ConnectionManager connectionManager;

    private final EmployeeRepository employeeRepository;

    private final ProjectRepository projectRepository;

    @Override
    public EmployeeProject create(EmployeeProject object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO l_employees_projects (employee_id, project_id, created, changed)" +
                    " VALUES (?, ?, ?, ?) RETURNING id");
            fillStatement(object, statement);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            object.setId(resultSet.getLong("id"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public EmployeeProject read(Long id) {
        connectionManager.loadDriver();
        EmployeeProject employeeProject = null;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM l_employees_projects WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            employeeProject = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeProject;
    }

    @Override
    public EmployeeProject update(EmployeeProject object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE l_employees_projects SET employee_id = ?, project_id = ?," +
                    " created = ?, changed = ? WHERE id = ?");
            fillStatement(object, insertStatement);
            insertStatement.setLong(5, object.getId());
            insertStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return object;
    }

    @Override
    public void delete(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("DELETE FROM l_employees_projects WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<EmployeeProject> readAll() {
        connectionManager.loadDriver();
        List<EmployeeProject> employeeProjects = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM l_employees_projects");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employeeProjects.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeProjects;
    }

    private void fillStatement(EmployeeProject object, PreparedStatement statement) throws SQLException {
        statement.setLong(1, object.getEmployee().getId());
        statement.setLong(2, object.getProject().getId());
        statement.setTimestamp(3, object.getCreated());
        statement.setTimestamp(4, object.getChanged());
    }

    private EmployeeProject parseResultSet(ResultSet resultSet) throws SQLException {
        EmployeeProject employeeProject = new EmployeeProject();
        employeeProject.setId(resultSet.getLong("id"));
        employeeProject.setEmployee(employeeRepository.read(resultSet.getLong("employee_id")));
        employeeProject.setProject(projectRepository.read(resultSet.getLong("project_id")));
        employeeProject.setCreated(resultSet.getTimestamp("created"));
        employeeProject.setChanged(resultSet.getTimestamp("changed"));
        return employeeProject;
    }

    @Override
    public boolean checkIdExist(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM l_employees_projects WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
