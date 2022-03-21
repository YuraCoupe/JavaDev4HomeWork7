package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;

import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperService {
    public final DeveloperRepository developerRepository;
    public final DeveloperConverter developerConverter;
    public final DeveloperProjectConverter developerProjectConverter;

    public DeveloperService(DeveloperRepository developerRepository, DeveloperConverter developerConverter, DeveloperProjectConverter developerProjectConverter) {
        this.developerRepository = developerRepository;
        this.developerConverter = developerConverter;
        this.developerProjectConverter = developerProjectConverter;
    }

    public Set<DeveloperDto> findJavaDevelopers() {
        Set<DeveloperDao> developersDao = developerRepository.findJavaDevs().orElseThrow(()
                -> new DeveloperNotFoundException("Java developers do not find"));
        Set<DeveloperDto> developersDto = developersDao.stream()
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersDto;
    }

    public Set<DeveloperDto> findMiddleDevelopers() {
        Set<DeveloperDao> developersDao = developerRepository.findMiddleDevs().orElseThrow(()
                -> new DeveloperNotFoundException("Middle developers do not find"));
        Set<DeveloperDto> developersDto = developersDao.stream()
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersDto;
    }
}
