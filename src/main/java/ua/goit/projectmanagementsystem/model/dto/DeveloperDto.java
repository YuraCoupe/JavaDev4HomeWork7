package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;
import java.util.Set;

public class DeveloperDto {
    private Integer developerId;
    private String firstname;
    private String lastname;
    private Integer age;
    private String sex;
    private Integer companyId;
    private Integer salary;
    private Set<SkillDto> skills;
    private Set<ProjectDto> projects;

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Set<SkillDto> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDto> skills) {
        this.skills = skills;
    }

    public Set<ProjectDto> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDto> projects) {
        this.projects = projects;
    }

    public DeveloperDto() {
    }

    @Override
    public String toString() {
        return "DeveloperDto{" +
                "developerId=" + developerId +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", companyId=" + companyId +
                ", salary=" + salary +
                ", skills=" + skills +
                ", projects=" + projects +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperDto that = (DeveloperDto) o;
        return developerId.equals(that.developerId) && firstname.equals(that.firstname)
                && lastname.equals(that.lastname) && age.equals(that.age)
                && sex.equals(that.sex) && companyId.equals(that.companyId)
                && salary.equals(that.salary) && skills.equals(that.skills)
                && projects.equals((that.projects));
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, firstname, lastname, age, sex, companyId, salary, skills, projects);
    }
}
