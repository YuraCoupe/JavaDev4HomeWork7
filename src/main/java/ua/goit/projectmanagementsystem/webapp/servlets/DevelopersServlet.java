package ua.goit.projectmanagementsystem.webapp.servlets;

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
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperProjectDAO;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;
import ua.goit.projectmanagementsystem.service.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(urlPatterns = "/developers/*")
public class DevelopersServlet extends HttpServlet {
    private DeveloperService developerService;
    private CompanyService companyService;
    private ProjectService projectService;
    private DeveloperProjectService developerProjectService;
    private Validator validator;

    @Override
    public void init() {
        PropertiesUtil util = new PropertiesUtil(getServletContext());

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());

        DeveloperRepository developerRepository = new DeveloperRepository(dbConnector);
        CompanyRepository companyRepository = new CompanyRepository(dbConnector);
        SkillConverter skillConverter = new SkillConverter();
        CompanyConverter companyConverter = new CompanyConverter();
        ProjectConverter projectConverter = new ProjectConverter();
        DeveloperShortConverter developerShortConverter = new DeveloperShortConverter();
        DeveloperConverter developerConverter = new DeveloperConverter(skillConverter, companyConverter);
        DeveloperProjectConverter developerProjectConverter = new DeveloperProjectConverter(developerConverter, projectConverter);

        this.developerService = new DeveloperService(developerRepository, companyRepository, developerShortConverter, developerConverter, developerProjectConverter);
        this.companyService = new CompanyService(companyRepository, companyConverter);
        this.projectService = new ProjectService(new ProjectRepository(dbConnector), companyRepository, companyConverter);
        this.developerProjectService = new DeveloperProjectService(new DeveloperProjectDAO(dbConnector));

        this.validator = new Validator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer developerId = null;
        DeveloperDto developerDto = new DeveloperDto();
        if (!req.getParameter("developerId").isBlank()) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            developerDto.setDeveloperId(developerId);
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

        CompanyDto companyDto = companyService.finbById(companyId);

        developerDto.setFirstName(firstName);
        developerDto.setLastName(lastName);
        developerDto.setAge(age);
        developerDto.setSex(sex);
        developerDto.setCompany(companyDto);
        developerDto.setSalary(salary);

        if (Objects.isNull(developerId)) {
            developerService.save(developerDto);
        } else {
            developerService.update(developerDto);
        }
        resp.sendRedirect("/developers");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/developers/?", "");
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            DeveloperDto developerDto = developerService.findById(Integer.parseInt(deleteId));
            developerService.delete(developerDto);
            resp.sendRedirect("/developers");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
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
            List<DeveloperDto> developers = developerService.findAll();
            req.setAttribute("developers", developers);
            req.getRequestDispatcher("/WEB-INF/jsp/developers.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("developer", new DeveloperDto());
        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        List<Project> projectsWithoutThisDeveloper = projectService.findAll();
        req.setAttribute("projectsWithoutThisDeveloper", projectsWithoutThisDeveloper);
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws
            ServletException, IOException {
        DeveloperDto developer = developerService.findById(id);
        req.setAttribute("developer", developer);
        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        List<Project> projects = projectService.findByDeveloperId(developer.getDeveloperId());
        req.setAttribute("projects", projects);
        List<Project> projectsWithoutThisDeveloper = projectService.findWithoutThisDeveloperId(id);
        req.setAttribute("projectsWithoutThisDeveloper", projectsWithoutThisDeveloper);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);

    }
}
