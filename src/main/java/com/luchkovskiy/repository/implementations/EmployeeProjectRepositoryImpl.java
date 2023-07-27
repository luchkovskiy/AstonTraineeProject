package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.EmployeeProject;
import com.luchkovskiy.repository.EmployeeProjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeProjectRepositoryImpl implements EmployeeProjectRepository {

    private final SessionFactory sessionFactory;

    @Override
    public EmployeeProject create(EmployeeProject object) {
        getCurrentSession().persist(object);
        return object;
    }

    @Override
    public EmployeeProject read(Long id) {
        return getCurrentSession().get(EmployeeProject.class, id);
    }

    @Override
    public EmployeeProject update(EmployeeProject object) {
        getCurrentSession().merge(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        Session session = getCurrentSession();
        EmployeeProject employeeProject = session.get(EmployeeProject.class, id);
        if (employeeProject != null) {
            session.remove(employeeProject);
        }
    }

    @Override
    public List<EmployeeProject> readAll() {
        return getCurrentSession().createQuery("FROM EmployeeProject", EmployeeProject.class).getResultList();
    }

    @Override
    public boolean checkIdExist(Long id) {
        Session session = getCurrentSession();
        EmployeeProject employeeProject = session.get(EmployeeProject.class, id);
        return employeeProject != null;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
