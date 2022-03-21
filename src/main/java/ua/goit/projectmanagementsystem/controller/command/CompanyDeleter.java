package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.ADD_COMPANY;
import static ua.goit.projectmanagementsystem.controller.command.Commands.DELETE_COMPANY;

public class CompanyDeleter implements Command {
    private final View view;
    private final CompanyService companyService;

    public CompanyDeleter(View view, CompanyService companyService) {
        this.view = view;
        this.companyService = companyService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(DELETE_COMPANY.getName());
    }

    @Override
    public void process() {
        String name;
        while (true) {
            view.write("Enter company name:");
            name = view.read();
            if (companyService.isCompanyExist(name)) {
                break;
            }
            view.write("Company doesn't exist.");
        }

        CompanyDto company = companyService.findByName(name);
        companyService.delete(company);
        view.write(String.format("Company %s deleted from database", name));
    }
}
