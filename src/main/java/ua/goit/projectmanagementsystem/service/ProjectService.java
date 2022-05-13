package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.exception.ProjectAlreadyExistException;
import ua.goit.projectmanagementsystem.exception.ProjectNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.Dao.CompanyDao;
import ua.goit.projectmanagementsystem.Dao.ProjectDao;

import java.util.List;

public class ProjectService {

    private static ProjectService instance;
    private static final ProjectDao projectDao = ProjectDao.getInstance();
    private static final CompanyDao companyDao = CompanyDao.getInstance();

    public ProjectService() {
    }

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    public Integer save(Project project) {
        Integer id = null;
        if (projectDao.findByName(project.getProjectName()).isEmpty()) {
            id = projectDao.create(project).getProjectId();
        } else {
            throw new ProjectAlreadyExistException("This project already exists");
        }
        return id;
    }

    public void delete(Project project) {
        if (!projectDao.findById(project.getProjectId()).isEmpty()) {
            projectDao.delete(project);
        } else {
            throw new ProjectNotFoundException("This project doesn't exist");
        }
    }

    public void update(Project project) {
        projectDao.update(project);
    }

    public List<Project> findAll() {
        return projectDao.findAll();
    }

    public Project findById(Integer id) {
        return projectDao.findById(id).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %d does not exist", id)));
    }

    public Project findByName(String name) {
        return projectDao.findByName(name).orElseThrow(()
                -> new CompanyNotFoundException(String.format("Project %s does not exist", name)));
    }

    public List<Project> findWithoutThisDeveloperId(Integer developerId) {
        return projectDao.findWithoutThisDeveloperId(developerId);
    }
}
