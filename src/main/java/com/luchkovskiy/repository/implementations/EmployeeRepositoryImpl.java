package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Employee;
import com.luchkovskiy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Employee create(Employee object) {
        getCurrentSession().persist(object);
        return object;
    }

    @Override
    public Employee read(Long id) {
        return getCurrentSession().get(Employee.class, id);
    }

    @Override
    public Employee update(Employee object) {
        getCurrentSession().merge(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        Session session = getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        if (employee != null) {
            session.remove(employee);
        }
    }

    @Override
    public List<Employee> readAll() {
        return getCurrentSession().createQuery("FROM Employee ", Employee.class).getResultList();
    }

    @Override
    public boolean checkIdExist(Long id) {
        Session session = getCurrentSession();
        Employee employee = session.get(Employee.class, id);
        return employee != null;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Employee> readByProjectId(Long projectId) {
        Session session = getCurrentSession();
        String hql = "SELECT e FROM Employee e" +
                " JOIN EmployeeProject ep ON e = ep.employee" +
                " JOIN Project p ON ep.project = p WHERE p.id = :projectId";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        query.setParameter("projectId", projectId);
        return query.getResultList();
    }


    @Override
    public List<Employee> readByPosition(String positionName) {
        Session session = getCurrentSession();
        String hql = "SELECT e " +
                "FROM Employee e " +
                "JOIN Position p ON e = p.employee " +
                "WHERE p.name = :positionName";
        Query<Employee> query = session.createQuery(hql, Employee.class);
        query.setParameter("positionName", positionName);
        return query.getResultList();
    }

}
