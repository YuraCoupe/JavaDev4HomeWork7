package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectDto;

import java.util.Set;
import java.util.stream.Collectors;

public class ProjectConverter implements Converter<ProjectDao, ProjectDto> {
    private final DeveloperConverter developerConverter;

    public ProjectConverter(DeveloperConverter developerConverter) {
        this.developerConverter = developerConverter;
    }

    @Override
    public ProjectDto daoToDto(ProjectDao projectDao) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(projectDao.getProjectId());
        projectDto.setProjectName(projectDao.getProjectName());
        projectDto.setCompanyId(projectDao.getCompanyId());
        projectDto.setCustomerId(projectDao.getCustomerId());
        projectDto.setProjectCost(projectDao.getProjectCost());
        Set<DeveloperDto> developers = projectDao.getDevelopers().stream()
                .map(developerDao -> developerConverter.daoToDto(developerDao))
                .collect(Collectors.toSet());
        projectDto.setDevelopers(developers);
        return projectDto;
    }

    @Override
    public ProjectDao dtoToDao(ProjectDto projectDto) {
        ProjectDao projectDao = new ProjectDao();
        projectDao.setProjectId(projectDto.getProjectId());
        projectDao.setProjectName(projectDto.getProjectName());
        projectDao.setCompanyId(projectDto.getCompanyId());
        projectDao.setCustomerId(projectDto.getCustomerId());
        projectDao.setProjectCost(projectDto.getProjectCost());
        Set<DeveloperDao> developers = projectDto.getDevelopers().stream()
                .map(developerDto -> developerConverter.dtoToDao(developerDto))
                .collect(Collectors.toSet());
        projectDao.setDevelopers(developers);
        return projectDao;
    }
}
