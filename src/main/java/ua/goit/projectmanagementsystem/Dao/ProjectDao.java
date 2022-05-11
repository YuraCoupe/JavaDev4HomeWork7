package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.model.domain.Skill;

import java.sql.*;
import java.util.*;

public class ProjectDao extends AbstractDao<Project>{

    private static final String FIND_BY_NAME = "FROM Project p WHERE p.projectName = :projectName";

    private static final String FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER =
            "SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "EXCEPT SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "JOIN developerstoprojects dtp ON p.project_id = dtp.project_id\n" +
                    "WHERE dtp.developer_id = ?;";

    private final static String FIND_DEVELOPERS_BY_PROJECT_ID =
            "SELECT pr.project_id, pr.project_name, d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary\n" +
            "FROM projects pr\n" +
            "JOIN developersToProjects dtp ON dtp.project_id = pr.project_id \n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id \n" +
            "WHERE pr.project_id = ?";

    private final static String FIND_ALL_JOIN =
            "SELECT dtp.project_id, p.project_name, p.company_id, p.customer_id, p.project_cost, COUNT(d.developer_id) as developers_number\n" +
            "FROM developerstoprojects dtp\n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id\n" +
            "JOIN projects p ON dtp.project_id = p.project_id\n" +
            "GROUP BY dtp.project_id, p.project_name, p.company_id, p.customer_id, p.project_cost";

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
        em.clear();
        Query query = em.createNativeQuery(FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER, Project.class)
                .setParameter(1, developerId);
        return (List<Project>) query.getResultList();
    }
}
