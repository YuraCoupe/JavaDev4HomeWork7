package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.FIND_COMPANY_BY_NAME;

public class CompanyFinder implements Command{

    private final View view;
    private final CompanyService companyService;

    public CompanyFinder(View view, CompanyService companyService) {
        this.view = view;
        this.companyService = companyService;
    }

    @Override
    public boolean canProcess(String input) {
        return input.equals(FIND_COMPANY_BY_NAME.getName());
    }

    @Override
    public void process() {
        view.write("Enter company name");
        String name = view.read();
        CompanyDto company = companyService.findByName(name);
        view.write("Company " + name + " located in " + company.getCompanyLocation());
    }
}
