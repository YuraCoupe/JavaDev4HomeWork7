package ua.goit.projectmanagementsystem.service;

import jakarta.servlet.http.HttpServletRequest;
import ua.goit.projectmanagementsystem.DAO.CompanyDAO;
import ua.goit.projectmanagementsystem.DAO.DeveloperDAO;
import ua.goit.projectmanagementsystem.DAO.ProjectDAO;
import ua.goit.projectmanagementsystem.model.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    private final CompanyDAO companyDAO;
    private final DeveloperDAO developerDAO;
    private final ProjectDAO projectDAO;

    public Validator(CompanyDAO companyDAO, DeveloperDAO developerDAO, ProjectDAO projectDAO) {
        this.companyDAO = companyDAO;
        this.developerDAO = developerDAO;
        this.projectDAO = projectDAO;
    }

    public ErrorMessage validateCompany(HttpServletRequest req) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<String> errors = new ArrayList<>();

        String companyName = req.getParameter("companyName");
        if (companyName.isBlank()) {
            errors.add("Company name can not be empty");
        }

        String companyIdString = req.getParameter("companyId");
        if (companyIdString.isBlank() & companyDAO.findByName(companyName).isPresent()) {
            errors.add(String.format("Company with name %s already exists", companyName));
        }

        String companyLocation = req.getParameter("companyLocation");
        if (companyLocation.isBlank()) {
            errors.add("Company location can not be empty");
        }
        errorMessage.setErrors(errors);
        return errorMessage;
    }

    public ErrorMessage validateDeveloper(HttpServletRequest req) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<String> errors = new ArrayList<>();

        String firstName = req.getParameter("firstName");
        if (firstName.isBlank()) {
            errors.add("First name can not be empty");
        }

        String lastName = req.getParameter("lastName");
        if (lastName.isBlank()) {
            errors.add("Last name can not be empty");
        }

        if (developerDAO.findByName(firstName, lastName).isPresent()) {
            errors.add(String.format("Developer with name %s %s already exists", firstName, lastName));
        }

        try {
            Integer.parseInt(req.getParameter("age"));
        } catch (Exception e) {
            errors.add("Age is invalid");
        }

        try {
            Integer.parseInt(req.getParameter("salary"));
        } catch (Exception e) {
            errors.add("Salary is invalid");
        }

        errorMessage.setErrors(errors);
        return errorMessage;
    }

    public ErrorMessage validateProject(HttpServletRequest req) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<String> errors = new ArrayList<>();

        String projectName = req.getParameter("projectName");
        if (projectName.isBlank()) {
            errors.add("Project name can not be empty");
        }

        String projectIdString = req.getParameter("projectId");
        if (projectIdString.isBlank() & projectDAO.findByName(projectName).isPresent()) {
            errors.add(String.format("Project with name %s already exists", projectName));
        }

        try {
            Integer.parseInt(req.getParameter("customerId"));
        } catch (Exception e) {
            errors.add("Customer is invalid");
        }

        try {
            Integer.parseInt(req.getParameter("companyId"));
        } catch (Exception e) {
            errors.add("Company is invalid");
        }

        try {
            Integer.parseInt(req.getParameter("projectCost"));
        } catch (Exception e) {
            errors.add("Project cost is invalid");
        }

        errorMessage.setErrors(errors);
        return errorMessage;
    }
}
