package ua.goit.projectmanagementsystem.webapp.listeners;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ua.goit.projectmanagementsystem.service.*;

@WebListener
public class LoadListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("companyService", CompanyService.getInstance());
        servletContext.setAttribute("developerService", DeveloperService.getInstance());
        servletContext.setAttribute("projectService", ProjectService.getInstance());
        servletContext.setAttribute("validator", Validator.getInstance());

    }
}