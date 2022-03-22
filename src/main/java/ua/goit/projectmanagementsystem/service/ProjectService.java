package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperConverter;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.converter.ProjectConverter;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectDto;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;

import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperConverter developerConverter;
    private final ProjectConverter projectConverter;
    private final DeveloperProjectConverter developerProjectConverter;

    public ProjectService(ProjectRepository projectRepository, DeveloperConverter developerConverter, ProjectConverter projectConverter, DeveloperProjectConverter developerProjectConverter) {
        this.projectRepository = projectRepository;
        this.developerProjectConverter = developerProjectConverter;
        this.developerConverter = developerConverter;
        this.projectConverter = projectConverter;
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

    public Set<ProjectDto> findAllProjects() {
        Set<ProjectDao> projectDaos = projectRepository.findAllProjects().orElseThrow(()
                -> new ProjectNotFoundException("There are no existed projects"));
        Set<ProjectDto> projectDtos = projectDaos.stream()
                .map(projectDao -> projectConverter.daoToDto(projectDao))
                .collect(Collectors.toSet());
        return projectDtos;
    }

    public HashMap<ProjectDto, Integer> findAllProjectsWithDevelopersNumber() {
        HashMap<ProjectDao, Integer> projectDaoMap = projectRepository.findAllProjectsWithDevelopersNumber().orElseThrow(()
                -> new ProjectNotFoundException("There are no existed projects"));
        HashMap<ProjectDto, Integer> projectDtoMap = new HashMap<>();
        for (HashMap.Entry<ProjectDao, Integer> entry : projectDaoMap.entrySet()) {
            projectDtoMap.put(projectConverter.daoToDto(entry.getKey()), entry.getValue());

        }
        return projectDtoMap;
    }
}
