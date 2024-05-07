package net.java.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import config.ConnexionDb;
import net.java.model.Car;

public class CarDAO {
    private static final String INSERT_CAR_SQL = "INSERT INTO car (registration_number, model, manufacturer, year, color) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_CAR_BY_REGISTRATION_NUMBER = "SELECT registration_number, model, manufacturer, year, color FROM car WHERE registration_number=?";
    private static final String SELECT_ALL_CARS = "SELECT registration_number, model, manufacturer, year, color FROM car";
    private static final String DELETE_CAR_SQL = "DELETE FROM car WHERE registration_number=?";
    private static final String UPDATE_CAR_SQL = "UPDATE car SET model=?, manufacturer=?, year=?, color=? WHERE registration_number=?";

    public CarDAO() {
    }

    public void insertCar(Car car) throws SQLException {
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CAR_SQL)) {
            preparedStatement.setString(1, car.getRegistrationNumber());
            preparedStatement.setString(2, car.getModel());
            preparedStatement.setString(3, car.getManufacturer());
            preparedStatement.setString(4, car.getYear());
            preparedStatement.setString(5, car.getColor());
            preparedStatement.executeUpdate();
        }
    }

    public Car selectCar(String registrationNumber) {
        Car car = null;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CAR_BY_REGISTRATION_NUMBER)) {
            preparedStatement.setString(1, registrationNumber);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String model = rs.getString("model");
                String manufacturer = rs.getString("manufacturer");
                String year = rs.getString("year");
                String color = rs.getString("color");
                car = new Car(registrationNumber, model, manufacturer, year, color);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return car;
    }

    public List<Car> selectAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CARS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String registrationNumber = rs.getString("registration_number");
                String model = rs.getString("model");
                String manufacturer = rs.getString("manufacturer");
                String year = rs.getString("year");
                String color = rs.getString("color");
                cars.add(new Car(registrationNumber, model, manufacturer, year, color));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cars;
    }

    public boolean deleteCar(String registrationNumber) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CAR_SQL)) {
            statement.setString(1, registrationNumber);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }

    public boolean updateCar(Car car) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CAR_SQL)) {
            statement.setString(1, car.getModel());
            statement.setString(2, car.getManufacturer());
            statement.setString(3, car.getYear());
            statement.setString(4, car.getColor());
            statement.setString(5, car.getRegistrationNumber());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    public List<Car> searchCars(String keyword) {
        List<Car> cars = new ArrayList<>();
        
        String SEARCH_CARS_SQL = "SELECT registration_number, model, manufacturer, year, color FROM car WHERE registration_number LIKE ? OR model LIKE ? OR manufacturer LIKE ? OR color LIKE ? OR year LIKE ?";
        try (Connection connection = ConnexionDb.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_CARS_SQL)) {
            
            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            preparedStatement.setString(3, searchKeyword);
            preparedStatement.setString(4, searchKeyword);
            preparedStatement.setString(5, searchKeyword);
            
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String registrationNumber = rs.getString("registration_number");
                String model = rs.getString("model");
                String manufacturer = rs.getString("manufacturer");
                String year = rs.getString("year");
                String color = rs.getString("color");
                cars.add(new Car(registrationNumber, model, manufacturer, year, color));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return cars;
    }
    
    private void printSQLException(SQLException ex) {
        // Your existing printSQLException method
    }
}
