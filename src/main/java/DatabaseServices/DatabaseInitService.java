package DatabaseServices;

public class DatabaseInitService {
    public static void main(String[] args) {
        ExecuteSql.executeSqlFile("sql/init_db.sql");
    }
}
