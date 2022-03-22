package ua.goit.projectmanagementsystem.controller;

import ua.goit.projectmanagementsystem.controller.command.*;
import ua.goit.projectmanagementsystem.exception.ExitException;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PMSController {
    private final View view;
    private final List<Command> commands;

    public PMSController(View view, ProjectService projectService, DeveloperService developerService, CompanyService companyService) {
        this.view = view;
        this.commands = new ArrayList<>(Arrays.asList(
                new Exit(view),
                new Help(view),
                new CompanyCreator(view, companyService),
                new CompanyFinder(view, companyService),
                new CompanyDeleter(view, companyService),
                new CompanyUpdater(view, companyService),
                new SalarySumFinder(view, projectService),
                new DevsByProjectFinder(view, projectService),
                new JavaDevsFinder(view, developerService),
                new MiddleDevsFinder(view, developerService),
                new AllProjectsFinder(view, projectService)
        ));
    }

    public void run() {
        view.write("Welcome to project management system");
        executeCommand();
    }

    private void executeCommand() {
        try {
            while (true) {
                view.write("Please, enter command or enter help to see available commands");
                String input = view.read();
                boolean isIncorrectCommand = true;
                for (Command command : commands) {
                    if (command.canProccess(input)) {
                        command.process();
                        isIncorrectCommand = false;
                    }
                }
                if (isIncorrectCommand) {
                    view.write("Incorrect command. Please, try again");
                }
            }
        } catch (ExitException e) {
            view.write("Good bye!");
        }
    }
}
