package com.luchkovskiy.repository.implementations;

import com.luchkovskiy.models.Project;
import com.luchkovskiy.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    private final SessionFactory sessionFactory;

    @Override
    public Project create(Project object) {
        getCurrentSession().persist(object);
        return object;
    }

    @Override
    public Project read(Long id) {
        return getCurrentSession().get(Project.class, id);
    }

    @Override
    public Project update(Project object) {
        getCurrentSession().merge(object);
        return object;
    }

    @Override
    public void delete(Long id) {
        Session session = getCurrentSession();
        Project project = session.get(Project.class, id);
        if (project != null) {
            session.remove(project);
        }
    }

    @Override
    public List<Project> readAll() {
        return getCurrentSession().createQuery("FROM Project", Project.class).getResultList();
    }

    @Override
    public boolean checkIdExist(Long id) {
        Session session = getCurrentSession();
        Project project = session.get(Project.class, id);
        return project != null;
    }

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public List<Project> readByEmployeeId(Long employeeId) {
        Session session = getCurrentSession();
        String hql = "SELECT p " +
                "FROM Project p " +
                "JOIN EmployeeProject ep ON p = ep.project " +
                "JOIN Employee e ON ep.employee = e " +
                "WHERE e.id = :employeeId";
        Query<Project> query = session.createQuery(hql, Project.class);
        query.setParameter("employeeId", employeeId);
        return query.getResultList();

    }

}
