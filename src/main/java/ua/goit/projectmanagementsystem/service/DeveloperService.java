package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.Dao.CompanyDao;
import ua.goit.projectmanagementsystem.Dao.DeveloperDao;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {

    private static DeveloperService instance;

    private static final DeveloperDao developerDAO = DeveloperDao.getInstance();
    private final CompanyDao companyDAO = CompanyDao.getInstance();

    public DeveloperService() {
    }

    public static DeveloperService getInstance() {
        if (instance == null) {
            instance = new DeveloperService();
        }
        return instance;
    }

    public Developer findByName(String firstName, String lastName) {
        Developer developer = developerDAO.findByName(firstName, lastName).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer %s %s does not exist", firstName, lastName)));
        Company company = companyDAO.findById(developer.getCompany().getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developer;
    }

    public void save(Developer developer) {
        if (developerDAO.findByName(developer.getFirstName(), developer.getLastName()).isEmpty()) {
            developerDAO.create(developer);
        } else {
            throw new CompanyAlreadyExistException("This developer already exists");
        }
    }

    public void delete(Developer developer) {
        if (!developerDAO.findById(developer.getDeveloperId()).isEmpty()) {
            developerDAO.delete(developer);
        } else {
            throw new DeveloperNotFoundException("This developer doesn't exist");
        }
    }

    public void update(Developer developer) {
        developerDAO.update(developer);
    }

    public List<Developer> findAll() {
        return developerDAO.findAll();
    }

    public Developer findById(Integer id) {
        Developer developer = developerDAO.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer with %d does not exist", id)));
        return developer;
    }

    public List<Developer> findWithoutThisProjectId(Integer projectId) {
        return developerDAO.findWithoutThisProjectId(projectId);
    }

    public List<Developer> findAllUnemployed() {
        return developerDAO.findAllUnemployed();
    }
}