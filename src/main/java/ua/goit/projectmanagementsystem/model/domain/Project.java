package ua.goit.projectmanagementsystem.model.domain;

public class Project {
    private Integer projectId;
    private String projectName;
    private Integer customerId;
    private Integer companyId;
    private Integer projectCost;

    public Project(Integer projectId, String projectName, Integer customerId, Integer companyId, Integer projectCost) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.customerId = customerId;
        this.companyId = companyId;
        this.projectCost = projectCost;
    }

    public Project() {

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
}
