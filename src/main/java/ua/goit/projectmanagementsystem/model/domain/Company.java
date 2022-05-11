package ua.goit.projectmanagementsystem.model.domain;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "companies")
public class Company {
    private Integer companyId;
    private String companyName;
    private String companyLocation;
    private Set<Developer> developers;
    private Set<Project> projects;

    public Company() {
    }

    public Company(String companyName, String companyLocation) {
        this.companyName = companyName;
        this.companyLocation = companyLocation;
    }


    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_id_generator")
    @SequenceGenerator(name = "company_id_generator", sequenceName = "companies_company_id_seq", allocationSize = 1)
    public Integer getCompanyId() {
        return companyId;
    }

    @Column(name = "company_name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Column(name = "company_location")
    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    @OneToMany(mappedBy = "company")
    public Set<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<Developer> developers) {
        this.developers = developers;
    }

    @OneToMany(mappedBy = "company")
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Company{" +
                "companyId=" + companyId +
                ", companyName='" + companyName + '\'' +
                ", companyLocation='" + companyLocation + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company that = (Company) o;
        return companyId.equals(that.companyId) && companyName.equals(that.companyName) && companyLocation.equals(that.companyLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyId, companyName, companyLocation);
    }
}
