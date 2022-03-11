package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.Set;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_JAVA_DEVELOPERS;

public class FindJavaDevs implements Command{
    private final View view;
    private final DeveloperService developerService;

    public FindJavaDevs(View view, DeveloperService developerService) {
        this.view = view;
        this.developerService = developerService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(FIND_JAVA_DEVELOPERS.getName());
    }

    @Override
    public void process() {
        Set<DeveloperDto> developers = developerService.findJavaDevelopers();
        view.write("Java developers list:");
        developers.stream()
                .forEach(developer -> view.write(developer.toString()));
        view.write("\n");
    }
}
