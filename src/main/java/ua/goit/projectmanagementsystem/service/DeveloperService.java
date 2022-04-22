package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperShortConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.CompanyDao;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;

import ua.goit.projectmanagementsystem.repository.Repository;

import java.util.List;

import java.util.stream.Collectors;

public class DeveloperService {
    public final Repository repository;
    public final DeveloperShortConverter developerShortConverter;
    public final DeveloperConverter developerConverter;
    public final DeveloperProjectConverter developerProjectConverter;

    public DeveloperService(Repository repository, DeveloperShortConverter developerShortConverter, DeveloperConverter developerConverter, DeveloperProjectConverter developerProjectConverter) {
        this.repository = repository;
        this.developerShortConverter = developerShortConverter;
        this.developerConverter = developerConverter;
        this.developerProjectConverter = developerProjectConverter;
    }

    public DeveloperDto findByName(String firstName, String lastName) {
        DeveloperDao developerDao = repository.findDeveloperByName(firstName, lastName).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer %s %s does not exist", firstName, lastName)));
        CompanyDao companyDao = repository.findCompanyById(developerDao.getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developerConverter.daoToDto(developerDao, companyDao);
    }

    public boolean isDeveloperExist(String firstName, String lastName) {
        return repository.findDeveloperByName(firstName, lastName).isPresent();
    }

    public void save(DeveloperDto developerDto) {
        if (repository.findDeveloperByName(developerDto.getFirstName(), developerDto.getLastName()).isEmpty()) {
            repository.saveDeveloper(developerConverter.dtoToDao(developerDto));
        } else {
            throw new CompanyAlreadyExistException("This developer already exists");
        }
    }

    public void delete(DeveloperDto developerDto) {
        if (!repository.findDeveloperById(developerDto.getCompany().getCompanyId()).isEmpty()) {
            repository.deleteDeveloper(developerConverter.dtoToDao(developerDto));
        } else {
            throw new CompanyNotFoundException("This developer doesn't exist");
        }
    }

    public void update(DeveloperDto developerDto) {
        repository.updateDeveloper(developerConverter.dtoToDao(developerDto));
    }

    public List<DeveloperDto> findAll() {
        return repository.findAllDevelopers()
                .stream()
                .map(developerDao -> {CompanyDao companyDao = repository.findCompanyById(developerDao.getCompanyId()).orElseThrow(()
                        -> new CompanyNotFoundException(String.format("Company does not exist")));
                    return developerConverter.daoToDto(developerDao, companyDao);
                })
                .collect(Collectors.toList());
    }

    public DeveloperDto findById(Integer id) {
        DeveloperDao developerDao = repository.findDeveloperById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer with %d does not exist", id)));
        CompanyDao companyDao = repository.findCompanyById(developerDao.getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developerConverter.daoToDto(developerDao, companyDao);
    }
}
