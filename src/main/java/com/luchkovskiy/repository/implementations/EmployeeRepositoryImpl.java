package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Employee;
import com.luchkovskiy.repository.EmployeeRepository;
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
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final ConnectionManager connectionManager;

    @Override
    public Employee create(Employee object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO employees (name, age, created, changed)" +
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
    public Employee read(Long id) {
        connectionManager.loadDriver();
        Employee employee = null;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            employee = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee update(Employee object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE employees SET name = ?, age = ?," +
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM employees WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> readAll() {
        connectionManager.loadDriver();
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employees.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    private void fillStatement(Employee object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setInt(2, object.getAge());
        statement.setTimestamp(3, object.getCreated());
        statement.setTimestamp(4, object.getChanged());
    }

    private Employee parseResultSet(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setName(resultSet.getString("name"));
        employee.setAge(resultSet.getInt("age"));
        employee.setCreated(resultSet.getTimestamp("created"));
        employee.setChanged(resultSet.getTimestamp("changed"));
        return employee;
    }

    @Override
    public boolean checkIdExist(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id = ?");
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
    public List<Employee> readByProjectId(Long projectId) {
        connectionManager.loadDriver();
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT e.*" +
                    "FROM employees e " +
                    "JOIN l_employees_projects pe ON e.id = pe.employee_id " +
                    "JOIN projects p ON pe.project_id = p.id " +
                    "WHERE p.id = ?");
            statement.setLong(1, projectId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employees.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public List<Employee> readByPosition(String positionName) {
        connectionManager.loadDriver();
        List<Employee> employees = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT e.* " +
                    "FROM employees e " +
                    "JOIN positions p ON e.id = p.employee_id " +
                    "WHERE p.name = ?");
            statement.setString(1, positionName);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                employees.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
