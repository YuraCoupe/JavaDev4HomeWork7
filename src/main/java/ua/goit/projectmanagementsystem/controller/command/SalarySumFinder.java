package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.GET_SALARY_SUM_BY_PROJECT_ID;

public class SalarySumFinder implements Command {
    private final View view;
    private final ProjectService projectService;

    public SalarySumFinder(View view, ProjectService projectService) {
        this.view = view;
        this.projectService = projectService;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(GET_SALARY_SUM_BY_PROJECT_ID.getName());
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
        Integer projectSalarySum = projectService.getSalarySum(projectId);
        view.write("Salary sum of project with ID " + projectId + " equals " + projectSalarySum + " USD.\n");
    }
}
