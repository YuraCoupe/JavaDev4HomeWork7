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
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
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
        this.companyService = new CompanyService(new CompanyRepository(dbConnector), new CompanyConverter());
        this.developerService = new DeveloperService(new DeveloperRepository(dbConnector), new CompanyRepository(dbConnector), new DeveloperShortConverter(), new DeveloperConverter(new SkillConverter(), new CompanyConverter()), new DeveloperProjectConverter(new DeveloperConverter(new SkillConverter(), new CompanyConverter()), new ProjectConverter()));
        validator = new Validator();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer companyId = null;
        CompanyDto companyDto = new CompanyDto();

        if (!req.getParameter("companyId").isBlank()) {
            companyId = Integer.parseInt(req.getParameter("companyId"));
            companyDto.setCompanyId(companyId);
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

        companyDto.setCompanyName(companyName);
        companyDto.setCompanyLocation(companyLocation);

        if (Objects.isNull(companyId)) {
            companyId = companyService.save(companyDto);
        } else {
            companyService.update(companyDto);
        }

        Integer developerId;
        DeveloperDto developerDto;
        if (Objects.nonNull(req.getParameter("developerId"))) {
            developerId = Integer.parseInt(req.getParameter("developerId"));
            developerDto = developerService.findById(developerId);
            companyDto = companyService.finbById(companyId);
            developerDto.setCompany(companyDto);
            developerService.update(developerDto);
        }
        resp.sendRedirect("/companies");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/companies/?", "");
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            CompanyDto companyDto = companyService.finbById(Integer.parseInt(deleteId));
            companyService.delete(companyDto);
            resp.sendRedirect("/companies");
        } else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                String removeId = req.getParameter("removeId");
                if (removeId != null) {
                    DeveloperDto developerDto = developerService.findById(Integer.parseInt(removeId));
                    CompanyDto companyDto = companyService.findByName("Unemployed");
                    developerDto.setCompany(companyDto);
                    developerService.update(developerDto);
                }
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/companies");
            }
        } else {
            List<CompanyDto> companies = companyService.findAllExUnemployed();
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/WEB-INF/jsp/companies.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("company", new CompanyDto());
        //List<CompanyDto> all = service.findAll();
        //req.setAttribute("companies", all);
        List<DeveloperDto> unemployedDevelopers = developerService.findAllUnemployed();
        req.setAttribute("unemployedDevelopers", unemployedDevelopers);
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto company = companyService.finbById(id);
        req.setAttribute("company", company);
        List<DeveloperDto> developers = developerService.findByCompanyId(company.getCompanyId());
        req.setAttribute("developers", developers);
        List<DeveloperDto> unemployedDevelopers = developerService.findAllUnemployed();
        req.setAttribute("unemployedDevelopers", unemployedDevelopers);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);

    }
}
