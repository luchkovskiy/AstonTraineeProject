package com.luchkovskiy.service.implementations;

import com.luchkovskiy.models.Position;
import com.luchkovskiy.repository.PositionRepository;
import com.luchkovskiy.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionServiceImpl implements PositionService {

    private final PositionRepository positionRepository;

    @Override
    public Position create(Position object) {
        object.setCreated(Timestamp.valueOf(LocalDateTime.now()));
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return positionRepository.create(object);
    }

    @Override
    public Position read(Long id) {
        idCheck(id);
        return positionRepository.read(id);
    }

    @Override
    public Position update(Position object) {
        idCheck(object.getId());
        object.setChanged(Timestamp.valueOf(LocalDateTime.now()));
        return positionRepository.update(object);
    }

    @Override
    public void delete(Long id) {
        idCheck(id);
        positionRepository.delete(id);
    }

    @Override
    public List<Position> readAll() {
        return positionRepository.readAll();
    }

    private void idCheck(Long id) {
        if (!positionRepository.checkIdExist(id))
            throw new RuntimeException("Position id does not exist");
    }

}
