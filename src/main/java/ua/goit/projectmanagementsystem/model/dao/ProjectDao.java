package ua.goit.projectmanagementsystem.model.dao;

import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;

import java.util.Objects;
import java.util.Set;

public class ProjectDao {
    private Integer projectId;
    private String projectName;
    private Integer customerId;
    private Integer companyId;
    private Integer projectCost;
    private Set<DeveloperDao> developers;

    public ProjectDao() {
    }

    public ProjectDao(String projectName, Integer customerId, Integer companyId, Integer projectCost, Set<DeveloperDao> developers) {
        this.projectName = projectName;
        this.customerId = customerId;
        this.companyId = companyId;
        this.projectCost = projectCost;
        this.developers = developers;
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

    public Set<DeveloperDao> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<DeveloperDao> developers) {
        this.developers = developers;
    }

    @Override
    public String toString() {
        return "ProjectDao{" +
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
        ProjectDao that = (ProjectDao) o;
        return projectId.equals(that.projectId) && projectName.equals(that.projectName) && customerId.equals(that.customerId) && companyId.equals(that.companyId) && projectCost.equals(that.projectCost) && developers.equals(that.developers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, projectName, customerId, companyId, projectCost, developers);
    }
}
