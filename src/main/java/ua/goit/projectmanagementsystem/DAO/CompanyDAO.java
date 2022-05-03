package ua.goit.projectmanagementsystem.DAO;

import ua.goit.projectmanagementsystem.config.DatabaseManager;
import ua.goit.projectmanagementsystem.model.domain.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompanyDAO {
    private static final String INSERT = "INSERT INTO companies (company_name, company_location) VALUES (?, ?);";
    private static final String UPDATE = "UPDATE companies SET company_name = ?, company_location = ? WHERE company_id = ?;";
    private static final String FIND_BY_NAME = "SELECT * FROM companies WHERE companies.company_name = ?;";
    private static final String FIND_ALL = "SELECT * FROM companies ORDER BY company_name;";
    private static final String FIND_ALL_EX_UNEMPLOYED = "SELECT * FROM companies WHERE company_name <> 'Unemployed' ORDER BY company_name;";
    private static final String DELETE = "DELETE FROM companies WHERE companies.company_id = ?;";
    private static final String FIND_BY_ID = "SELECT * FROM companies WHERE companies.company_id = ?;";

    private final DatabaseManager databaseManager;

    public CompanyDAO(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    public Optional<Company> findByName(String name) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompany(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public Integer save(Company company) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setString(2, company.getCompanyLocation());
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

    public void update(Company company) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, company.getCompanyName());
            preparedStatement.setString(2, company.getCompanyLocation());
            preparedStatement.setInt(3, company.getCompanyId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void delete(Company company) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE)) {
            preparedStatement.setInt(1, company.getCompanyId());
            preparedStatement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private Optional<Company> mapToCompany(ResultSet resultSet) throws SQLException {
        Company company = null;
        while (resultSet.next()) {
            company = new Company();
            company.setCompanyId(resultSet.getInt("company_id"));
            company.setCompanyName(resultSet.getString("company_name"));
            company.setCompanyLocation(resultSet.getString("company_location"));
        }
        return Optional.ofNullable(company);
    }

    private List<Company> mapToCompanys(ResultSet resultSet) throws SQLException {
        List<Company> companys = new ArrayList<>();
        while (resultSet.next()) {
            Company company = new Company();
            company.setCompanyId(resultSet.getInt("company_id"));
            company.setCompanyName(resultSet.getString("company_name"));
            company.setCompanyLocation(resultSet.getString("company_location"));
            companys.add(company);
        }
        return companys;
    }

    public List<Company> findAll() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompanys(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }

    public Optional<Company> findById(Integer id) {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompany(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }

    public List<Company> findAllExUnemployed() {
        try (Connection connection = databaseManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_EX_UNEMPLOYED)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            return mapToCompanys(resultSet);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new ArrayList<>();
    }
}
