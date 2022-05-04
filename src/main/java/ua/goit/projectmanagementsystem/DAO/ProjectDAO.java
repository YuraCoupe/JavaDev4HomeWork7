package ua.goit.projectmanagementsystem.DAO;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.Project;
import ua.goit.projectmanagementsystem.model.domain.Skill;

import java.sql.*;
import java.util.*;

public class ProjectDAO {

    private static final String INSERT = "INSERT INTO projects (project_name, customer_id, company_id, project_cost) VALUES (?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE projects SET project_name = ?, customer_id = ?, company_id = ?, project_cost = ? WHERE project_id = ?;";
    private static final String DELETE = "DELETE FROM projects WHERE projects.project_id = ?;";
    private static final String FIND_ALL = "SELECT * FROM projects ORDER BY project_name;";
    private static final String FIND_BY_ID = "SELECT * FROM projects WHERE projects.project_id = ?;";
    private static final String FIND_BY_NAME = "SELECT * FROM projects WHERE projects.project_name = ?;";

    private static final String FIND_PROJECTS_BY_DEVELOPER_ID =
            "SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "JOIN developerstoprojects dtp ON p.project_id = dtp.project_id\n" +
                    "WHERE dtp.developer_id = ?;";

    private static final String FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER =
            "SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "EXCEPT SELECT p.project_id, p.project_name, p.customer_id, p.company_id, p.project_cost FROM projects p \n" +
                    "JOIN developerstoprojects dtp ON p.project_id = dtp.project_id\n" +
                    "WHERE dtp.developer_id = ?;";

    private final static String GET_SALARY_SUM_BY_PROJECT_ID =
            "SELECT pr.project_id, pr.project_name, SUM(d.salary) as project_value\n" +
            "FROM projects pr\n" +
            "JOIN developersToProjects dtp ON dtp.project_id = pr.project_id \n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id \n" +
            "WHERE pr.project_id = ?\n" +
            "GROUP BY pr.project_id, pr.project_name";

    private final static String FIND_DEVELOPERS_BY_PROJECT_ID =
            "SELECT pr.project_id, pr.project_name, d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary\n" +
            "FROM projects pr\n" +
            "JOIN developersToProjects dtp ON dtp.project_id = pr.project_id \n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id \n" +
            "WHERE pr.project_id = ?";

    private final static String FIND_ALL_JOIN =
            "SELECT dtp.project_id, p.project_name, p.company_id, p.customer_id, p.project_cost, COUNT(d.developer_id) as developers_number\n" +
            "FROM developerstoprojects dtp\n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id\n" +
            "JOIN projects p ON dtp.project_id = p.project_id\n" +
            "GROUP BY dtp.project_id, p.project_name, p.company_id, p.customer_id, p.project_cost";

    private final DatabaseManager databaseManager;

    public ProjectDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Set<Developer>> findDevsByProjectId(Integer projectId) {
        SkillDAO skillRepository = new SkillDAO(databaseManager);
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEVELOPERS_BY_PROJECT_ID)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Developer> developers = new HashSet<>();
            while (resultSet.next()) {
                Developer developer = new Developer();
                developer.setDeveloperId(resultSet.getInt("developer_id"));
                developer.setFirstName(resultSet.getString("first_name"));
                developer.setLastName(resultSet.getString("last_name"));
                developer.setAge(Integer.parseInt(resultSet.getString("age")));
                developer.setSex(resultSet.getString("sex"));
                developer.setCompanyId(Integer.parseInt(resultSet.getString("company_id")));
                developer.setSalary(Integer.parseInt(resultSet.getString("salary")));
                Set<Skill> skills = skillRepository.getSkillsByDeveloperId(developer.getDeveloperId()).orElseThrow(()
                        -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", developer.getDeveloperId())));;
                developer.setSkills(skills);
                developers.add(developer);
            }
            return Optional.ofNullable(developers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Integer save(Project project) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setInt(2, project.getCustomerId());
            preparedStatement.setInt(3, project.getCompanyId());
            preparedStatement.setInt(4, project.getProjectCost());
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

    public void update(Project project) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, project.getProjectName());
            preparedStatement.setInt(2, project.getCustomerId());
            preparedStatement.setInt(3, project.getCompanyId());
            preparedStatement.setInt(4, project.getProjectCost());
            preparedStatement.setInt(5, project.getProjectId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Project project) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, project.getProjectId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public Optional<List<Project>> findAll() {
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = readObjects(resultSet);
            return Optional.ofNullable(projects);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Project> findById(Integer id) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Project> findByName(String name) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObject(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Project> findWithoutThisDeveloperId(Integer developerId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PROJECTS_EXCLUDING_CURRENT_DEVELOPER)) {
            preparedStatement.setLong(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Project> findByDeveloperId(Integer developerId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_PROJECTS_BY_DEVELOPER_ID)) {
            preparedStatement.setLong(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return readObjects(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    private Optional<Project> readObject(ResultSet resultSet) throws SQLException {
        Project project = null;
        while (resultSet.next()) {
            project = new Project();
            project.setProjectId(resultSet.getInt("project_id"));
            project.setProjectName(resultSet.getString("project_name"));
            project.setCompanyId(resultSet.getInt("company_id"));
            project.setCustomerId(resultSet.getInt("customer_id"));
            project.setProjectCost(resultSet.getInt("project_cost"));
        }
        return Optional.ofNullable(project);
    }

    private List<Project> readObjects(ResultSet resultSet) throws SQLException {
        List<Project> projects = new ArrayList<>();
        while (resultSet.next()) {
            Project project = new Project();
            project.setProjectId(resultSet.getInt("project_id"));
            project.setProjectName(resultSet.getString("project_name"));
            project.setCompanyId(resultSet.getInt("company_id"));
            project.setCustomerId(resultSet.getInt("customer_id"));
            project.setProjectCost(resultSet.getInt("project_cost"));
            projects.add(project);
        }
        return projects;
    }
}
