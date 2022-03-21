package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.ProjectDto;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.HashMap;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_ALL_PROJECTS;

public class AllProjectsFinder implements Command {
    private final View view;
    private final ProjectService projectService;

    public AllProjectsFinder(View view, ProjectService projectService) {
        this.view = view;
        this.projectService = projectService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(FIND_ALL_PROJECTS.getName());
    }

    @Override
    public void process() {
        HashMap<ProjectDto, Integer> projects = projectService.findAllProjectsWithDevelopersNumber();
        view.write("Projects list:");
        for (HashMap.Entry<ProjectDto, Integer> entry : projects.entrySet()) {
            view.write(String.format("project_id %d project_name %s developers number %d"
                    , entry.getKey().getProjectId(), entry.getKey().getProjectName(), entry.getValue()));
        }
        view.write("\n");
    }
}
