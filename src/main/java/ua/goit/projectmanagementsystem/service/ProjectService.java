package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.CompanyConverter;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.model.dao.ProjectDao;
import ua.goit.projectmanagementsystem.model.dto.ProjectShortDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectWithCompanyDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final CompanyConverter companyConverter;


    public ProjectService(ProjectRepository projectRepository, CompanyRepository companyRepository, CompanyConverter companyConverter) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.companyConverter = companyConverter;
    }

    public Integer getSalarySum(Integer projectId) {
        return projectRepository.getSalarySum(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
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

    public Integer save(Project project) {
        Integer id = null;
        if (projectRepository.findByName(project.getProjectName()).isEmpty()) {
            id = projectRepository.save(project);
        } else {
            throw new CompanyAlreadyExistException("This project already exists");
        }
        return id;
    }

    public void delete(Project project) {
        if (!projectRepository.findById(project.getProjectId()).isEmpty()) {
            projectRepository.delete(project);
        } else {
            throw new ProjectNotFoundException("This project doesn't exist");
        }
    }

    public void update(Project project) {
        projectRepository.update(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll().orElseThrow(()
                -> new CompanyNotFoundException("Projects do not exist"));
    }

    public Project findById(Integer id) {
        return projectRepository.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %d does not exist", id)));
    }

    public Project findByName(String name) {
        return projectRepository.findByName(name).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %s does not exist", name)));
    }

    public List<Project> findWithoutThisDeveloperId(Integer developerId) {
        return projectRepository.findWithoutThisDeveloperId(developerId);
    }

    public List<Project> findByDeveloperId(Integer developerId) {
        return projectRepository.findByDeveloperId(developerId);
    }

    public List<ProjectWithCompanyDto> findAllWithCompany() {
        List<Project> projects = projectRepository.findAll().orElseThrow(()
                -> new CompanyNotFoundException("Projects do not exist"));
        List<ProjectWithCompanyDto> projectWithCompanyDtos = projects.stream()
                .map(project -> {
                    ProjectWithCompanyDto projectWithCompanyDto = new ProjectWithCompanyDto();
                    projectWithCompanyDto.setProjectId(project.getProjectId());
                    projectWithCompanyDto.setProjectName(project.getProjectName());
                    projectWithCompanyDto.setCustomerId(project.getCustomerId());
                    projectWithCompanyDto.setCompanyId(project.getCompanyId());
                    projectWithCompanyDto.setProjectCost(project.getProjectCost());
                    projectWithCompanyDto.setCompanyDto(companyConverter.daoToDto(companyRepository.findById(project.getCompanyId()).orElseThrow(()
                            -> new CompanyNotFoundException(String.format("Company %d does not exist", project.getCompanyId())))));
                    return projectWithCompanyDto;
                })
                .collect(Collectors.toList());
        return projectWithCompanyDtos;
    }
}
