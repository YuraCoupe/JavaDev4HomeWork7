package ua.goit.projectmanagementsystem.service;

import jakarta.servlet.http.HttpServletRequest;
import ua.goit.projectmanagementsystem.model.ErrorMessage;

import java.util.ArrayList;
import java.util.List;

public class Validator {
    public ErrorMessage validateCompany(HttpServletRequest req) {
        ErrorMessage errorMessage = new ErrorMessage();
        List<String> errors = new ArrayList<>();

        String companyName = req.getParameter("companyName");
        if (companyName.isBlank()) {
            errors.add("Company name can not be empty");
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
