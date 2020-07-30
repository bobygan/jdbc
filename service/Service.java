package lesson4.service;

import lesson4.models.File;
import lesson4.models.Storage;
import lesson4.dao.DAO;

import java.util.List;

public class Service {

    DAO dao = new DAO();


    public File put(Storage storage, File file) throws Exception {
        validateStorage(storage);
        validateFile(file);
        checkMemory(storage, file);
        checkFormat(storage, file);
        return dao.put(storage, file);
    }


    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        validateStorage(storage);
        validateFile(files);

        checkMemory(storage, files);
        checkFormat(storage, files);
        return dao.putAll(storage, files);
    }

    public File delete(Storage storage, File file)throws Exception {
        validateStorage(storage);
        validateFile(file);
        return dao.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {
        validateStorage(storageFrom);
        validateStorage(storageTo);
        checkMemory(storageFrom, storageTo);
        checkFormat(storageFrom, storageTo);
        dao.transferAll(storageFrom, storageTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        validateStorage(storageFrom);
        validateStorage(storageTo);
        dao.storageDAO.findById(storageFrom.getId());
        dao.storageDAO.findById(storageTo.getId());
        checkMemory(storageTo, dao.fileDAO.findById(id));
        dao.transferFile(storageFrom, storageTo, id);
    }


    private void checkMemory(Storage storage, File file) throws Exception {
        // System.out.println("storage totalMemory=" + fileDAO.checkMemory(storage));
        // System.out.println("storage maxSize=" + storage.getStorageMaxSize());
        // System.out.println("file size=" + file.getSize());
        if (dao.fileDAO.checkMemory(storage) + file.getSize() > storage.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storage.getId());
            throw new Exception();
        }
    }

    private void checkMemory(Storage storage, List<File> files) throws Exception {
        long sum = 0;
        for (File file : files) {
            sum += file.getSize();
        }
        if (dao.fileDAO.checkMemory(storage) + sum > storage.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storage.getId());
            throw new Exception();
        }
    }


    private void checkMemory(Storage storageFrom, Storage storageTo) throws Exception {
        if (dao.fileDAO.checkMemory(storageFrom) > storageTo.getStorageMaxSize() - dao.fileDAO.checkMemory(storageTo)) {
            System.err.println("not enough memory in storage id=" + storageTo.getId());
            throw new Exception();
        }
    }

    public void checkFormat(Storage storage, File file) throws Exception {
        String joinedString = joinString(storage.getFormatsSupported());
        // System.out.println("storageFormatsSupported=" + joinedString);
        // System.out.println("fileFormats=" + file.getFormat());
        if (!(joinedString.contains(file.getFormat()))) {
            System.err.println("storage id=" + storage.getId() + " do not support file id=" + file.getId());
            throw new Exception();
        }
    }

    public void checkFormat(Storage storageFrom, Storage storageTo) throws Exception {
        String joinedStringFrom = joinString(storageFrom.getFormatsSupported());
        String joinedStringTo = joinString(storageTo.getFormatsSupported());
        // System.out.println("storageFormatsSupported=" + joinedString);
        // System.out.println("fileFormats=" + file.getFormat());
        if (!(joinedStringTo.contains(joinedStringFrom))) {
            System.err.println("storageTo id=" + storageTo.getId() + " do not support format storageFrom id=" + storageFrom.getId());
            throw new Exception();
        }
    }

    private void checkFormat(Storage storage, List<File> files) throws Exception {
        String joinedString = joinString(storage.getFormatsSupported());
        for (File file : files) {
            if (!(joinedString.contains(file.getFormat()))) {
                System.err.println("storage id=" + storage.getId() + " do not support file id=" + file.getId());
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


    private void checkFileInDB(File file) throws Exception {
        if (!(dao.fileDAO.findById(file.getId()).equals(file))) {
            throw new Exception("file id=" + file.getId() + "do not exist in DB");
        }
    }


    private void validateFile(File file) throws Exception {
        if (file == null || file.getId() == null) {
            System.err.println("wrong file data");
            throw new Exception();
        }
    }
        private void validateFile(List<File>files) throws Exception {
               for (File file : files) {
                validateFile(file);
               }
        }


    private void validateStorage(Storage storage) throws Exception {
        if (storage == null || storage.getId() == null) {
            System.err.println("wrong storage data");
            throw new Exception();
        }
    }
}

