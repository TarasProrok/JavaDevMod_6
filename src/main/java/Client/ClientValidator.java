package Client;

public class ClientValidator {

    public static boolean idValidation (long id) {
        if (id < 1) {
            return false;
        }
        return true;
    }

    public static boolean nameValidation (String name) {
        if (name.length()<2 || name.length() > 100) {
            return false;
        }
        return true;
    }
}
