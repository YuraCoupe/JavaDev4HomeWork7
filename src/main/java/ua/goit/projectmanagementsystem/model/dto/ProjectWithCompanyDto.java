package ua.goit.projectmanagementsystem.model.dto;

import ua.goit.projectmanagementsystem.model.domain.Project;

import java.util.Objects;
import java.util.Set;

public class ProjectWithCompanyDto extends Project {
    private CompanyDto companyDto;

    public CompanyDto getCompanyDto() {
        return companyDto;
    }

    public void setCompanyDto(CompanyDto companyDto) {
        this.companyDto = companyDto;
    }
}
