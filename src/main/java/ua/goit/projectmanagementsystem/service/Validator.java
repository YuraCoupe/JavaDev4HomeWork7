package ua.goit.projectmanagementsystem.service;

import com.oracle.wls.shaded.org.apache.xalan.xsltc.runtime.ErrorMessages_es;
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
}
