package ua.goit.projectmanagementsystem.model.dao;

import ua.goit.projectmanagementsystem.model.dto.ProjectDto;
import ua.goit.projectmanagementsystem.model.dto.SkillDto;

import java.util.Objects;
import java.util.Set;

public class DeveloperDao {
    private Integer developerId;
    private String firstname;
    private String lastname;
    private Integer age;
    private String sex;
    private Integer companyId;
    private Integer salary;
    private Set<SkillDao> skills;
    private Set<ProjectDao> projects;

    public DeveloperDao() {
    }

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

    public Set<SkillDao> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDao> skills) {
        this.skills = skills;
    }

    public Set<ProjectDao> getProjects() {
        return projects;
    }

    public void setProjects(Set<ProjectDao> projects) {
        this.projects = projects;
    }

    public DeveloperDao(String firstname, String lastname, Integer age, String sex, Integer companyId, Integer salary, Set<SkillDao> skills, Set<ProjectDao> projects) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.sex = sex;
        this.companyId = companyId;
        this.salary = salary;
        this.skills = skills;
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "DeveloperDao{" +
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
        DeveloperDao that = (DeveloperDao) o;
        return developerId.equals(that.developerId) && firstname.equals(that.firstname) && lastname.equals(that.lastname) && Objects.equals(age, that.age) && sex.equals(that.sex) && companyId.equals(that.companyId) && salary.equals(that.salary) && skills.equals(that.skills) && projects.equals(that.projects);
    }

    @Override
    public int hashCode() {
        return Objects.hash(developerId, firstname, lastname, age, sex, companyId, salary, skills, projects);
    }
}
