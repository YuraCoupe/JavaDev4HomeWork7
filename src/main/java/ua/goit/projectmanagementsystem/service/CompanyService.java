package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.Dao.CompanyDao;

import java.util.List;

public class CompanyService {

    private static CompanyService instance;

    private static final CompanyDao companyDAO = CompanyDao.getInstance();

    public CompanyService() {
    }

    public static CompanyService getInstance() {
        if (instance == null) {
            instance = new CompanyService();
        }
        return instance;
    }

    public Company findByName(String name) {
        return companyDAO.findByName(name).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company %s does not exist", name)));
    }

    public Integer save(Company company) {
        Integer id = null;
        if (companyDAO.findByName(company.getCompanyName()).isEmpty()) {
            id = companyDAO.create(company).getCompanyId();
        } else {
            throw new CompanyAlreadyExistException("This company already exists");
        }
        return id;
    }

    public void delete(Company company) {
        if (!companyDAO.findById(company.getCompanyId()).isEmpty()) {
            companyDAO.delete(company);
        } else {
            throw new CompanyNotFoundException("This company doesn't exist");
        }
    }

    public void update(Company company) {
            companyDAO.update(company);
    }

    public List<Company> findAll() {
        return companyDAO.findAll();
    }

    public List<Company> findAllExUnemployed() {
        return companyDAO.findAllExUnemployed();
    }

    public Company findById(Integer id) {
        return companyDAO.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company %d does not exist", id)));
    }
}




