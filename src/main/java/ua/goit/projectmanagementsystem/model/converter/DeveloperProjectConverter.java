package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectDto;

import java.util.Set;
import java.util.stream.Collectors;

public class DeveloperProjectConverter {
    private final DeveloperConverter developerConverter;
    private final ProjectConverter projectConverter;

    public DeveloperProjectConverter(DeveloperConverter developerConverter, ProjectConverter projectConverter) {
        this.developerConverter = developerConverter;
        this.projectConverter = projectConverter;
    }

    /*public DeveloperDto daoToDto(DeveloperDao developerDao) {
        DeveloperDto developerDto = developerConverter.daoToDto(developerDao);
        Set<ProjectDto> projects = developerDao.getProjects().stream()
                .map(projectDao -> projectConverter.daoToDto(projectDao))
                .collect(Collectors.toSet());
        developerDto.setProjects(projects);
        return developerDto;
    }

    public DeveloperDao dtoToDao(DeveloperDto developerDto) {
        DeveloperDao developerDao = developerConverter.dtoToDao(developerDto);
        Set<ProjectDao> projects = developerDto.getProjects().stream()
                .map(projectDto -> projectConverter.dtoToDao(projectDto))
                .collect(Collectors.toSet());
        developerDao.setProjects(projects);
        return developerDao;
    }

    public ProjectDto daoToDto(ProjectDao projectDao) {
        ProjectDto projectDto = projectConverter.daoToDto(projectDao);
        Set<DeveloperDto> developers = projectDao.getDevelopers().stream()
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        projectDto.setDevelopers(developers);
        return projectDto;
    }

    public ProjectDao dtoToDao(ProjectDto projectDto) {
        ProjectDao projectDao = projectConverter.dtoToDao(projectDto);
        Set<DeveloperDao> developers = projectDto.getDevelopers().stream()
                .map(developerDto -> developerConverter.dtoToDao(developerDto))
                .collect(Collectors.toSet());
        projectDao.setDevelopers(developers);
        return projectDao;
    }*/
}
