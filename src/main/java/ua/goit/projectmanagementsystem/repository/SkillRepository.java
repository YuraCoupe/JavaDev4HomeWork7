package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.dao.DeveloperDao;
import ua.goit.projectmanagementsystem.model.dao.SkillDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SkillRepository {
    private static final String GET_SKILLS_BY_DEVELOPER_ID =
            "SELECT dts.developer_id, s.skill_id, s.specialization, s.level\n" +
                    "FROM developerstoskills dts\n" +
                    "JOIN skills s ON dts.skill_id = s.skill_id \n" +
                    "WHERE dts.developer_id = ?";

    private final DatabaseManager databaseManager;

    public SkillRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Set<SkillDao>> getSkillsByDeveloperId(Integer developerId) {
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_SKILLS_BY_DEVELOPER_ID)) {
            preparedStatement.setInt(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<SkillDao> skills = new HashSet<>();
            while (resultSet.next()) {
                SkillDao skillDao = new SkillDao();
                skillDao.setSkillId(resultSet.getInt("skill_id"));
                skillDao.setLevel(resultSet.getString("level"));
                skillDao.setSpecialization(resultSet.getString("specialization"));
                skills.add(skillDao);
            }
            return Optional.ofNullable(skills);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
