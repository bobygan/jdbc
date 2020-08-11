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
        Storage storageDb= dao.storageDAO.findById(storage.getId());
        List<File> files = new ArrayList<>();
        files.add(file);
        checkMemory( storageDb, files);
        checkFormat(storageDb, files);
        return dao.put(storageDb, file);
    }


    public List<File> putAll(Storage storage, List<File> files) throws Exception {
        validate(storage);
        Storage storageDb= dao.storageDAO.findById(storage.getId());

        for (File file : files) {
            validate(file);
        }
        checkMemory(storageDb, files);
        checkFormat(storageDb, files);
        return dao.putAll(storageDb, files);
    }

    public File delete(Storage storage, File file) throws Exception {
        validate(storage);
        validate(file);
        return dao.delete(storage, file);
    }

    public void transferAll(Storage storageFrom, Storage storageTo) throws Exception {

        validate(storageFrom);
        validate(storageTo);

        Storage storageDbTo= dao.storageDAO.findById(storageTo.getId());
        Storage storageDbFrom= dao.storageDAO.findById(storageFrom.getId());

        checkMemory(storageDbTo, storageDbFrom.getFileList());
        checkFormat(storageDbTo, storageDbFrom.getFileList());
        dao.transferAll(storageDbFrom, storageDbTo);
    }

    public void transferFile(Storage storageFrom, Storage storageTo, long id) throws Exception {
        validate(storageFrom);
        validate(storageTo);
        Storage storageDbTo= dao.storageDAO.findById(storageTo.getId());
        Storage storageDbFrom= dao.storageDAO.findById(storageFrom.getId());


        File fileTemp;
        fileTemp = null;

        for (int i = 0; i < storageDbFrom.getFileList().size(); i++) {
            if (storageDbFrom.getFileList().get(i).getId() == id) {
                fileTemp = storageDbFrom.getFileList().get(i);
            }

        }

        List<File> files = new ArrayList<>();
        files.add(fileTemp);
        checkMemory(storageDbTo, files);
        checkFormat(storageDbTo, files);
        dao.transferFile(storageDbFrom, storageDbTo, id);
    }


    private void checkMemory(Storage storageTo, List<File> files) throws Exception {
        if (checkSum(storageTo.getFileList())+ checkSum(files) > storageTo.getStorageMaxSize()) {
            System.err.println("not enough memory in storage id=" + storageTo.getId());
            throw new Exception();
        }
    }

    private Long checkSum(List<File> files) {
        Long sum = 0l;
        if (null==files) {
            System.err.println("checkSum wrong data");
            return 0l;
        }

        for (int i = 0; i < files.size(); i++) {
            if (null!=files.get(i)) {
                sum += files.get(i).getSize();
            }
        }
        return sum;
    }

    private void checkFormat(Storage storageTo, List<File> files) throws Exception {
        for (int i = 0; i < files.size(); i++) {
            if (!(storageTo.getFormatsSupported().contains(files.get(i).getFormat()))) {
                System.out.println(storageTo.getFormatsSupported());
                System.out.println(files.get(i));
                System.err.println("storage id=" + storageTo.getId() + " do not support Format file id=" + files.remove(i).getId());
                throw new Exception();
            }
        }
    }

    private static <T extends IdEntity> void validate(T data) throws Exception {
        if (data != null && data.getId() != null) {
        } else {
            System.err.println("wrong data Null");
            throw new Exception();
        }
    }
}

