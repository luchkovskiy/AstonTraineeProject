package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Position;
import com.luchkovskiy.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Position create(Position object) {
        getCurrentSession().persist(object);
        return object;
    }

    @Override
    public Position read(Long id) {
        return getCurrentSession().get(Position.class, id);
    }

    @Override
    public Position update(Position object) {
        getCurrentSession().merge(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        Session session = getCurrentSession();
        Position position = session.get(Position.class, id);
        if (position != null) {
            session.remove(position);
        }
    }

    @Override
    public List<Position> readAll() {
        return getCurrentSession().createQuery("FROM Position", Position.class).getResultList();
    }

    @Override
    public boolean checkIdExist(Long id) {
        Session session = getCurrentSession();
        Position position = session.get(Position.class, id);
        return position != null;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
