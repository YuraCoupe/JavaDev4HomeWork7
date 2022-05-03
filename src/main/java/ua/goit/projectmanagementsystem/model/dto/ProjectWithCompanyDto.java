package ua.goit.projectmanagementsystem.model.dto;

import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Project;

public class ProjectWithCompanyDto extends Project {
    private Company companyDto;

    public Company getCompanyDto() {
        return companyDto;
    }

    public void setCompany(Company company) {
        this.companyDto = company;
    }
}

