package ua.goit.projectmanagementsystem.webapp.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.projectmanagementsystem.model.ErrorMessage;
import ua.goit.projectmanagementsystem.model.domain.Company;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.service.*;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@WebServlet(urlPatterns = "/projects/*")
public class ProjectsServlet extends HttpServlet {

    private ProjectService projectService;
    private CompanyService companyService;
    private DeveloperService developerService;
    private Validator validator;


    @Override
    public void init() {
        this.projectService = (ProjectService) getServletContext().getAttribute("projectService");
        this.companyService = (CompanyService) getServletContext().getAttribute("companyService");
        this.developerService = (DeveloperService) getServletContext().getAttribute("developerService");
        validator = (Validator) getServletContext().getAttribute("validator");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer projectId = null;
        Project project = new Project();

        if (!req.getParameter("projectId").isBlank()) {
            projectId = Integer.parseInt(req.getParameter("projectId"));
            project = projectService.findById(projectId);
        }

        ErrorMessage errorMessage = validator.validateProject(req);
        if (!errorMessage.getErrors().isEmpty()) {
            req.setAttribute("errorMessage", errorMessage);
            if (Objects.nonNull(projectId)) {
                handleId(projectId, req, resp);
            } else {
                handleNew(req, resp);
            }
            return;
        }

        String projectName = req.getParameter("projectName");
        String customerId = req.getParameter("customerId");
        String companyId = req.getParameter("companyId");
        String projectCost = req.getParameter("projectCost");

        project.setProjectName(projectName);
        project.setCustomerId(Integer.parseInt(customerId));
        Company company = companyService.findById(Integer.parseInt(companyId));
        project.setCompany(company);
        project.setProjectCost(Integer.parseInt(projectCost));

        Integer developerId;
        if (Objects.nonNull(req.getParameter("developerId"))) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            Developer developer = developerService.findById(developerId);
            project.getDevelopers().add(developer);
        }

        if (Objects.isNull(projectId)) {
            projectService.save(project);
        } else {
            projectService.update(project);
        }
        resp.sendRedirect("/projects");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/projects/?", "");
        String deleteId = req.getParameter("deleteId");
        String projectId = req.getParameter("projectId");
        if (deleteId != null) {
            Project project = projectService.findById(Integer.parseInt(deleteId));
            projectService.delete(project);
            resp.sendRedirect("/projects");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (projectId != null) {
            handleId(Integer.parseInt(projectId), req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                String removeId = req.getParameter("removeId");
                if (removeId != null) {
                    Project project = projectService.findById(id);
                    project.getDevelopers().remove(developerService.findById(Integer.parseInt(removeId)));
                    projectService.update(project);
                }
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/projects");
            }
        } else {
            List<Project> projects = projectService.findAll();
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/WEB-INF/jsp/projects.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("project", new Project());
        List<Company> companies = companyService.findAllExUnemployed();
        req.setAttribute("companies", companies);
        List<Developer> developersWithoutThisProject = developerService.findAll();
        req.setAttribute("developersWithoutThisProject", developersWithoutThisProject);
        req.getRequestDispatcher("/WEB-INF/jsp/project.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = projectService.findById(id);
        req.setAttribute("project", project);

        List<Company> companies = companyService.findAllExUnemployed();
        req.setAttribute("companies", companies);

        Set<Developer> developers = project.getDevelopers();
        req.setAttribute("developers", developers);

        List<Developer> developersWithoutThisProject = developerService.findWithoutThisProjectId(project.getProjectId());
        req.setAttribute("developersWithoutThisProject", developersWithoutThisProject);

        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/project.jsp").forward(req, resp);

    }
}
