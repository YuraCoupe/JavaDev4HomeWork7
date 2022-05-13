package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import ua.goit.projectmanagementsystem.model.domain.Project;
import java.util.*;

public class ProjectDao extends AbstractDao<Project>{

    private static final String FIND_BY_NAME = "FROM Project p WHERE p.projectName = :projectName";

    private static final String FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER =
            "SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "EXCEPT SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "JOIN developerstoprojects dtp ON p.project_id = dtp.project_id\n" +
                    "WHERE dtp.developer_id = ?;";

    private static ProjectDao instance;

    private ProjectDao() {
        super(Project.class);
    }

    public static ProjectDao getInstance() {
        if (instance == null) {
            instance = new ProjectDao();
        }
        return instance;
    }

    public Optional<Project> findByName(String name) {
        Project project = null;
        try {
            project = (Project) em.createQuery(FIND_BY_NAME)
                    .setParameter("projectName", name)
                    .getSingleResult();
        } catch (NoResultException nre) {
        }
        return Optional.ofNullable(project);
    }

    public List<Project> findWithoutThisDeveloperId(Integer developerId) {
        Query query = em.createNativeQuery(FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER, Project.class)
                .setParameter(1, developerId);
        return (List<Project>) query.getResultList();
    }
}
