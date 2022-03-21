package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.dao.CompanyDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class CompanyRepository implements Repository<CompanyDao> {
    private static final String INSERT = "INSERT INTO companies (company_name, company_location) VALUES (?, ?);";
    private static final String FIND_BY_NAME = "SELECT * FROM companies WHERE companies.company_name = ?;";
    private static final String DELETE = "DELETE FROM companies WHERE companies.company_name = ?;";

    private final DatabaseManager databaseManager;

    public CompanyRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<CompanyDao> findByName(String name) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompanyDao(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void save(CompanyDao companyDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, companyDao.getCompanyName());
            preparedStatement.setString(2, companyDao.getCompanyLocation());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(CompanyDao companyDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setString(1, companyDao.getCompanyName());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Optional<CompanyDao> mapToCompanyDao(ResultSet resultSet) throws SQLException {
        CompanyDao companyDao = null;
        while (resultSet.next()) {
            companyDao = new CompanyDao();
            companyDao.setCompanyId(resultSet.getInt("company_id"));
            companyDao.setCompanyName(resultSet.getString("company_name"));
            companyDao.setCompanyLocation(resultSet.getString("company_location"));
        }
        return Optional.ofNullable(companyDao);
    }
}
