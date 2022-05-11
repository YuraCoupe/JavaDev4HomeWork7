package ua.goit.projectmanagementsystem.service;

import ua.goit.projectmanagementsystem.exception.CompanyNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;
import ua.goit.projectmanagementsystem.Dao.DeveloperProjectDao;


public class DeveloperProjectService {
    private static DeveloperProjectService instance;

    private static final DeveloperProjectDao developerProjectDAO = DeveloperProjectDao.getInstance();

    public DeveloperProjectService() {
    }

    public static DeveloperProjectService getInstance() {
        if (instance == null) {
            instance = new DeveloperProjectService();
        }
        return instance;
    }

    public Integer save(DeveloperProject developerProject) {
        return developerProjectDAO.create(developerProject).getId();
    }

    public DeveloperProject findByIds(Integer developerId, Integer projectId) {
        return developerProjectDAO.findByIds(developerId, projectId).orElseThrow(()
                -> new CompanyNotFoundException("Entity does not exist"));
    }

    public void delete(DeveloperProject developerProject) {
        if (!developerProjectDAO.findByIds(developerProject.getDeveloperId(), developerProject.getProjectId()).isEmpty()) {
            developerProjectDAO.delete(developerProject);
        } else {
            throw new CompanyNotFoundException("This entity doesn't exist");
        }
    }
}
