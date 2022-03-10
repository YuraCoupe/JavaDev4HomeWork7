package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.converter.DeveloperProjectConverter;
import ua.goit.projectmanagementsystem.model.converter.ProjectConverter;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;

public class ProjectService {
    private final ProjectRepository projectRepository;
    private final DeveloperProjectConverter developerProjectConverter;

    public ProjectService(ProjectRepository projectRepository, DeveloperProjectConverter developerProjectConverter) {
        this.projectRepository = projectRepository;
        this.developerProjectConverter = developerProjectConverter;
    }

    public Integer getSalarySum(Integer projectId) {
        return projectRepository.getSalarySum(projectId).orElseThrow(()
                -> new ProjectNotFoundException(String.format("Project with ID %d does not exist", projectId)));
    }
}
