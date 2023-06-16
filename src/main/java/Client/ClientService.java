package Client;

import DatabaseServices.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ClientService {
    private PreparedStatement createClient;
    private PreparedStatement getByIdClient;
    private PreparedStatement setNameClient;
    private PreparedStatement deleteByIdClient;
    private PreparedStatement allClients;
    private PreparedStatement selectMaxIdSt;

    public ClientService() {
        Connection connection = Database.getConnection();

        try {
            createClient = connection.prepareStatement("INSERT INTO client (name) VALUES (?)");
            getByIdClient = connection.prepareStatement("SELECT name FROM client WHERE id = ?");
            setNameClient = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
            deleteByIdClient = connection.prepareStatement("DELETE FROM client WHERE id = ?");
            allClients = connection.prepareStatement("SELECT id, name FROM client");
            selectMaxIdSt = connection.prepareStatement("SELECT max(id) AS maxId FROM client");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public long create(String name) {
        long id;
        if (!ClientValidator.nameValidation(name)) {
            throw new RuntimeException("Invalid Name");
        }
        try {
            createClient.setString(1, name);
            createClient.executeUpdate();

            ResultSet resultSet = selectMaxIdSt.executeQuery();
            resultSet.next();
            id = resultSet.getLong("maxId");
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public String getById(long id) {
        String name;
        if (!ClientValidator.idValidation(id)) {
            throw new RuntimeException("Invalid Id");
        }
        try {
            getByIdClient.setLong(1, id);
            ResultSet resultSet = getByIdClient.executeQuery();
            resultSet.next();
            name = resultSet.getString("name");
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return name;
    }

    public void setName(long id, String name) {

        if (!ClientValidator.nameValidation(name)) {
            throw new RuntimeException("Invalid Name");
        }
        if (!ClientValidator.idValidation(id)) {
            throw new RuntimeException("Invalid Id");
        }
        try {
            setNameClient.setString(1, name);
            setNameClient.setLong(1, id);
            setNameClient.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteById(long id) {

        if (!ClientValidator.idValidation(id)) {
            throw new RuntimeException("Invalid Id");
        }

        try {
            deleteByIdClient.setLong(1, id);
            deleteByIdClient.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Client> listAll() {
        List<Client> clientsList = new ArrayList<>();
        try (ResultSet resultSet = allClients.executeQuery()){
            while (resultSet.next()) {
                clientsList.add(new Client(
                        resultSet.getLong("id"),
                        resultSet.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return clientsList;
    }
}