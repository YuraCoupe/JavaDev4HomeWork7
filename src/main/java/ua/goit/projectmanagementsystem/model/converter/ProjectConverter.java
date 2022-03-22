package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.ProjectDto;

public class ProjectConverter implements Converter<ProjectDao, ProjectDto> {

    @Override
    public ProjectDto daoToDto(ProjectDao projectDao) {
        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectId(projectDao.getProjectId());
        projectDto.setProjectName(projectDao.getProjectName());
        projectDto.setCompanyId(projectDao.getCompanyId());
        projectDto.setCustomerId(projectDao.getCustomerId());
        projectDto.setProjectCost(projectDao.getProjectCost());
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
        return projectDao;
    }
}
