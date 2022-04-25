package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperShortConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.CompanyDao;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;

import java.util.List;

import java.util.stream.Collectors;

public class DeveloperService {
    public final DeveloperRepository developerRepository;
    public final CompanyRepository companyRepository;
    public final DeveloperShortConverter developerShortConverter;
    public final DeveloperConverter developerConverter;
    public final DeveloperProjectConverter developerProjectConverter;

    public DeveloperService(DeveloperRepository developerRepository, CompanyRepository companyRepository, DeveloperShortConverter developerShortConverter, DeveloperConverter developerConverter, DeveloperProjectConverter developerProjectConverter) {
        this.developerRepository = developerRepository;
        this.companyRepository = companyRepository;
        this.developerShortConverter = developerShortConverter;
        this.developerConverter = developerConverter;
        this.developerProjectConverter = developerProjectConverter;
    }

    public DeveloperDto findByName(String firstName, String lastName) {
        DeveloperDao developerDao = developerRepository.findByName(firstName, lastName).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer %s %s does not exist", firstName, lastName)));
        CompanyDao companyDao = companyRepository.findById(developerDao.getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developerConverter.daoToDto(developerDao, companyDao);
    }

    public boolean isDeveloperExist(String firstName, String lastName) {
        return developerRepository.findByName(firstName, lastName).isPresent();
    }

    public void save(DeveloperDto developerDto) {
        if (developerRepository.findByName(developerDto.getFirstName(), developerDto.getLastName()).isEmpty()) {
            developerRepository.save(developerConverter.dtoToDao(developerDto));
        } else {
            throw new CompanyAlreadyExistException("This developer already exists");
        }
    }

    public void delete(DeveloperDto developerDto) {
        if (!developerRepository.findById(developerDto.getCompany().getCompanyId()).isEmpty()) {
            developerRepository.delete(developerConverter.dtoToDao(developerDto));
        } else {
            throw new CompanyNotFoundException("This developer doesn't exist");
        }
    }

    public void update(DeveloperDto developerDto) {
        developerRepository.update(developerConverter.dtoToDao(developerDto));
    }

    public List<DeveloperDto> findAll() {
        return developerRepository.findAll()
                .stream()
                .map(developerDao -> {CompanyDao companyDao = companyRepository.findById(developerDao.getCompanyId()).orElseThrow(()
                        -> new CompanyNotFoundException(String.format("Company does not exist")));
                    return developerConverter.daoToDto(developerDao, companyDao);
                })
                .collect(Collectors.toList());
    }

    public DeveloperDto findById(Integer id) {
        DeveloperDao developerDao = developerRepository.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer with %d does not exist", id)));
        CompanyDao companyDao = companyRepository.findById(developerDao.getCompanyId()).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company does not exist")));
        return developerConverter.daoToDto(developerDao, companyDao);
    }

    public List<DeveloperDto> findByCompanyId(Integer companyId) {
        return developerRepository.findByCompanyId(companyId)
                .stream()
                .map(developerDao -> {CompanyDao companyDao = companyRepository.findById(developerDao.getCompanyId()).orElseThrow(()
                        -> new CompanyNotFoundException(String.format("Company does not exist")));
                    return developerConverter.daoToDto(developerDao, companyDao);
                })
                .collect(Collectors.toList());
    }
}
