package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.repository.DeveloperProjectDAO;


public class DeveloperProjectService {
    private final DeveloperProjectDAO developerProjectDAO;

    public DeveloperProjectService(DeveloperProjectDAO developerProjectDAO) {
        this.developerProjectDAO = developerProjectDAO;
    }

    public Integer save(Integer developerId, Integer projectId) {
        return developerProjectDAO.save(developerId, projectId);
    }

    public DeveloperProject findByIds(Integer developerId, Integer projectId) {
        return developerProjectDAO.findByIds(developerId, projectId).orElseThrow(()
                -> new CompanyNotFoundException("Entity does not exist"));
    }

    public void delete(DeveloperProject developerProject) {
        if (!developerProjectDAO.findByIds(developerProject.getDeveloperId(), developerProject.getProjectId()).isEmpty()) {
            developerProjectDAO.delete(developerProject);
        } else {
            throw new CompanyNotFoundException("This entity doesn't exist");
        }
    }
}
