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
import ua.goit.projectmanagementsystem.model.converter.CompanyConverter;
import ua.goit.projectmanagementsystem.model.dto.CompanyDto;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.service.CompanyService;

import java.io.IOException;
import java.util.*;

@WebServlet(urlPatterns = "/companies/*")
public class CompaniesServlet extends HttpServlet {
    private CompanyService service;
    private static Gson jsonParser = new Gson();


    @Override
    public void init() {
        PropertiesUtil util = new PropertiesUtil(getServletContext());

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword(), util.getJdbcDriver());
        this.service = new CompanyService(new CompanyRepository(dbConnector), new CompanyConverter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer companyId = null;
        CompanyDto companyDto = new CompanyDto();

        if (!req.getParameter("companyId").isBlank()) {
            companyId = Integer.parseInt(req.getParameter("companyId"));
            companyDto.setCompanyId(companyId);
        }
        String companyName = req.getParameter("companyName");
        String companyLocation = req.getParameter("companyLocation");

        companyDto.setCompanyName(companyName);
        companyDto.setCompanyLocation(companyLocation);

        if (Objects.isNull(companyId)) {
            service.save(companyDto);
        } else {
            service.update(companyDto);
        }

        resp.sendRedirect("/companies");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String idStr = requestURI.replaceAll("/companies/?", "");
        String deleteId = req.getParameter("deleteId");
        if (deleteId != null) {
            CompanyDto companyDto = service.finbById(Integer.parseInt(deleteId));
            service.delete(companyDto);
            resp.sendRedirect("/companies");
        }
        else if ("new".equalsIgnoreCase(idStr)) {
            handleNew(req, resp);
        } else if (!idStr.equals("")) {
            try {
                Integer id = Integer.parseInt(idStr);
                handleId(id, req, resp);
            } catch (RuntimeException e) {
                resp.sendRedirect("/companies");
            }
        } else {
            List<CompanyDto> companies = service.findAll();
            req.setAttribute("companies", companies);
            req.getRequestDispatcher("/WEB-INF/jsp/companies.jsp").forward(req, resp);
        }
    }

    private void handleNew(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("company", new CompanyDto());
        //List<CompanyDto> all = service.findAll();
        //req.setAttribute("companies", all);
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);
    }

    private void handleId(Integer id, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CompanyDto company = service.finbById(id);
        req.setAttribute("company", company);
        req.setCharacterEncoding("UTF-8");
        req.getRequestDispatcher("/WEB-INF/jsp/company.jsp").forward(req, resp);

    }
}
