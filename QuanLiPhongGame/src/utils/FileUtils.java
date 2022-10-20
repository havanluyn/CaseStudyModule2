package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static <E> List<E> read(String fileName) {
        List<E> list = new ArrayList<>();
        try {
            InputStream os = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(os);
            E item;
            while ((item = (E) ois.readObject()) != null) {
                list.add(item);
            }
            return list;
        } catch (Exception e) {
        }
        return list;
    }

    public static <T> void write(String fileName, List<T> items) {
        try {
            File file = new File(fileName);
            OutputStream os = new FileOutputStream(file);
            ObjectOutputStream oss = new ObjectOutputStream(os);
            for (T item : items) {
                oss.writeObject(item);
            }
            oss.flush();
            oss.close();
        } catch (Exception e) {

        }
    }
}
