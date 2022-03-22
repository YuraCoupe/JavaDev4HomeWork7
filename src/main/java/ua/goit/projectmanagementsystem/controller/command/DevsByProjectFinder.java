package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.Set;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_DEVELOPERS_BY_PROJECT_ID;

public class DevsByProjectFinder implements Command{

    private final View view;
    private final ProjectService projectService;

    public DevsByProjectFinder(View view, ProjectService projectService) {
        this.view = view;
        this.projectService = projectService;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(FIND_DEVELOPERS_BY_PROJECT_ID.getName());
    }

    @Override
    public void process() {
        view.write("Enter project ID");
        Integer projectId = 0;
        while (true) {
            try {
                projectId = Integer.parseInt(view.read());
                break;
            } catch (NumberFormatException e) {
                view.write("Please, write correct ID");
            }
        }
        Set<DeveloperDto> developers = projectService.findDevsByProjectId(projectId);

        view.write("Project with ID " + projectId + " developers list:");

        developers.stream()
                .forEach(developer -> view.write(developer.toString()));
        view.write("\n");
    }
}
