package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;

public class CompanyDto {
    private Integer companyId;
    private String companyName;
    private String companyLocation;

    public CompanyDto() {
    }

    public CompanyDto(String companyName, String companyLocation) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDto that = (CompanyDto) o;
        return companyId.equals(that.companyId) && companyName.equals(that.companyName) && companyLocation.equals(that.companyLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, companyLocation);
    }
}
