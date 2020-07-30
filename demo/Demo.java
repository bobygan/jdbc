package lesson4.demo;

import lesson4.models.File;
import lesson4.models.Storage;
import lesson4.controller.Controller;
import lesson4.dao.FileDAO;
import lesson4.dao.StorageDAO;

import java.util.ArrayList;

public class Demo {
    public static void main(String[] args) {
        String formats[] = {"txt", "doc", "bmp"};
        Storage storage0 = new Storage((long) 0, formats, "ua", (long) 10000);
        Storage storage1 = new Storage((long) 1, formats, "ua", (long) 1000000);
        Storage storage2 = new Storage((long) 2, formats, "ua", (long) 100);
        Storage storage3 = new Storage((long) 3, formats, "ua", (long) 100);
        Storage storage4 = new Storage((long) 4, formats, "ua", (long) 100);

        File file0 = new File((long) 0, "A", "txt", (long) 12, storage0);
        File file1 = new File((long) 1, "B", "txt", (long) 50, storage1);
        File file2 = new File((long) 2, "C", "txt", (long) 12, storage2);
        File file3 = new File((long) 3, "D", "txt", (long) 12, storage3);
        File file4 = new File((long) 4, "E", "txt", (long) 12, storage4);
        File file5 = new File((long) 5, "F", "txt", (long) 12, storage4);
        File file6=  new File((long) 6, "G", "txt", (long) 12, storage4);
        File file7 = new File((long) 7, "I", "txt", (long) 12, storage4);
        File file8=  new File((long) 8, "H", "txt", (long) 12, storage4);

        FileDAO fileDAO = new FileDAO();
        StorageDAO storageDAO = new StorageDAO();
        Controller controller = new Controller();



        try {
            storageDAO.save(storage0);
        } catch (Exception e) {
        }

        try {
            storageDAO.update(storage0);
        } catch (Exception e) {
        }



        try {
            storageDAO.save(storage1);
        } catch (Exception e) {
        }

            try {
                storageDAO.delete(storage1.getId());
            } catch (Exception e) {
            }


        try {
            storageDAO.save(storage2);
        } catch (Exception e) {
        }

        try {
            System.out.println( (storageDAO.findById(storage2.getId()).toString()));
        } catch (Exception e) {
        }

      //  System.exit(1);


        try {
            storageDAO.save(storage3);
        } catch (Exception e) {
        }
        // fileDAO.update(file0);
        //fileDAO.update(file1);
        // fileDAO.update(file2);
        // fileDAO.update(file3);

        try{ storageDAO.save(storage4); }
          catch (Exception e){
         }

        try {
            fileDAO.save(file0);
        } catch (Exception e) {
        }

        try {
            fileDAO.findById(file0.getId());
        } catch (Exception e) {
        }

        //System.exit(1);

        try {
            fileDAO.save(file1);
        } catch (Exception e) {
        }
        try {
            fileDAO.save(file2);
        } catch (Exception e) {
        }
        try {
            fileDAO.save(file3);
        } catch (Exception e) {
        }

        try {
            fileDAO.save(file4);
        } catch (Exception e) {
        }

        try {
            fileDAO.save(file5);
        } catch (Exception e) {
        }

        try {
            fileDAO.save(file6);
        } catch (Exception e) {
        }

        try {
            fileDAO.save(file7);
        } catch (Exception e) {
        }

        try {
            fileDAO.save(file8);
        } catch (Exception e) {
        }

        try {
            System.out.println(fileDAO.findById((long)8).toString());
        } catch (Exception e) {
        }

      //  try {
      //      fileDAO.update(file0);
      //  } catch (Exception e) {
      //  }

      //  try {
      //      fileDAO.update(file1);
      //  } catch (Exception e) {
        // }


        try {
            controller.put(storage0,file1);
        } catch (Exception e) {
        }

      //  try {
        //    controller.delete(storage0,file1);
       // } catch (Exception e) {
      //  }


     //   try {
      //      storageDAO.update(storage0);
     //   } catch (Exception e) {
     //   }

        ArrayList<File> arrayList = new ArrayList<>();
        arrayList.add(file4);
        arrayList.add(file5);
        arrayList.add(file6);
        arrayList.add(file7);
        arrayList.add(file8);
      //  try {
      //      controller.putAll(storage0,arrayList);
     //   } catch (Exception e) {
     //   }

        try {
            controller.transferAll(storage4,storage1);
        } catch (Exception e) {
        }

        try {
            controller.transferFile(storage4,storage2,4);
        } catch (Exception e) {
        }

    }

}
