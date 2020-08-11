package lesson4.dao;

import lesson4.models.File;
import lesson4.models.Storage;

import java.sql.*;
import java.util.List;

public class FileDAO {
    private static final String sqlRequestSaveFile = "INSERT INTO FI (ID, NAMEFILE, FORMATFILE, SIZEFILE, STORAGEID) VALUES (?,?,?,?,?)";
    private static final String sqlRequestDeleteFile = "DELETE FROM FI WHERE  ID =?";
    private static final String sqlRequestUpdateFile = "UPDATE FI SET NAMEFILE=?, FORMATFILE=?, SIZEFILE=?, STORAGEID=? WHERE ID=?";
    private static final String sqlRequestFindByIdFile = "SELECT * FROM FI WHERE  ID =?";
    private static final String sqlRequestUpdateStorageFile = "UPDATE FI SET storageid=? WHERE storageid=?";


    public File save(File file) throws Exception {
        Storage storage = (file.getStorage());
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestSaveFile)) {
            prepareStatement.setLong(1, file.getId());
            prepareStatement.setString(2, file.getName());
            prepareStatement.setString(3, file.getFormat());
            prepareStatement.setLong(4, file.getSize());
            prepareStatement.setLong(5, storage.getId());

            int res = prepareStatement.executeUpdate();
            System.out.println("save file id=" + file.getId() + " was finished with result" + res);
            return file;

        } catch (SQLException e) {
            System.err.println("Something went wrong during save file id=" + file.getId());
            e.printStackTrace();
            throw e;
        }
    }

    public void delete(long id) {
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestDeleteFile)) {
            prepareStatement.setLong(1, id);

            int res = prepareStatement.executeUpdate();
            System.out.println("delete file id= " + id + " was finished with result " + res);
        } catch (SQLException e) {
            System.err.println("Something went wrong during delete file id=" + id);
            e.printStackTrace();
        }
    }

    public File update(File file) throws Exception {
        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestUpdateFile)) {
            prepareStatement.setString(1, file.getName());
            prepareStatement.setString(2, file.getFormat());
            prepareStatement.setLong(3, file.getSize());
            prepareStatement.setLong(4, file.getStorage().getId());
            prepareStatement.setLong(5, file.getId());

            int res = prepareStatement.executeUpdate();

            System.out.println("update file id=" + file.getId() + " was finished with result  " + res);
            return file;

        } catch (SQLException e) {
            System.err.println("Something went wrong during update file ID= " + file.getId());
            e.printStackTrace();
            throw e;
        }

    }


    public List<File> updateList(List<File> files) throws Exception {

        long id = 0;
        try (PreparedStatement prepareStatement = Jdbc.connect(false).prepareStatement(sqlRequestUpdateFile)) {
            int res = 0;
            for (File file : files) {

                id = file.getId();
                prepareStatement.setLong(5, file.getId());
                prepareStatement.setString(1, file.getName());
                prepareStatement.setString(2, file.getFormat());
                prepareStatement.setLong(3, file.getSize());
                prepareStatement.setLong(4, file.getStorage().getId());
                res = prepareStatement.executeUpdate();
            }
            Jdbc.connect(false).commit();
            System.out.println("Update files off list, was finished with result  " + res);
            return files;
        } catch (SQLException e) {
            System.err.println("Something went wrong during update list, id wrong file= " + id);
            e.printStackTrace();
            Jdbc.connect(false).rollback();
            throw e;
        }
    }


    public File findById(Long id) throws Exception {

        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestFindByIdFile)) {
            StorageDAO storageDAO = new StorageDAO();
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            resultSet.next();



            File file = new File(resultSet.getLong(1), resultSet.getString(2), resultSet.getString(3), resultSet.getLong(4), storageDAO.findById(resultSet.getLong(1)));
            System.out.println("findFileById id="+id+" was finished successfully");
            return file;

        } catch (SQLException e) {
            System.err.println("Something went wrong during findById, file ID= " + id);
            e.printStackTrace();
            throw e;
        }

    }



    public void updateStorage(Storage storageFrom, Storage storageTo) throws Exception {

        try (PreparedStatement prepareStatement = Jdbc.connect(true).prepareStatement(sqlRequestUpdateStorageFile)) {

            prepareStatement.setLong(2, storageFrom.getId());
            prepareStatement.setLong(1, storageTo.getId());
            int res = prepareStatement.executeUpdate();
            System.out.println("update storageFrom id=" + storageFrom.getId() + " storageTo id=" + storageTo.getId() + " was finished with result  " + res);

        } catch (SQLException e) {
            System.err.println("Something went wrong during updateStorage");
            e.printStackTrace();
            throw e;
        }
    }
}
