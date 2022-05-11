package ua.goit.projectmanagementsystem.Dao;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.domain.Skill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SkillDao extends AbstractDao<Skill>{
    private static final String GET_SKILLS_BY_DEVELOPER_ID =
            "SELECT dts.developer_id, s.skill_id, s.specialization, s.level\n" +
                    "FROM developerstoskills dts\n" +
                    "JOIN skills s ON dts.skill_id = s.skill_id \n" +
                    "WHERE dts.developer_id = ?";

    private final DatabaseManager databaseManager;

    public SkillDao(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Set<Skill>> getSkillsByDeveloperId(Integer developerId) {
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_SKILLS_BY_DEVELOPER_ID)) {
            preparedStatement.setInt(1, developerId);
            ResultSet resultSet = preparedStatement.executeQuery();
            Set<Skill> skills = new HashSet<>();
            while (resultSet.next()) {
                Skill skill = new Skill();
                skill.setSkillId(resultSet.getInt("skill_id"));
                skill.setLevel(resultSet.getString("level"));
                skill.setSpecialization(resultSet.getString("specialization"));
                skills.add(skill);
            }
            return Optional.ofNullable(skills);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
