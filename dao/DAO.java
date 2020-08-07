package lesson4.dao;

import lesson4.models.File;
import lesson4.models.Storage;

import java.util.ArrayList;
import java.util.List;

public class DAO {
   public StorageDAO storageDAO = new StorageDAO();
   public FileDAO fileDAO = new FileDAO();


    public File put(Storage storage, File file) throws Exception {
        File temp = new File(file.getId(), file.getName(), file.getFormat(), file.getSize(), storage);
        return fileDAO.update(temp);
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        ArrayList<File> temp = new ArrayList<>();
        for (File file : files) {
            File file1 = new File(file.getId(), file.getName(), file.getFormat(), file.getSize(), storage);
            temp.add(file1);
        }
        return fileDAO.updateList(temp);
    }

    public File delete(Storage storage, File file) {
        fileDAO.delete(file.getId());
        return file;
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {

        fileDAO.updateStorage(storageFrom, storageTo);

    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        File temp = fileDAO.findById(id);
        File file = new File(temp.getId(), temp.getName(), temp.getFormat(), temp.getSize(), storageTo);
        fileDAO.update(file);
    }


}




