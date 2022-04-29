package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.domain.DeveloperProject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeveloperProjectDAO {
    private static final String INSERT = "INSERT INTO developerstoprojects (developer_id, project_id) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE projectstodevelopers SET developer_id = ?, project_id = ? WHERE id = ?;";
    private static final String DELETE = "DELETE FROM developerstoprojects WHERE id = ?;";
    private static final String FIND_ALL = "SELECT * FROM developerstoprojects;";
    private static final String FIND_BY_IDS = "SELECT * FROM developerstoprojects WHERE developer_id = ? AND project_id = ?;";
    private static final String FIND_BY_DEVELOPER_ID = "SELECT * FROM developerstoprojects WHERE developer_id = ?;";
    private static final String FIND_BY_PROJECT_ID = "SELECT * FROM developerstoprojects WHERE project_id = ?;";

    private final DatabaseManager databaseManager;

    public DeveloperProjectDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Integer save(Integer developerId, Integer projectId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, projectId);
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            Integer id = resultSet.getInt(1);
            return id;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void delete(DeveloperProject developerProject) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, developerProject.getId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Optional<DeveloperProject> readObject(ResultSet resultSet) throws SQLException {
        DeveloperProject developerProject = null;
        while (resultSet.next()) {
            developerProject = new DeveloperProject();
            developerProject.setId(resultSet.getInt("id"));
            developerProject.setDeveloperId(resultSet.getInt("developer_id"));
            developerProject.setProjectId(resultSet.getInt("project_id"));
        }
        return Optional.ofNullable(developerProject);
    }

    private List<DeveloperProject> readObjects(ResultSet resultSet) throws SQLException {
        List<DeveloperProject> developerProjects= new ArrayList<>();
        while (resultSet.next()) {
            DeveloperProject developerProject = new DeveloperProject();
            developerProject.setId(resultSet.getInt("id"));
            developerProject.setDeveloperId(resultSet.getInt("developer_id"));
            developerProject.setProjectId(resultSet.getInt("project_id"));
            developerProjects.add(developerProject);
        }
        return developerProjects;
    }

    public List<DeveloperProject> findAll() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<DeveloperProject> findByIds(Integer developerId, Integer projectId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_IDS)) {
            preparedStatement.setInt(1, developerId);
            preparedStatement.setInt(2, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<DeveloperProject> findByDeveloperId(Integer developerId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_DEVELOPER_ID)) {
            preparedStatement.setInt(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<DeveloperProject> findByProjectId(Integer projectId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PROJECT_ID)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}
