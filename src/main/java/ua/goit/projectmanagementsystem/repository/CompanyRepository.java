package ua.goit.projectmanagementsystem.repository;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.dao.CompanyDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyRepository {
    private static final String INSERT = "INSERT INTO companies (company_name, company_location) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE companies SET company_name = ?, company_location = ? WHERE company_id = ?;";
    private static final String FIND_BY_NAME = "SELECT * FROM companies WHERE companies.company_name = ?;";
    private static final String FIND_ALL = "SELECT * FROM companies WHERE company_name <> 'Unemployed' ORDER BY company_name;";
    private static final String DELETE = "DELETE FROM companies WHERE companies.company_id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM companies WHERE companies.company_id = ?;";

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

    public void update(CompanyDao companyDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, companyDao.getCompanyName());
            preparedStatement.setString(2, companyDao.getCompanyLocation());
            preparedStatement.setInt(3, companyDao.getCompanyId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(CompanyDao companyDao) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, companyDao.getCompanyId());
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

    private List<CompanyDao> mapToCompanyDaos(ResultSet resultSet) throws SQLException {
        List<CompanyDao> companyDaos = new ArrayList<>();
        while (resultSet.next()) {
            CompanyDao companyDao = new CompanyDao();
            companyDao.setCompanyId(resultSet.getInt("company_id"));
            companyDao.setCompanyName(resultSet.getString("company_name"));
            companyDao.setCompanyLocation(resultSet.getString("company_location"));
            companyDaos.add(companyDao);
        }
        return companyDaos;
    }

    public List<CompanyDao> findAll() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompanyDaos(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<CompanyDao> findById(Integer id) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompanyDao(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
