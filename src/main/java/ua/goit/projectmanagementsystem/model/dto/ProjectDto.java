package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;
import java.util.Set;

public class ProjectDto {
    private Integer projectId;
    private String projectName;
    private Integer customerId;
    private Integer companyId;
    private Integer projectCost;
    private Set<DeveloperDto> developers;

    public ProjectDto() {
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Integer projectCost) {
        this.projectCost = projectCost;
    }

    public Set<DeveloperDto> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDto> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                "projectId=" + projectId +
                ", projectName='" + projectName + '\'' +
                ", customerId=" + customerId +
                ", companyId=" + companyId +
                ", projectCost=" + projectCost +
                ", developers=" + developers +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectDto that = (ProjectDto) o;
        return projectId.equals(that.projectId) && projectName.equals(that.projectName)
                && customerId.equals(that.customerId) && companyId.equals(that.companyId)
                && projectCost.equals(that.projectCost) && developers.equals(that.developers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, customerId, companyId, projectCost, developers);
    }
}
