package ua.goit.projectmanagementsystem.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import ua.goit.projectmanagementsystem.config.PersistenceProvider;
import ua.goit.projectmanagementsystem.model.domain.Company;
import java.util.List;
import java.util.Optional;

public class CompanyDao extends AbstractDao<Company> {
    private static final String FIND_BY_NAME = "FROM Company c WHERE c.companyName = :companyName";
    private static final String FIND_ALL_EX_UNEMPLOYED = "FROM Company c WHERE c.companyName <> 'Unemployed'";

    private EntityManager em = PersistenceProvider.getEntityManager();
    private static CompanyDao instance;

    private CompanyDao() {
        super(Company.class);
    }

    public static CompanyDao getInstance() {
        if (instance == null) {
            instance = new CompanyDao();
        }
        return instance;
    }

    public Optional<Company> findByName(String name) {
        Company company = null;
        try {
            company = (Company) em.createQuery(FIND_BY_NAME)
                    .setParameter("companyName", name)
                    .getSingleResult();
        } catch (NoResultException nre) {

        }
        return Optional.ofNullable(company);
    }

    public List<Company> findAllExUnemployed() {
        em.clear();
        TypedQuery<Company> query = em.createQuery(FIND_ALL_EX_UNEMPLOYED, Company.class);
        return query.getResultList();
    }
}
