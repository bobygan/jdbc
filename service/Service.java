package lesson4.service;

import lesson4.IdEntity;
import lesson4.dao.DAO;
import lesson4.models.File;
import lesson4.models.Storage;

import java.util.ArrayList;
import java.util.List;


public class Service {

    private DAO dao = new DAO();


    public File put(Storage storage, File file) throws Exception {
        validate(storage);
        validate(file);

        List<File> files = new ArrayList<>();
        files.add(file);

        checkMemory(storage, files);
        checkFormat(storage, files);
        File fileTemp=dao.put(storage, file);
        storage.getFileList().add(file);
        return fileTemp;
    }


    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        validate(storage);
        for (File file : files) {
            validate(file);
        }

        checkMemory(storage, files);
        checkFormat(storage, files);

        List<File> fileListTemp= dao.putAll(storage, files);
        storage.getFileList().addAll(files);
        return fileListTemp;
    }

    public File delete(Storage storage, File file) throws Exception {
        validate(storage);
        validate(file);
        File fileTemp=dao.delete(storage, file);
        storage.getFileList().remove(file);
        return fileTemp;
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        validate(storageFrom);
        validate(storageTo);
        checkMemory(storageTo, storageFrom.getFileList());
        checkFormat(storageTo, storageFrom.getFileList());
        dao.transferAll(storageFrom, storageTo);

        storageTo.getFileList().addAll(storageFrom.getFileList());
        storageFrom.getFileList().clear();
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        validate(storageFrom);
        validate(storageTo);
        File fileTemp=null;

        for (File file :  storageFrom.getFileList()) {
            if (file.getId()==id){
                fileTemp=file;
            }
        }
        validate(fileTemp);



        List<File> files = new ArrayList<>();
        files.add(fileTemp);


        checkMemory(storageTo, files);
        checkFormat(storageTo, files);
        dao.transferFile(storageFrom, storageTo, id);

        storageTo.getFileList().add(fileTemp);
        storageFrom.getFileList().remove(fileTemp);
    }


    private void checkMemory(Storage storageTo, List<File> files) throws Exception {
        if (checkSum(storageTo.getFileList()) + checkSum(files) > storageTo.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storageTo.getId());
            throw new Exception();
        }
    }

    public long checkSum(List<File>files) {
        long sum = 0;
        for (File file : files) {
            sum +=  file.getSize();
        }
        return sum;
    }

    private void checkFormat(Storage storageTo, List<File> files) throws Exception {
        String joinedString = joinString(storageTo.getFormatsSupported());

        for (File file : files) {
            if (!(joinedString.contains(file.getFormat()))) {
                System.err.println("storage id=" + storageTo.getId() + " do not support file id=" + file.getId());
                throw new Exception();
            }
        }
    }

    private String joinString(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            stringBuilder.append(strings[i]);
        }
        return stringBuilder.toString();
    }


    private static <T extends IdEntity> void validate(T data) throws Exception {
        if (data != null && data.getId() != null) {
        } else {
            System.err.println("wrong data");
            throw new Exception();
        }
    }
}

