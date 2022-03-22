package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.DeveloperShortDto;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.Set;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_MIDDLE_DEVELOPERS;

public class MiddleDevsFinder implements Command{
    private final View view;
    private final DeveloperService developerService;

    public MiddleDevsFinder(View view, DeveloperService developerService) {
        this.view = view;
        this.developerService = developerService;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(FIND_MIDDLE_DEVELOPERS.getName());
    }

    @Override
    public void process() {
        Set<DeveloperShortDto> developers = developerService.findMiddleDevelopers();
        view.write("Middle developers list:");
        developers.stream()
                .forEach(developer -> view.write(developer.toString()));
        view.write("\n");
    }
}
