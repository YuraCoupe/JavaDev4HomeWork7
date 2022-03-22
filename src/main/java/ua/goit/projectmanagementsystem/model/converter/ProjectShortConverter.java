package ua.goit.projectmanagementsystem.model.converter;

import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.ProjectShortDto;

public class ProjectShortConverter {

    public ProjectShortDto daoToDto(ProjectDao projectDao, Integer developersNumber) {
        ProjectShortDto projectShortDto = new ProjectShortDto();
        projectShortDto.setProjectName(projectDao.getProjectName());
        projectShortDto.setProjectCost(projectDao.getProjectCost());
        projectShortDto.setDevelopersNumber(developersNumber);
        return projectShortDto;
    }
}
