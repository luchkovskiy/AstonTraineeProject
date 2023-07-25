package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Project;
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
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ConnectionManager connectionManager;

    @Override
    public Project create(Project object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO projects (name, start_time, created, changed)" +
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
    public Project read(Long id) {
        connectionManager.loadDriver();
        Project project = null;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            project = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    @Override
    public Project update(Project object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE projects SET name = ?, start_time = ?," +
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM projects WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Project> readAll() {
        connectionManager.loadDriver();
        List<Project> projects = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                projects.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    private void fillStatement(Project object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setTimestamp(2, object.getStartTime());
        statement.setTimestamp(3, object.getCreated());
        statement.setTimestamp(4, object.getChanged());
    }

    private Project parseResultSet(ResultSet resultSet) throws SQLException {
        Project project = new Project();
        project.setId(resultSet.getLong("id"));
        project.setName(resultSet.getString("name"));
        project.setStartTime(resultSet.getTimestamp("start_time"));
        project.setCreated(resultSet.getTimestamp("created"));
        project.setChanged(resultSet.getTimestamp("changed"));
        return project;
    }

    @Override
    public boolean checkIdExist(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM projects WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Project> readByEmployeeId(Long employeeId) {
        connectionManager.loadDriver();
        List<Project> projects = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT p.* " +
                    "FROM projects p " +
                    "JOIN l_employees_projects pe ON p.id = pe.project_id " +
                    "JOIN employees e ON pe.employee_id = e.id " +
                    "WHERE e.id = ?");
            statement.setLong(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                projects.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

}
