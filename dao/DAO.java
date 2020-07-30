package lesson4.dao;

import lesson4.File;
import lesson4.Storage;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    StorageDAO storageDAO = new StorageDAO();
    FileDAO fileDAO = new FileDAO();


    public File put(Storage storage, File file) throws Exception {
        checkMemory(storage, file);
        checkFormat(storage, file);
        //checkFileInDB(file);
        File temp = new File(file.getId(), file.getName(), file.getFormat(), file.getSize(), storage);
        return fileDAO.update(temp);
    }

    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        checkMemory(storage, files);
        checkFormat(storage, files);

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
        checkMemory(storageFrom, storageTo);
        checkFormat(storageFrom, storageTo);
        fileDAO.updateStorage(storageFrom, storageTo);

    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        storageDAO.findById(storageFrom.getId());
        storageDAO.findById(storageTo.getId());
        checkMemory(storageTo, fileDAO.findById(id));
        File temp = fileDAO.findById(id);

        File file = new File(temp.getId(), temp.getName(), temp.getFormat(), temp.getSize(), storageTo);
        fileDAO.update(file);
    }

    private void checkMemory(Storage storage, File file) throws Exception {
        // System.out.println("storage totalMemory=" + fileDAO.checkMemory(storage));
        // System.out.println("storage maxSize=" + storage.getStorageMaxSize());
        // System.out.println("file size=" + file.getSize());
        if (fileDAO.checkMemory(storage) + file.getSize() > storage.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storage.getId());
            throw new Exception();
        }
    }

    private void checkMemory(Storage storage, List<File> files) throws Exception {
        long sum = 0;
        for (File file : files) {
            sum += file.getSize();
        }
        if (fileDAO.checkMemory(storage) + sum > storage.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storage.getId());
            throw new Exception();
        }
    }


    private void checkMemory(Storage storageFrom, Storage storageTo) throws Exception {
        if (fileDAO.checkMemory(storageFrom) > storageTo.getStorageMaxSize() - fileDAO.checkMemory(storageTo)) {
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

        if (!(fileDAO.findById(file.getId()).equals(file))) {
            throw new Exception("file id=" + file.getId() + "do not exist in DB");
        }
    }
}




