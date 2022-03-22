package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;
import java.util.Set;

public class ProjectShortDto {
    private String projectName;
    private Integer projectCost;
    private Integer developersNumber;

    public ProjectShortDto() {
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Integer projectCost) {
        this.projectCost = projectCost;
    }

    public Integer getDevelopersNumber() {
        return developersNumber;
    }

    public void setDevelopersNumber(Integer developersNumber) {
        this.developersNumber = developersNumber;
    }

    @Override
    public String toString() {
        return "ProjectDto{" +
                ", projectName='" + projectName + '\'' +
                ", projectCost=" + projectCost +
                ", developers number=" + developersNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectShortDto that = (ProjectShortDto) o;
        return projectName.equals(that.projectName) && projectCost.equals(that.projectCost) && developersNumber.equals(that.developersNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, projectCost, developersNumber);
    }
}
