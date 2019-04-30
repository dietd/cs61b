package byow.proj3.files;

import byow.proj3.World;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;

public class FileSystem {

    public static void save(World world) {
        File f = new File("./saved_world.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(world);
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            //System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            //System.exit(0);
        }
    }

    public static World load() {
        File f = new File("./saved_world.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                return (World) os.readObject();
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                //System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                //System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                //System.exit(0);
            }
        }
        return new World(123);
    }
}
