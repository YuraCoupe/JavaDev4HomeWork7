package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.converter.ProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperConverter developerConverter;
    private final DeveloperProjectConverter developerProjectConverter;

    public ProjectService(ProjectRepository projectRepository, DeveloperConverter developerConverter, DeveloperProjectConverter developerProjectConverter) {
        this.projectRepository = projectRepository;
        this.developerProjectConverter = developerProjectConverter;
        this.developerConverter = developerConverter;
    }

    public Integer getSalarySum(Integer projectId) {
        return projectRepository.getSalarySum(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
    }

    public Set<DeveloperDto> findDevsByProjectId(Integer projectId) {
        Set<DeveloperDao> developersDao = projectRepository.findDevsByProjectId(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
        Set<DeveloperDto> developersDto = developersDao.stream()
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersDto;
    }
}
