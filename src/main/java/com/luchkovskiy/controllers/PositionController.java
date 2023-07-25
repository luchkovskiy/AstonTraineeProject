package com.luchkovskiy.controllers;

import com.luchkovskiy.controllers.requests.create.PositionCreateRequest;
import com.luchkovskiy.controllers.requests.update.PositionUpdateRequest;
import com.luchkovskiy.models.Position;
import com.luchkovskiy.service.EmployeeService;
import com.luchkovskiy.service.PositionService;
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
@RequestMapping("/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Position> create(@RequestBody PositionCreateRequest positionCreateRequest) {
        Position position = Position.builder()
                .name(positionCreateRequest.getName())
                .employee(employeeService.read(positionCreateRequest.getEmployeeId()))
                .build();
        Position createdPosition = positionService.create(position);
        return new ResponseEntity<>(createdPosition, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Position> read(@PathVariable("id") Long id) {
        Position position = positionService.read(id);
        return new ResponseEntity<>(position, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Object> readAll() {
        List<Position> positions = positionService.readAll();
        return new ResponseEntity<>(positions, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Position> update(@RequestBody PositionUpdateRequest positionUpdateRequest) {
        Position position = positionService.read(positionUpdateRequest.getId());
        position.setName(positionUpdateRequest.getName());
        position.setEmployee(employeeService.read(positionUpdateRequest.getEmployeeId()));
        Position updatedPosition = positionService.update(position);
        return new ResponseEntity<>(updatedPosition, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        positionService.delete(id);
    }
}
