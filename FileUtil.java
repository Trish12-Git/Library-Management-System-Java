import java.io.*;

public class FileUtil {

    // Save object to file
    public static void saveObject(String filename, Object obj) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(
                             new FileOutputStream(filename))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + filename);
            e.printStackTrace();
        }
    }

    // Load object from file
    public static Object loadObject(String filename) {
    File file = new File(filename);

    // If file doesn't exist OR is empty
    if (!file.exists() || file.length() == 0) {
        return null;
    }

    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
        return ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading from file: " + filename);
        e.printStackTrace();
        return null;
    }
  }
}