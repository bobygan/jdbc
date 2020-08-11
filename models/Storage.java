package lesson4.models;

import lesson4.IdEntity;

import java.util.List;

public class Storage extends IdEntity {
    private Long id;
    private String formatsSupported;
    private String storageCountry;
    private Long storageMaxSize;
    private List <File> fileList;


    public Storage(Long id, String formatsSupported, String storageCountry, Long storageMaxSize, List<File> fileList) {
        this.id = id;
        this.formatsSupported = formatsSupported;
        this.storageCountry = storageCountry;
        this.storageMaxSize = storageMaxSize;
        this.fileList = fileList;
    }

    public Storage(Long id) {
        this.id = id;

    }

    public Long getId() {
        return id;
    }

    public String getFormatsSupported() {
        return formatsSupported;
    }

    public String getStorageCountry() {
        return storageCountry;
    }

    public Long getStorageMaxSize() {
        return storageMaxSize;
    }

    public List<File> getFileList() {
        return fileList;
    }

    @Override
    public String toString() {
        return "Storage{" +
                "id=" + id +
                ", formatsSupported='" + formatsSupported + '\'' +
                ", storageCountry='" + storageCountry + '\'' +
                ", storageMaxSize=" + storageMaxSize +
                ", fileList=" + fileList +
                '}';
    }
}

