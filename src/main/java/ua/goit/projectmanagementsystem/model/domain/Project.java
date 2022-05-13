package ua.goit.projectmanagementsystem.model.domain;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "projects")
public class Project {
    private Integer projectId;
    private String projectName;
    private Integer customerId;
    private Integer projectCost;
    private Set<Developer> developers;
    private Company company;

    public Project() {
    }

    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "project_id_generator")
    @SequenceGenerator(name = "project_id_generator", sequenceName = "projects_project_id_seq", allocationSize = 1)
    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Column(name = "customer_id")
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Column(name = "project_cost")
    public Integer getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(Integer projectCost) {
        this.projectCost = projectCost;
    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "developerstoprojects",
            joinColumns = { @JoinColumn(name = "project_id") },
            inverseJoinColumns = { @JoinColumn(name = "developer_id") }
    )
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
