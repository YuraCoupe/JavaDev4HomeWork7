package ua.goit.projectmanagementsystem.webapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.projectmanagementsystem.model.ErrorMessage;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.service.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@WebServlet(urlPatterns = "/developers/*")
public class DevelopersServlet extends HttpServlet {
    private DeveloperService developerService;
    private CompanyService companyService;
    private ProjectService projectService;
    private DeveloperProjectService developerProjectService;
    private Validator validator;

    @Override
    public void init() {

        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
        this.companyService = (CompanyService) getServletContext().getAttribute("companyService");
        this.projectService = (ProjectService) getServletContext().getAttribute("projectService");
        this.developerProjectService = (DeveloperProjectService) getServletContext().getAttribute("developerProjectService");
        this.validator = (Validator) getServletContext().getAttribute("validator");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer developerId = null;
        Developer developer = new Developer();
        if (!req.getParameter("developerId").isBlank()) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            developer.setDeveloperId(developerId);
        }

        ErrorMessage errorMessage = validator.validateDeveloper(req);
        if (!errorMessage.getErrors().isEmpty()) {
            req.setAttribute("errorMessage", errorMessage);
            if (Objects.nonNull(developerId)) {
                handleId(developerId, req, resp);
            } else {
                handleNew(req, resp);
            }
            return;
        }

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        Integer age = Integer.parseInt(req.getParameter("age"));
        String sex = req.getParameter("sex");
        Integer companyId = Integer.parseInt(req.getParameter("companyId"));
        Integer salary = Integer.parseInt(req.getParameter("salary"));
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setAge(age);
        developer.setSex(sex);
        Company company = companyService.findById(companyId);
        developer.setCompany(company);
        developer.setSalary(salary);

        if (Objects.isNull(developerId)) {
            developerService.save(developer);
        } else {
            developerService.update(developer);
        }
        resp.sendRedirect("/developers");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/developers/?", "");
        String deleteId = req.getParameter("deleteId");
        String developerId = req.getParameter("developerId");

        if (deleteId != null) {
            Developer developer = developerService.findById(Integer.parseInt(deleteId));
            developerService.delete(developer);
            resp.sendRedirect("/developers");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (developerId != null) {
            handleId(Integer.parseInt(developerId), req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                String removeId = req.getParameter("removeId");
                if (removeId != null) {
                    DeveloperProject developerProject = developerProjectService.findByIds(id, Integer.parseInt(removeId));
                    developerProjectService.delete(developerProject);
                }
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/developers");
            }
        } else {
            List<Developer> developers = developerService.findAll();
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/WEB-INF/jsp/developers.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("developer", new Developer());
        List<Company> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        List<Project> projectsWithoutThisDeveloper = projectService.findAll();
        req.setAttribute("projectsWithoutThisDeveloper", projectsWithoutThisDeveloper);
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        Developer developer = developerService.findById(id);
        req.setAttribute("developer", developer);
        List<Company> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        Set<Project> projects = developer.getProjects();
        req.setAttribute("projects", projects);
        List<Project> projectsWithoutThisDeveloper = projectService.findWithoutThisDeveloperId(id);
        req.setAttribute("projectsWithoutThisDeveloper", projectsWithoutThisDeveloper);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);

    }
}
