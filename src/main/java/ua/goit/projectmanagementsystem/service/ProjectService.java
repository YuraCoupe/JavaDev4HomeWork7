package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.*;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperShortDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectShortDto;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperConverter developerConverter;
    private final DeveloperShortConverter developerShortConverter;
    private final ProjectConverter projectConverter;
    private final ProjectShortConverter projectShortConverter;
    private final DeveloperProjectConverter developerProjectConverter;

    public ProjectService(ProjectRepository projectRepository, DeveloperShortConverter developerShortConverter, DeveloperConverter developerConverter,
                          ProjectConverter projectConverter, ProjectShortConverter projectShortConverter, DeveloperProjectConverter developerProjectConverter) {
        this.projectRepository = projectRepository;
        this.developerProjectConverter = developerProjectConverter;
        this.developerConverter = developerConverter;
        this.developerShortConverter = developerShortConverter;
        this.projectConverter = projectConverter;
        this.projectShortConverter = projectShortConverter;
    }

    public Integer getSalarySum(Integer projectId) {
        return projectRepository.getSalarySum(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
    }

    public Set<DeveloperShortDto> findDevsByProjectId(Integer projectId) {
        Set<DeveloperDao> developersDao = projectRepository.findDevsByProjectId(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
        Set<DeveloperShortDto> developersShortDto = developersDao.stream()
                .map(developerDao -> developerShortConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        return developersShortDto;
    }

    public Set<ProjectShortDto> findAllProjectsWithDevelopersNumber() {
        HashMap<ProjectDao, Integer> projectDaoMap = projectRepository.findAllProjectsWithDevelopersNumber().orElseThrow(()
                -> new ProjectNotFoundException("There are no existed projects"));

        Set<ProjectShortDto> projectShortDtos;
        projectShortDtos = projectDaoMap.entrySet().stream()
                .map(projectDao -> {
                    ProjectShortDto projectShortDto = new ProjectShortDto();
                    projectShortDto.setProjectName(projectDao.getKey().getProjectName());
                    projectShortDto.setProjectCost(projectDao.getKey().getProjectCost());
                    projectShortDto.setDevelopersNumber(projectDao.getValue());
                    return projectShortDto;
                })
                .collect(Collectors.toSet());

        return projectShortDtos;
    }
}
