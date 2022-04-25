package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.SkillDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class DeveloperRepository {
    private static final String INSERT = "INSERT INTO developers (first_name, last_name, age, sex, company_id, salary ) VALUES (?, ?, ?, ?, ?, ?);";
    private static final String UPDATE = "UPDATE developers SET first_name = ?, last_name = ?, age = ?, sex = ?, company_id = ?, salary = ? WHERE developer_id = ?;";
    private static final String FIND_BY_NAME = "SELECT * FROM developers WHERE first_name = ? AND last_name = ?;";
    private static final String FIND_ALL = "SELECT * FROM developers ORDER BY first_name;";
    private static final String DELETE = "DELETE FROM developers WHERE developer_id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM developers WHERE developer_id = ?;";
    private static final String FIND_BY_COMPANY_ID = "SELECT * FROM developers WHERE company_id = ?;";

    private final static String FIND_JAVA_DEVELOPERS =
            "SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary\n" +
                    "FROM developers d\n" +
                    "JOIN developerstoskills dts ON dts.developer_id = d.developer_id\n" +
                    "JOIN skills s ON dts.skill_id = s.skill_id\n" +
                    "WHERE s.specialization = ?";

    private final static String FIND_MIDDLE_DEVELOPERS =
            "SELECT d.developer_id, d.first_name, d.last_name, d.age, d.sex, d.company_id, d.salary\n" +
                    "FROM developers d\n" +
                    "JOIN developerstoskills dts ON dts.developer_id = d.developer_id\n" +
                    "JOIN skills s ON dts.skill_id = s.skill_id\n" +
                    "WHERE s.level = ?";

    private final DatabaseManager databaseManager;

    public DeveloperRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Set<DeveloperDao>> findJavaDevs() {
        SkillRepository skillRepository = new SkillRepository(databaseManager);
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_JAVA_DEVELOPERS)) {
            preparedStatement.setString(1, "Java");
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<DeveloperDao> developers = resultsetToDeveloperDao(skillRepository, resultSet);
            return Optional.ofNullable(developers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<Set<DeveloperDao>> findMiddleDevs() {
        SkillRepository skillRepository = new SkillRepository(databaseManager);
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(FIND_MIDDLE_DEVELOPERS)) {
            preparedStatement.setString(1, "Middle");
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<DeveloperDao> developers = resultsetToDeveloperDao(skillRepository, resultSet);
            return Optional.ofNullable(developers);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<DeveloperDao> findByName(String firstName, String lastName) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloperDao(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    private Set<DeveloperDao> resultsetToDeveloperDao(SkillRepository skillRepository, ResultSet resultSet) throws SQLException {
        Set<DeveloperDao> developers = new HashSet<>();
        while (resultSet.next()) {
            DeveloperDao developerDao = new DeveloperDao();
            developerDao.setDeveloperId(resultSet.getInt("developer_id"));
            developerDao.setFirstname(resultSet.getString("first_name"));
            developerDao.setLastname(resultSet.getString("last_name"));
            developerDao.setAge(Integer.parseInt(resultSet.getString("age")));
            developerDao.setSex(resultSet.getString("sex"));
            developerDao.setCompanyId(Integer.parseInt(resultSet.getString("company_id")));
            developerDao.setSalary(Integer.parseInt(resultSet.getString("salary")));
            Set<SkillDao> skills = skillRepository.getSkillsByDeveloperId(developerDao.getDeveloperId()).orElseThrow(()
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", developerDao.getDeveloperId())));
            ;
            developerDao.setSkills(skills);
            developers.add(developerDao);
        }
        return developers;
    }

    public void save(DeveloperDao developerDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, developerDao.getFirstname());
            preparedStatement.setString(2, developerDao.getLastname());
            preparedStatement.setInt(3, developerDao.getAge());
            preparedStatement.setString(4, developerDao.getSex());
            preparedStatement.setInt(5, developerDao.getCompanyId());
            preparedStatement.setInt(6, developerDao.getSalary());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void update(DeveloperDao developerDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, developerDao.getFirstname());
            preparedStatement.setString(2, developerDao.getLastname());
            preparedStatement.setInt(3, developerDao.getAge());
            preparedStatement.setString(4, developerDao.getSex());
            preparedStatement.setInt(5, developerDao.getCompanyId());
            preparedStatement.setInt(6, developerDao.getSalary());
            preparedStatement.setInt(7, developerDao.getDeveloperId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(DeveloperDao developerDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, developerDao.getDeveloperId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Optional<DeveloperDao> mapToDeveloperDao(ResultSet resultSet) throws SQLException {
        SkillRepository skillRepository = new SkillRepository(databaseManager);
        DeveloperDao developerDao = null;
        while (resultSet.next()) {
            developerDao = new DeveloperDao();
            developerDao.setDeveloperId(resultSet.getInt("developer_id"));
            developerDao.setFirstname(resultSet.getString("first_name"));
            developerDao.setLastname(resultSet.getString("last_name"));
            developerDao.setAge(resultSet.getInt("age"));
            developerDao.setSex(resultSet.getString("sex"));
            developerDao.setCompanyId(resultSet.getInt("company_id"));
            developerDao.setSalary(resultSet.getInt("salary"));
            DeveloperDao finalDeveloperDao = developerDao;
            Set<SkillDao> skills = skillRepository.getSkillsByDeveloperId(developerDao.getDeveloperId()).orElseThrow(()
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", finalDeveloperDao.getDeveloperId())));
            developerDao.setSkills(skills);
        }
        return Optional.ofNullable(developerDao);
    }

    private List<DeveloperDao> mapToDeveloperDaos(ResultSet resultSet) throws SQLException {
        SkillRepository skillRepository = new SkillRepository(databaseManager);
        List<DeveloperDao> developerDaos = new ArrayList<>();
        while (resultSet.next()) {
            DeveloperDao developerDao = new DeveloperDao();
            developerDao.setDeveloperId(resultSet.getInt("developer_id"));
            developerDao.setFirstname(resultSet.getString("first_name"));
            developerDao.setLastname(resultSet.getString("last_name"));
            developerDao.setAge(resultSet.getInt("age"));
            developerDao.setSex(resultSet.getString("sex"));
            developerDao.setCompanyId(resultSet.getInt("company_id"));
            developerDao.setSalary(resultSet.getInt("salary"));
            Set<SkillDao> skills = skillRepository.getSkillsByDeveloperId(developerDao.getDeveloperId()).orElseThrow(()
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", developerDao.getDeveloperId())));
            developerDao.setSkills(skills);
            developerDaos.add(developerDao);
        }
        return developerDaos;
    }

    public List<DeveloperDao> findAll() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloperDaos(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<DeveloperDao> findById(Integer id) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloperDao(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<DeveloperDao> findByCompanyId(Integer companyId) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_COMPANY_ID)) {
            preparedStatement.setLong(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToDeveloperDaos(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}

