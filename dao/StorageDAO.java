package lesson4.dao;

import lesson4.models.Storage;

import java.sql.*;

public class StorageDAO {

    private static final String sqlRequestSaveStorage = "INSERT INTO STORAGE (ID,formatsSupported,storageCountry,storageMaxSize) VALUES (?,?,?,?)";
    private static final String sqlRequestDeleteStorage = "DELETE FROM STORAGE WHERE  ID =?";
    private static final String sqlRequestUpdateStorage = "UPDATE STORAGE SET formatsSupported=?, storageCountry=?, storageMaxSize=? WHERE ID=?";
    private static final String sqlRequestFindByIdStorage = "SELECT * FROM STORAGE WHERE  ID =?";


    public Storage save(Storage storage) throws Exception {
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestSaveStorage)) {
            prepareStatement.setLong(1, storage.getId());
            prepareStatement.setString(2, storage.getFormatsSupported().toString());
            prepareStatement.setString(3, storage.getStorageCountry());
            prepareStatement.setLong(4, storage.getStorageMaxSize());

            int res = prepareStatement.executeUpdate();
            System.out.println("save storage id="+storage.getId()+ " was finished with result " + res);
            return storage;

        } catch (SQLException e) {
            System.err.println("Something went wrong during save storage id="+storage.getId());
            e.printStackTrace();
            throw e;
        }
    }

    public void delete(long id) throws Exception {
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestDeleteStorage)) {
            prepareStatement.setLong(1, id);
            int res = prepareStatement.executeUpdate();
            System.out.println("delete was finished with result= " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong during delete storage id= " + id);
            e.printStackTrace();
            throw e;
        }
    }

    public Storage update(Storage storage) throws Exception{
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestUpdateStorage)) {
            prepareStatement.setLong(4, storage.getId());
            prepareStatement.setString(1, storage.getFormatsSupported().toString());
            prepareStatement.setString(2, storage.getStorageCountry());
            prepareStatement.setLong(3, storage.getStorageMaxSize());

            int res = prepareStatement.executeUpdate();
            System.out.println("update storage id="+storage.getId()+" was finished with result  " + res);
            return storage;

        } catch (SQLException e) {
            System.err.println("Something went wrong during update");
            e.printStackTrace();
            throw e;
        }
    }

    public Storage findById(Long id) throws Exception {
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestFindByIdStorage)) {

            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            resultSet.next();
            Storage storage = new Storage(resultSet.getLong(1), resultSet.getString(2).split("//"), resultSet.getString(3), resultSet.getLong(4));
            System.out.println("findStorageById id="+id+" was finished successfully");
            return storage;

        } catch (SQLException e) {
            System.err.println("Something went wrong during findStorageById storage id=" + id);
            e.printStackTrace();
            throw e;
        }
    }

}
