package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.exception.DeveloperNotFoundException;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.SkillDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class DeveloperRepository {
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
                    -> new DeveloperNotFoundException(String.format("Developer with ID %d does not exist", developerDao.getDeveloperId())));;
            developerDao.setSkills(skills);
            developers.add(developerDao);
        }
        return developers;
    }
}
