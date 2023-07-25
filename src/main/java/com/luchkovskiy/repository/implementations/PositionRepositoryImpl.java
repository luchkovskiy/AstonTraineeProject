package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Position;
import com.luchkovskiy.repository.EmployeeRepository;
import com.luchkovskiy.repository.PositionRepository;
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
public class PositionRepositoryImpl implements PositionRepository {

    private final ConnectionManager connectionManager;

    private final EmployeeRepository employeeRepository;

    @Override
    public Position create(Position object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO positions (name, employee_id, created, changed)" +
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
    public Position read(Long id) {
        connectionManager.loadDriver();
        Position position = null;
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE id = ?");
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            position = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return position;
    }

    @Override
    public Position update(Position object) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement insertStatement = connection.prepareStatement("UPDATE positions SET name = ?, employee_id = ?," +
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
            PreparedStatement statement = connection.prepareStatement("DELETE FROM positions WHERE id = ?");
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Position> readAll() {
        connectionManager.loadDriver();
        List<Position> positions = new ArrayList<>();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                positions.add(parseResultSet(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return positions;
    }

    private void fillStatement(Position object, PreparedStatement statement) throws SQLException {
        statement.setString(1, object.getName());
        statement.setLong(2, object.getEmployee().getId());
        statement.setTimestamp(3, object.getCreated());
        statement.setTimestamp(4, object.getChanged());
    }

    private Position parseResultSet(ResultSet resultSet) throws SQLException {
        Position position = new Position();
        position.setId(resultSet.getLong("id"));
        position.setName(resultSet.getString("name"));
        position.setEmployee(employeeRepository.read(resultSet.getLong("employee_id")));
        position.setCreated(resultSet.getTimestamp("created"));
        position.setChanged(resultSet.getTimestamp("changed"));
        return position;
    }

    @Override
    public boolean checkIdExist(Long id) {
        connectionManager.loadDriver();
        try (Connection connection = connectionManager.open()) {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM positions WHERE id = ?");
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
