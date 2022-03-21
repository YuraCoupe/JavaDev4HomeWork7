package ua.goit.projectmanagementsystem;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.config.PostgresHikariProvider;
import ua.goit.projectmanagementsystem.config.PropertiesUtil;
import ua.goit.projectmanagementsystem.controller.PMSController;
import ua.goit.projectmanagementsystem.model.converter.*;
import ua.goit.projectmanagementsystem.repository.CompanyRepository;
import ua.goit.projectmanagementsystem.repository.DeveloperRepository;
import ua.goit.projectmanagementsystem.repository.ProjectRepository;
import ua.goit.projectmanagementsystem.service.CompanyService;
import ua.goit.projectmanagementsystem.service.DeveloperService;
import ua.goit.projectmanagementsystem.service.ProjectService;
import ua.goit.projectmanagementsystem.view.Console;
import ua.goit.projectmanagementsystem.view.View;

import javax.crypto.CipherInputStream;

public class Application {
    public static void main(String[] args) {
        PropertiesUtil util = new PropertiesUtil();

        DatabaseManager dbConnector = new PostgresHikariProvider(util.getHostname(), util.getPort(),
                util.getSchema(), util.getUser(), util.getPassword());

        ProjectRepository projectRepository = new ProjectRepository(dbConnector);
        DeveloperRepository developerRepository = new DeveloperRepository(dbConnector);
        CompanyRepository companyRepository = new CompanyRepository(dbConnector);

        SkillConverter skillConverter = new SkillConverter();
        DeveloperConverter developerConverter = new DeveloperConverter(skillConverter);
        ProjectConverter projectConverter = new ProjectConverter();
        DeveloperProjectConverter developerProjectConverter = new DeveloperProjectConverter(developerConverter, projectConverter);
        CompanyConverter companyConverter = new CompanyConverter();

        ProjectService projectService = new ProjectService(projectRepository, developerConverter, projectConverter, developerProjectConverter);
        DeveloperService developerService = new DeveloperService(developerRepository, developerConverter, developerProjectConverter);
        CompanyService companyService = new CompanyService(companyRepository, companyConverter);

        View view = new Console();

        PMSController pmsController = new PMSController(view, projectService, developerService, companyService);
        pmsController.run();
    }
}
