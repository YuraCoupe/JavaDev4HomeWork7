package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.model.dto.ProjectWithCompanyDto;
import ua.goit.projectmanagementsystem.DAO.CompanyDAO;
import ua.goit.projectmanagementsystem.DAO.ProjectDAO;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectDAO projectDAO;
    private final CompanyDAO companyDAO;

    public ProjectService(ProjectDAO projectDAO, CompanyDAO companyDAO) {
        this.projectDAO = projectDAO;
        this.companyDAO = companyDAO;
    }

    public Integer save(Project project) {
        Integer id = null;
        if (projectDAO.findByName(project.getProjectName()).isEmpty()) {
            id = projectDAO.save(project);
        } else {
            throw new CompanyAlreadyExistException("This project already exists");
        }
        return id;
    }

    public void delete(Project project) {
        if (!projectDAO.findById(project.getProjectId()).isEmpty()) {
            projectDAO.delete(project);
        } else {
            throw new ProjectNotFoundException("This project doesn't exist");
        }
    }

    public void update(Project project) {
        projectDAO.update(project);
    }

    public List<Project> findAll() {
        return projectDAO.findAll().orElseThrow(()
                -> new CompanyNotFoundException("Projects do not exist"));
    }

    public Project findById(Integer id) {
        return projectDAO.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %d does not exist", id)));
    }

    public Project findByName(String name) {
        return projectDAO.findByName(name).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %s does not exist", name)));
    }

    public List<Project> findWithoutThisDeveloperId(Integer developerId) {
        return projectDAO.findWithoutThisDeveloperId(developerId);
    }

    public List<Project> findByDeveloperId(Integer developerId) {
        return projectDAO.findByDeveloperId(developerId);
    }

    public List<ProjectWithCompanyDto> findAllWithCompany() {
        List<Project> projects = projectDAO.findAll().orElseThrow(()
                -> new CompanyNotFoundException("Projects do not exist"));
        List<ProjectWithCompanyDto> projectWithCompanyDtos = projects.stream()
                .map(project -> {
                    ProjectWithCompanyDto projectWithCompanyDto = new ProjectWithCompanyDto();
                    projectWithCompanyDto.setProjectId(project.getProjectId());
                    projectWithCompanyDto.setProjectName(project.getProjectName());
                    projectWithCompanyDto.setCustomerId(project.getCustomerId());
                    projectWithCompanyDto.setCompanyId(project.getCompanyId());
                    projectWithCompanyDto.setProjectCost(project.getProjectCost());
                    projectWithCompanyDto.setCompany(companyDAO.findById(project.getCompanyId()).orElseThrow(()
                            -> new CompanyNotFoundException(String.format("Company %d does not exist", project.getCompanyId()))));
                    return projectWithCompanyDto;
                })
                .collect(Collectors.toList());
        return projectWithCompanyDtos;
    }
}
