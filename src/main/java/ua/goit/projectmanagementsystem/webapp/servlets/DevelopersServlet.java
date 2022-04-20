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
import ua.goit.projectmanagementsystem.model.converter.*;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.model.dto.DeveloperDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.DeveloperService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@WebServlet(urlPatterns = "/developers/*")
public class DevelopersServlet extends HttpServlet {
    private DeveloperService developerService;
    private CompanyService companyService;
    private static Gson jsonParser = new Gson();


    @Override
    public void init() {
        PropertiesUtil util = new PropertiesUtil(getServletContext());

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());

        DeveloperRepository developerRepository = new DeveloperRepository(dbConnector);
        DeveloperShortConverter developerShortConverter = new DeveloperShortConverter();
        SkillConverter skillConverter = new SkillConverter();
        DeveloperConverter developerConverter = new DeveloperConverter(skillConverter);
        ProjectConverter projectConverter = new ProjectConverter();
        DeveloperProjectConverter developerProjectConverter = new DeveloperProjectConverter(developerConverter, projectConverter);

        this.developerService = new DeveloperService(developerRepository, developerShortConverter, developerConverter, developerProjectConverter);

        CompanyRepository companyRepository = new CompanyRepository(dbConnector);
        CompanyConverter companyConverter = new CompanyConverter();
        this.companyService = new CompanyService(companyRepository, companyConverter);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<DeveloperDto> modelFromStream = Optional.empty();
        try (InputStream inputStream = req.getInputStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            String jsonStr = scanner.nextLine();
            modelFromStream = Optional.of(jsonParser.fromJson(jsonStr, DeveloperDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelFromStream.ifPresent(developerDto -> developerService.save(developerDto));
        resp.sendRedirect("/developers");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<DeveloperDto> modelFromStream = Optional.empty();
        try (InputStream inputStream = req.getInputStream();
             Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
            String jsonStr = scanner.nextLine();
            modelFromStream = Optional.of(jsonParser.fromJson(jsonStr, DeveloperDto.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        modelFromStream.ifPresent(developerDto -> developerService.update(developerDto));
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
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DeveloperDto developer = developerService.findById(id);
        req.setAttribute("developer", developer);
        List<CompanyDto> companies = companyService.findAll();
        req.setAttribute("companies", companies);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/developer.jsp").forward(req, resp);

    }
}
