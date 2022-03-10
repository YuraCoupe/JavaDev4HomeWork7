package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ProjectRepository {
    private final static String GET_SALARY_SUM_BY_PROJECT_ID =
            "SELECT pr.project_id, pr.project_name, SUM(d.salary) as project_value\n" +
            "FROM projects pr\n" +
            "JOIN developersToProjects dtp ON dtp.project_id = pr.project_id \n" +
            "JOIN developers d ON dtp.developer_id = d.developer_id \n" +
            "WHERE pr.project_id = ?\n" +
            "GROUP BY pr.project_id, pr.project_name";

    private final DatabaseManager databaseManager;

    public ProjectRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Integer> getSalarySum(Integer projectId) {
        try (Connection connection = databaseManager.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(GET_SALARY_SUM_BY_PROJECT_ID)) {
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return Optional.ofNullable(resultSet.getInt("project_value"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
