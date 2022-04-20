package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperShortConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.DeveloperShortDto;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperService {
    public final DeveloperRepository developerRepository;
    public final DeveloperShortConverter developerShortConverter;
    public final DeveloperConverter developerConverter;
    public final DeveloperProjectConverter developerProjectConverter;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperShortConverter developerShortConverter, DeveloperConverter developerConverter, DeveloperProjectConverter developerProjectConverter) {
        this.developerRepository = developerRepository;
        this.developerShortConverter = developerShortConverter;
        this.developerConverter = developerConverter;
        this.developerProjectConverter = developerProjectConverter;
    }

    public Set<DeveloperShortDto> findJavaDevelopers() {
        Set<DeveloperDao> developersDao = developerRepository.findJavaDevs().orElseThrow(()
                -> new DeveloperNotFoundException("Java developers do not find"));
        Set<DeveloperShortDto> developersShortDto = developersDao.stream()
                .map(developerDao -> developerShortConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersShortDto;
    }

    public Set<DeveloperShortDto> findMiddleDevelopers() {
        Set<DeveloperDao> developersDao = developerRepository.findMiddleDevs().orElseThrow(()
                -> new DeveloperNotFoundException("Middle developers do not find"));
        Set<DeveloperShortDto> developersShortDto = developersDao.stream()
                .map(developerDao -> developerShortConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersShortDto;
    }

    public DeveloperDto findByName(String firstName, String lastName) {
        return developerConverter.daoToDto(developerRepository.findByName(firstName, lastName).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Company %s does not exist", firstName))));
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
        if (!developerRepository.findById(developerDto.getCompanyId()).isEmpty()) {
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
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toList());
    }

    public DeveloperDto findById(Integer id) {
        return developerConverter.daoToDto(developerRepository.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Developer %d does not exist", id))));
    }
}
