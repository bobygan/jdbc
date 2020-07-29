package lesson4;

import java.util.List;

public class Service {

    DAO dao = new DAO();


    public File put(Storage storage, File file) throws Exception{
         return dao.put(storage, file);
    }


    public List<File> putAll(Storage storage, List<File> files)throws Exception {
        return dao.putAll(storage,files);
    }

    public File delete(Storage storage, File file) {
        return dao.delete(storage,file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception{
         dao.transferAll(storageFrom,storageTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception{
        dao.transferFile(storageFrom,storageTo,id);

    }


}
