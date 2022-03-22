package ua.goit.projectmanagementsystem.controller.command;

import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.view.View;

import static ua.goit.projectmanagementsystem.controller.command.Commands.UPDATE_COMPANY;

public class CompanyUpdater implements Command {
    private final View view;
    private final CompanyService companyService;

    public CompanyUpdater(View view, CompanyService companyService) {
        this.view = view;
        this.companyService = companyService;
    }

    @Override
    public boolean canProccess(String input) {
        return input.equals(UPDATE_COMPANY.getName());
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

        view.write("Enter company name or press Enter to skip. Actual name is " + company.getCompanyName());
        name = view.read();

        view.write("Enter company location or press Enter to skip. Actual name is " + company.getCompanyLocation());
        String location = view.read();

        if (!name.equals("")) {
            company.setCompanyName(name);
        }
        if (!location.equals("")) {
            company.setCompanyLocation(location);
        }
        companyService.update(company);
        view.write("Company data updated");
    }
}
