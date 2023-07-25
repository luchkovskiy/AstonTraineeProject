package com.luchkovskiy.repository;

import com.luchkovskiy.models.Position;

public interface PositionRepository extends CRUDRepository<Position> {

    boolean checkIdExist(Long id);

}
