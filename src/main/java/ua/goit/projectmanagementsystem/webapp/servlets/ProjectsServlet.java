package ua.goit.projectmanagementsystem.webapp.servlets;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.config.PostgresHikariProvider;
import ua.goit.projectmanagementsystem.config.PropertiesUtil;
import ua.goit.projectmanagementsystem.model.ErrorMessage;
import ua.goit.projectmanagementsystem.model.converter.*;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.model.dto.ProjectWithCompanyDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperProjectDAO;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;
import ua.goit.projectmanagementsystem.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = "/projects/*")
public class ProjectsServlet extends HttpServlet {
    private ProjectService projectService;
    private DeveloperService developerService;
    private DeveloperProjectService developerProjectService;
    private CompanyService companyService;
    private static Gson jsonParser = new Gson();
    private Validator validator;


    @Override
    public void init() {
        PropertiesUtil util = new PropertiesUtil(getServletContext());

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());
        this.projectService = new ProjectService(new ProjectRepository(dbConnector), new CompanyRepository(dbConnector), new CompanyConverter());
        this.companyService = new CompanyService(new CompanyRepository(dbConnector), new CompanyConverter());
        this.developerService = new DeveloperService(new DeveloperRepository(dbConnector), new CompanyRepository(dbConnector), new DeveloperShortConverter(), new DeveloperConverter(new SkillConverter(), new CompanyConverter()), new DeveloperProjectConverter(new DeveloperConverter(new SkillConverter(), new CompanyConverter()), new ProjectConverter()));
        this.developerProjectService = new DeveloperProjectService(new DeveloperProjectDAO(dbConnector));
        validator = new Validator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer projectId = null;
        Project project = new Project();

        if (!req.getParameter("projectId").isBlank()) {
            projectId = Integer.parseInt(req.getParameter("projectId"));
            project.setProjectId(projectId);
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
        project.setCompanyId(Integer.parseInt(companyId));
        project.setProjectCost(Integer.parseInt(projectCost));

        if (Objects.isNull(projectId)) {
            projectId = projectService.save(project);
        } else {
            projectService.update(project);
        }

        Integer developerId;
        if (Objects.nonNull(req.getParameter("developerId"))) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            developerProjectService.save(developerId, projectId);
        }
        resp.sendRedirect("/projects");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/projects/?", "");
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            Project project = projectService.findById(Integer.parseInt(deleteId));
            projectService.delete(project);
            resp.sendRedirect("/projects");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                String removeId = req.getParameter("removeId");
                if (removeId != null) {
                    DeveloperProject developerProject = developerProjectService.findByIds(Integer.parseInt(removeId), id);
                    developerProjectService.delete(developerProject);
                }
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/projects");
            }
        } else {
            List<ProjectWithCompanyDto> projects = projectService.findAllWithCompany();
            req.setAttribute("projects", projects);
            req.getRequestDispatcher("/WEB-INF/jsp/projects.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("project", new Project());
        //List<CompanyDto> all = service.findAll();
        //req.setAttribute("companies", all);
        List<CompanyDto> companies = companyService.findAllExUnemployed();
        req.setAttribute("companies", companies);
        List<DeveloperDto> developersWithoutThisProject = developerService.findAll();
        req.setAttribute("developersWithoutThisProject", developersWithoutThisProject);
        req.getRequestDispatcher("/WEB-INF/jsp/project.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Project project = projectService.findById(id);
        req.setAttribute("project", project);

        List<CompanyDto> companies = companyService.findAllExUnemployed();
        req.setAttribute("companies", companies);

        List<DeveloperDto> developers = developerService.findByProjectId(project.getProjectId());
        req.setAttribute("developers", developers);

        List<DeveloperDto> developersWithoutThisProject = developerService.findWithoutThisProjectId(project.getProjectId());
        req.setAttribute("developersWithoutThisProject", developersWithoutThisProject);

        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/project.jsp").forward(req, resp);

    }
}
