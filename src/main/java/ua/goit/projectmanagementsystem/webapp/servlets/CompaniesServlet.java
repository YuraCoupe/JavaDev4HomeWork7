package ua.goit.projectmanagementsystem.webapp.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.projectmanagementsystem.DAO.DeveloperDAO;
import ua.goit.projectmanagementsystem.DAO.ProjectDAO;
import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.config.PostgresHikariProvider;
import ua.goit.projectmanagementsystem.config.PropertiesUtil;
import ua.goit.projectmanagementsystem.model.ErrorMessage;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.DAO.CompanyDAO;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.service.Validator;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/companies/*")
public class CompaniesServlet extends HttpServlet {
    private CompanyService companyService;
    private DeveloperService developerService;
    private static Gson jsonParser = new Gson();
    private Validator validator;


    @Override
    public void init() {
        PropertiesUtil util = new PropertiesUtil(getServletContext());

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());
        CompanyDAO companyDAO = new CompanyDAO(dbConnector);
        DeveloperDAO developerDAO = new DeveloperDAO(dbConnector);
        ProjectDAO projectDAO = new ProjectDAO(dbConnector);
        this.companyService = new CompanyService(companyDAO);
        this.developerService = new DeveloperService(developerDAO, companyDAO);
        validator = new Validator(companyDAO, developerDAO, projectDAO);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer companyId = null;
        Company company = new Company();

        if (!req.getParameter("companyId").isBlank()) {
            companyId = Integer.parseInt(req.getParameter("companyId"));
            company.setCompanyId(companyId);
        }

        ErrorMessage errorMessage = validator.validateCompany(req);
        if (!errorMessage.getErrors().isEmpty()) {
            req.setAttribute("errorMessage", errorMessage);
            if (Objects.nonNull(companyId)) {
                handleId(companyId, req, resp);
            } else {
                handleNew(req, resp);
            }
            return;
        }

        String companyName = req.getParameter("companyName");
        String companyLocation = req.getParameter("companyLocation");

        company.setCompanyName(companyName);
        company.setCompanyLocation(companyLocation);

        if (Objects.isNull(companyId)) {
            companyId = companyService.save(company);
        } else {
            companyService.update(company);
        }

        Integer developerId;
        Developer developer;
        if (Objects.nonNull(req.getParameter("developerId"))) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            developer = developerService.findById(developerId);
            developer.setCompanyId(companyId);
            developerService.update(developer);
        }
        resp.sendRedirect("/companies");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/companies/?", "");
        String deleteId = req.getParameter("deleteId");
        String companyId = req.getParameter("companyId");
        if (deleteId != null) {
            Company company = companyService.findById(Integer.parseInt(deleteId));
            companyService.delete(company);
            resp.sendRedirect("/companies");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (companyId != null) {
            handleId(Integer.parseInt(companyId), req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                String removeId = req.getParameter("removeId");
                if (removeId != null) {
                    Developer developer = developerService.findById(Integer.parseInt(removeId));
                    Company company = companyService.findByName("Unemployed");
                    developer.setCompanyId(company.getCompanyId());
                    developerService.update(developer);
                }
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/companies");
            }
        } else {
            List<Company> companies = companyService.findAllExUnemployed();
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/WEB-INF/jsp/companies.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("company", new Company());
        List<Developer> unemployedDevelopers = developerService.findAllUnemployed();
        req.setAttribute("unemployedDevelopers", unemployedDevelopers);
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Company company = companyService.findById(id);
        req.setAttribute("company", company);
        List<Developer> developers = developerService.findByCompanyId(company.getCompanyId());
        req.setAttribute("developers", developers);
        List<Developer> unemployedDevelopers = developerService.findAllUnemployed();
        req.setAttribute("unemployedDevelopers", unemployedDevelopers);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);

    }
}
