package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.*;

public class Help implements Command{

    private final View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(HELP.getName());
    }

    @Override
    public void process() {
        view.write("Enter " + HELP.getName() + " to see available commands.");
        view.write("Enter " + EXIT.getName() + " to exit.");
        view.write("Enter " + ADD_COMPANY.getName() + " to add new company.");
        view.write("Enter " + DELETE_COMPANY.getName() + " to delete company.");
        view.write("Enter " + UPDATE_COMPANY.getName() + " to update company.");
        view.write("Enter " + FIND_COMPANY_BY_NAME.getName() + " to find company by name.");
        view.write("Enter " + GET_SALARY_SUM_BY_PROJECT_ID.getName() + " to find salary sum by project ID.");
        view.write("Enter " + FIND_DEVELOPERS_BY_PROJECT_ID.getName() + " to find developers by project ID.");
        view.write("Enter " + FIND_JAVA_DEVELOPERS.getName() + " to find all Java developers.");
        view.write("Enter " + FIND_MIDDLE_DEVELOPERS.getName() + " to find all middle developers.");
        view.write("Enter " + FIND_ALL_PROJECTS.getName() + " to get all projects list with developers number.");
    }
}
