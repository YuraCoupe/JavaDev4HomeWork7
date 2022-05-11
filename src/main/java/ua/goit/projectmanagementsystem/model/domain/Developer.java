package ua.goit.projectmanagementsystem.model.domain;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "developers")
public class Developer {
    private Integer developerId;
    private String firstName;
    private String lastName;
    private Integer age;
    private String sex;
    private Integer salary;
    //private Set<Skill> skills;
    private Set<Project> projects;
    private Company company;




    @Id
    @Column(name = "developer_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "developer_id_generator")
    @SequenceGenerator(name = "developer_id_generator", sequenceName = "developers_developer_id_seq", allocationSize = 1)
    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "age")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Column(name = "sex")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "salary")
    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @ManyToOne
    @JoinColumn(name="company_id", nullable=false)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
//    public Set<Skill> getSkills() {
//        return skills;
//    }
//
//    public void setSkills(Set<Skill> skills) {
//        this.skills = skills;
//    }

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "developerstoprojects",
            joinColumns = { @JoinColumn(name = "developer_id") },
            inverseJoinColumns = { @JoinColumn(name = "project_id") }
    )
    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Developer() {
    }

    @Override
    public String toString() {
        return "DeveloperDto{" +
                "developerId=" + developerId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", company=" + company +
                ", salary=" + salary +
//                ", skills=" + skills +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Developer that = (Developer) o;
        return developerId.equals(that.developerId) && firstName.equals(that.firstName)
                && lastName.equals(that.lastName) && age.equals(that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, firstName, lastName, age);
    }
}
