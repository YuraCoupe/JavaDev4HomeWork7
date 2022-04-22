package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.dao.CompanyDao;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;

import java.util.List;
import java.util.Optional;

public class Repository {
    private final DatabaseManager databaseManager;
    private final CompanyRepository companyRepository;
    private final DeveloperRepository developerRepository;

    public Repository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        this.companyRepository = new CompanyRepository(this.databaseManager);
        this.developerRepository = new DeveloperRepository(this.databaseManager);
    }

    public Optional<CompanyDao> findByName(String name) {
        return companyRepository.findByName(name);
    }

    public void saveCompany(CompanyDao companyDao) {
        companyRepository.save(companyDao);
    }

    public void updateCompany(CompanyDao companyDao) {
        companyRepository.update(companyDao);
    }

    public void deleteCompany(CompanyDao companyDao) {
        companyRepository.delete(companyDao);
    }

    public List<CompanyDao> findAllCompanies() {
        return companyRepository.findAll();
    }

    public Optional<CompanyDao> findCompanyById(Integer id) {
        return companyRepository.findById(id);
    }

    public Optional<DeveloperDao> findDeveloperByName(String firstName, String lastName) {
        return developerRepository.findByName(firstName, lastName);
    }

    public void saveDeveloper(DeveloperDao developerDao) {
        developerRepository.save(developerDao);
    }

    public void updateDeveloper(DeveloperDao developerDao) {
        developerRepository.update(developerDao);
    }

    public void deleteDeveloper(DeveloperDao developerDao) {
        developerRepository.delete(developerDao);
    }

    public List<DeveloperDao> findAllDevelopers() {
        return developerRepository.findAll();
    }

    public Optional<DeveloperDao> findDeveloperById(Integer id) {
        return developerRepository.findById(id);
    }
}
