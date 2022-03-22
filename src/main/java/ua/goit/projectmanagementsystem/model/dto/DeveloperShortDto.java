package ua.goit.projectmanagementsystem.model.dto;

import java.util.Objects;
import java.util.Set;

public class DeveloperShortDto {
    private String firstname;
    private String lastname;
    private Integer salary;

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

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Developer{" +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeveloperShortDto that = (DeveloperShortDto) o;
        return firstname.equals(that.firstname) && lastname.equals(that.lastname) && salary.equals(that.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstname, lastname, salary);
    }
}
