package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.ProjectDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectShortDto;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.HashMap;
import java.util.Set;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_ALL_PROJECTS;

public class AllProjectsFinder implements Command {
    private final View view;
    private final ProjectService projectService;

    public AllProjectsFinder(View view, ProjectService projectService) {
        this.view = view;
        this.projectService = projectService;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(FIND_ALL_PROJECTS.getName());
    }

    @Override
    public void process() {
        Set<ProjectShortDto> projects = projectService.findAllProjectsWithDevelopersNumber();
        view.write("Projects list:");
        projects.stream()
                .forEach(project -> view.write(project.toString()));
    }
}
