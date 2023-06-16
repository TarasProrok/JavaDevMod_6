package DatabaseServices;

public class DatabasePopulateService {
    public static void main(String[] args) {
        ExecuteSql.executeSqlFile("sql/populate_db.sql");
    }
}
