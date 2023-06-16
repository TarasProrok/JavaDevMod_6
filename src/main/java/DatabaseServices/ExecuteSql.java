package DatabaseServices;

import java.sql.*;
import java.util.function.Consumer;

public class ExecuteSql {
    public void QueExecute (String sql, Consumer<ResultSet> consumer) {
        Database database = Database.getInstance();

        try (Connection connection = database.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            consumer.accept(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

        public static int executeUpdateSql (String sql) {
        try (Connection conn = Database.getConnection();
        PreparedStatement statement = conn.prepareStatement(sql)) {
            return statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static void executeSqlFile(String fileName) {
        String sql = DbUtility.getFilesLines (fileName);
        executeUpdateSql(sql);
    }
}
