package DatabaseServices;

import org.flywaydb.core.Flyway;

import java.sql.*;

public class Database {
    private static final Database INSTANCE = new Database();

    private Database() {

    }

    public static Database getInstance() {
        return INSTANCE;
    }

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:h2:./funnypatch");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    public static void migrate() {
        Flyway flyway = Flyway
                .configure()
                .dataSource("jdbc:h2:./funnypatch", null, null)
                .load();
        flyway.migrate();
    }
}
