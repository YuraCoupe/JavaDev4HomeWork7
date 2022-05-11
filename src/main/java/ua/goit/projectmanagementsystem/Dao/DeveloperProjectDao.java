package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.TypedQuery;
import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperProjectDao extends AbstractDao<DeveloperProject>{
    private static final String FIND_BY_IDS = "FROM DeveloperProject WHERE developerId = :developerId AND projectId = :projectId";
    private static final String FIND_BY_DEVELOPER_ID = "FROM DeveloperProject WHERE developerId = :developerId";
    private static final String FIND_BY_PROJECT_ID = "SELECT * FROM DeveloperProject WHERE projectId = :projectId";

    private static DeveloperProjectDao instance;

    private DeveloperProjectDao() {
        super(DeveloperProject.class);
    }

    public static DeveloperProjectDao getInstance() {
        if (instance == null) {
            instance = new DeveloperProjectDao();
        }
        return instance;
    }

    public Optional<DeveloperProject> findByIds(Integer developerId, Integer projectId) {
        em.clear();
        DeveloperProject developerProject = (DeveloperProject) em.createQuery(FIND_BY_IDS)
                .setParameter("developerId", developerId)
                .setParameter("projectId", projectId)
                .getSingleResult();
        return Optional.of(developerProject);
    }

    public List<DeveloperProject> findByDeveloperId(Integer developerId) {
        TypedQuery<DeveloperProject> query = em.createQuery(FIND_BY_DEVELOPER_ID, DeveloperProject.class)
                .setParameter("developerId", developerId);
        return query.getResultList();
    }

    public List<DeveloperProject> findByProjectId(Integer projectId) {
        TypedQuery<DeveloperProject> query = em.createQuery(FIND_BY_PROJECT_ID, DeveloperProject.class)
                .setParameter("projectId", projectId);
        return query.getResultList();
    }
}
