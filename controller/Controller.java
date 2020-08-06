package lesson4.controller;

import lesson4.models.File;
import lesson4.models.Storage;
import lesson4.service.Service;

import java.util.List;

public class Controller {

    private Service service = new Service();

    public File put(Storage storage, File file) throws Exception {
        File temp = service.put(storage, file);
        System.out.println("put file id="+file.getId()+" to storage id="+storage.getId()+" was finished successfully");
        return temp;
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        List<File> temp=service.putAll(storage, files);
        System.out.println("put list files" +" to storage id="+storage.getId()+" was finished successfully");
        return temp;
    }

    public File delete(Storage storage, File file) throws Exception{
        File temp = service.delete(storage, file);
        System.out.println("delete file id="+file.getId()+" from storage id="+storage.getId()+" was finished successfully");
        return temp;
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        service.transferAll(storageFrom, storageTo);
        System.out.println("transferAll files from storage id="+storageFrom.getId()+" to storage id="+storageTo.getId()+" was finished successfully");
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        service.transferFile(storageFrom, storageTo, id);
        System.out.println("transfer file id="+id+" from storage id="+storageFrom.getId()+"to storage id="+storageTo.getId()+" was finished successfully");
    }

}
