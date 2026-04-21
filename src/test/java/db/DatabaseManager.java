package db;

import java.sql.*;

public class DatabaseManager {

    private static final String URL = "jdbc:sqlite:prices.db";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS products (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT UNIQUE,
                    price INTEGER
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Integer getPrice(String name) {
        String sql = "SELECT price FROM products WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("price");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void insertOrUpdate(String name, int price) {
        Integer existingPrice = getPrice(name);

        if (existingPrice == null) {
            insert(name, price);
        } else {
            update(name, price);
        }
    }

    private static void insert(String name, int price) {
        String sql = "INSERT INTO products(name, price) VALUES(?, ?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setInt(2, price);
            pstmt.executeUpdate();

            System.out.println("Добавлено в БД: " + name + " = " + price);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void update(String name, int price) {
        String sql = "UPDATE products SET price = ? WHERE name = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, price);
            pstmt.setString(2, name);
            pstmt.executeUpdate();

            System.out.println("Обновлено в БД: " + name + " = " + price);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}