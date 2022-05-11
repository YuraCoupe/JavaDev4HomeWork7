package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import java.util.*;

public class DeveloperDao extends AbstractDao<Developer>{
    private static final String FIND_BY_NAME = "FROM Developer d WHERE d.firstName = :firstName AND d.lastName = :lastName";
    private static final String FIND_DEVELOPERS_EXCLUDING_CURRENT_PROJECT =
            "SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary FROM developers d \n" +
                    "EXCEPT SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary FROM developers d \n" +
                    "JOIN developerstoprojects dtp ON d.developer_id = dtp.developer_id\n" +
                    "WHERE dtp.project_id = ?;";
    private static final String FIND_ALL_UNEMPLOYED = "FROM Developer d WHERE d.company.companyName = 'Unemployed'";

    private static DeveloperDao instance;

    private DeveloperDao() {
        super(Developer.class);
    }

    public static DeveloperDao getInstance() {
        if (instance == null) {
            instance = new DeveloperDao();
        }
        return instance;
    }

    public Optional<Developer> findByName(String firstName, String lastName) {
        Developer developer = null;
        try {
            developer = (Developer) em.createQuery(FIND_BY_NAME)
                    .setParameter("firstName", firstName)
                    .setParameter("lastName", lastName)
                    .getSingleResult();
        }catch (NoResultException nre) {
        }
        return Optional.ofNullable(developer);
    }

    public List<Developer> findWithoutThisProjectId(Integer projectId) {
        em.clear();
        Query query = em.createNativeQuery(FIND_DEVELOPERS_EXCLUDING_CURRENT_PROJECT, Developer.class)
                .setParameter(1, projectId);
        return (List<Developer>) query.getResultList();
    }

    public List<Developer> findAllUnemployed() {
        em.clear();
        TypedQuery<Developer> query = em.createQuery(FIND_ALL_UNEMPLOYED, Developer.class);
        return query.getResultList();
    }
}

