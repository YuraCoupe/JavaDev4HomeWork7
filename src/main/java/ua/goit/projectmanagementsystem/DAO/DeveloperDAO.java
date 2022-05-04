package ua.goit.projectmanagementsystem.DAO;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.domain.Developer;
import ua.goit.projectmanagementsystem.model.domain.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DeveloperDAO {
    private static final String INSERT = "INSERT INTO developers (first_name, last_name, age, sex, company_id, salary ) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE developers SET first_name = ?, last_name = ?, age = ?, sex = ?, company_id = ?, salary = ? WHERE developer_id = ?;";
    private static final String FIND_BY_NAME = "SELECT * FROM developers WHERE first_name = ? AND last_name = ?;";
    private static final String FIND_ALL = "SELECT * FROM developers ORDER BY first_name;";
    private static final String DELETE = "DELETE FROM developers WHERE developer_id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM developers WHERE developer_id = ?;";
    private static final String FIND_BY_COMPANY_ID = "SELECT * FROM developers WHERE company_id = ?;";
    private static final String FIND_BY_PROJECT_ID =
            "SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary FROM developers d \n" +
                    "JOIN developerstoprojects dtp ON d.developer_id = dtp.developer_id\n" +
                    "WHERE dtp.project_id = ?;";
    private static final String FIND_DEVELOPERS_EXCLUDING_CURRENT_PROJECT =
            "SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary FROM developers d \n" +
                    "EXCEPT SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary FROM developers d \n" +
                    "JOIN developerstoprojects dtp ON d.developer_id = dtp.developer_id\n" +
                    "WHERE dtp.project_id = ?;";
    private static final String FIND_ALL_UNEMPLOYED =
            "SELECT * FROM developers d\n" +
                    "JOIN companies c ON d.company_id = c.company_id\n" +
                    "WHERE c.company_name = 'Unemployed';";

    private final DatabaseManager databaseManager;

    public DeveloperDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Developer> findByName(String firstName, String lastName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloper(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public void save(Developer developer) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getAge());
            preparedStatement.setString(4, developer.getSex());
            preparedStatement.setInt(5, developer.getCompanyId());
            preparedStatement.setInt(6, developer.getSalary());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(Developer developer) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, developer.getFirstName());
            preparedStatement.setString(2, developer.getLastName());
            preparedStatement.setInt(3, developer.getAge());
            preparedStatement.setString(4, developer.getSex());
            preparedStatement.setInt(5, developer.getCompanyId());
            preparedStatement.setInt(6, developer.getSalary());
            preparedStatement.setInt(7, developer.getDeveloperId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Developer developer) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, developer.getDeveloperId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Optional<Developer> mapToDeveloper(ResultSet resultSet) throws SQLException {
        SkillDAO skillDAO = new SkillDAO(databaseManager);
        Developer developer = null;
        while (resultSet.next()) {
            developer = new Developer();
            developer.setDeveloperId(resultSet.getInt("developer_id"));
            developer.setFirstName(resultSet.getString("first_name"));
            developer.setLastName(resultSet.getString("last_name"));
            developer.setAge(resultSet.getInt("age"));
            developer.setSex(resultSet.getString("sex"));
            developer.setCompanyId(resultSet.getInt("company_id"));
            developer.setSalary(resultSet.getInt("salary"));
            Developer finalDeveloper = developer;
            Set<Skill> skills = skillDAO.getSkillsByDeveloperId(developer.getDeveloperId()).orElseThrow(()
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", finalDeveloper.getDeveloperId())));
            developer.setSkills(skills);
        }
        return Optional.ofNullable(developer);
    }

    private List<Developer> mapToDevelopers(ResultSet resultSet) throws SQLException {
        SkillDAO skillDAO = new SkillDAO(databaseManager);
        List<Developer> developers = new ArrayList<>();
        while (resultSet.next()) {
            Developer developer = new Developer();
            developer.setDeveloperId(resultSet.getInt("developer_id"));
            developer.setFirstName(resultSet.getString("first_name"));
            developer.setLastName(resultSet.getString("last_name"));
            developer.setAge(resultSet.getInt("age"));
            developer.setSex(resultSet.getString("sex"));
            developer.setCompanyId(resultSet.getInt("company_id"));
            developer.setSalary(resultSet.getInt("salary"));
            Set<Skill> skills = skillDAO.getSkillsByDeveloperId(developer.getDeveloperId()).orElseThrow(()
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", developer.getDeveloperId())));
            developer.setSkills(skills);
            developers.add(developer);
        }
        return developers;
    }

    public Optional<List<Developer>> findAll() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return Optional.ofNullable(mapToDevelopers(resultSet));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Developer> findById(Integer id) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloper(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Developer> findByCompanyId(Integer companyId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COMPANY_ID)) {
            preparedStatement.setLong(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDevelopers(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Developer> findByProjectId(Integer projectId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PROJECT_ID)) {
            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDevelopers(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Developer> findWithoutThisProjectId(Integer projectId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_DEVELOPERS_EXCLUDING_CURRENT_PROJECT)) {
            preparedStatement.setLong(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDevelopers(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public List<Developer> findAllUnemployed() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_UNEMPLOYED)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDevelopers(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}

