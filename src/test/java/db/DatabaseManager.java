package db;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:identifier.sqlite";

    public static Connection connect() {
        try {
            return DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "name TEXT PRIMARY KEY, " +
                "price TEXT" +
                ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sql);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPrice(String name) {
        String sql = "SELECT price FROM products WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("price");
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void insert(String name, String price) {
        String sql = "INSERT INTO products(name, price) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);
            stmt.setString(2, price);

            stmt.executeUpdate();

            System.out.println("INSERT: " + name + " -> " + price);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(String name, String price) {
        String sql = "UPDATE products SET price = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, price);
            stmt.setString(2, name);

            stmt.executeUpdate();

            System.out.println("UPDATE: " + name + " -> " + price);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}