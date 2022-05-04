package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.DAO.CompanyDAO;
import ua.goit.projectmanagementsystem.DAO.DeveloperDAO;
import ua.goit.projectmanagementsystem.model.dto.DeveloperWithCompanyDto;

import java.util.List;
import java.util.stream.Collectors;

public class DeveloperService {
    public final DeveloperDAO developerDAO;
    public final CompanyDAO companyDAO;

    public DeveloperService(DeveloperDAO developerDAO, CompanyDAO companyDAO) {
        this.developerDAO = developerDAO;
        this.companyDAO = companyDAO;
    }

    public Developer findByName(String firstName, String lastName) {
        Developer developer = developerDAO.findByName(firstName, lastName).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer %s %s does not exist", firstName, lastName)));
        Company company = companyDAO.findById(developer.getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developer;
    }

    public void save(Developer developer) {
        if (developerDAO.findByName(developer.getFirstName(), developer.getLastName()).isEmpty()) {
            developerDAO.save(developer);
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
        return developerDAO.findAll().orElseThrow(() -> new DeveloperNotFoundException("Developer doesn't exist"));
    }

    public Developer findById(Integer id) {
        Developer developer = developerDAO.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer with %d does not exist", id)));
        return developer;
    }

    public List<Developer> findByCompanyId(Integer companyId) {
        return developerDAO.findByCompanyId(companyId);
    }

    public List<Developer> findByProjectId(Integer projectId) {
        return developerDAO.findByProjectId(projectId);
    }

    public List<Developer> findWithoutThisProjectId(Integer projectId) {
        return developerDAO.findWithoutThisProjectId(projectId);
    }

    public List<Developer> findAllUnemployed() {
        return developerDAO.findAllUnemployed();
    }

    public List<DeveloperWithCompanyDto> findAllWithCompany() {
        List<Developer> developers = developerDAO.findAll().orElseThrow(()
                -> new DeveloperNotFoundException("Developers do not exist"));
        List<DeveloperWithCompanyDto> developerWithCompanyDtos = developers.stream()
                .map(developer -> {
                    DeveloperWithCompanyDto developerWithCompanyDto = new DeveloperWithCompanyDto();
                    developerWithCompanyDto.setDeveloperId(developer.getDeveloperId());
                    developerWithCompanyDto.setFirstName(developer.getFirstName());
                    developerWithCompanyDto.setLastName(developer.getLastName());
                    developerWithCompanyDto.setAge(developer.getAge());
                    developerWithCompanyDto.setSex(developer.getSex());
                    developerWithCompanyDto.setCompanyId(developer.getCompanyId());
                    developerWithCompanyDto.setSalary(developer.getSalary());
                    developerWithCompanyDto.setCompany(companyDAO.findById(developer.getCompanyId()).orElseThrow(()
                            -> new CompanyNotFoundException(String.format("Company %d does not exist", developer.getCompanyId()))));
                    return developerWithCompanyDto;
                })
                .collect(Collectors.toList());
        return developerWithCompanyDtos;
    }
}