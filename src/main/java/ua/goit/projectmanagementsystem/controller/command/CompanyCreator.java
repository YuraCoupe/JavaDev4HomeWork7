package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.ADD_COMPANY;
import static ua.goit.projectmanagementsystem.controller.command.Commands.GET_SALARY_SUM_BY_PROJECT_ID;

public class CompanyCreator implements Command {
    private final View view;
    private final CompanyService companyService;

    public CompanyCreator(View view, CompanyService companyService) {
        this.view = view;
        this.companyService = companyService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(ADD_COMPANY.getName());
    }

    @Override
    public void process() {
        String name;
        while (true) {
            view.write("Enter company name:");
            name = view.read();
            if (!companyService.isCompanyExist(name)) {
                break;
            }
            view.write("Company already exists.");
        }

        view.write("Enter company location:");
        String location = view.read();


        CompanyDto company = new CompanyDto(name, location);
        companyService.save(company);
        view.write(String.format("Company %s added located in %s added to database", name, location));
    }
}
