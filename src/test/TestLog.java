package test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunline on 2016/9/10.
 */
public class TestLog {
    public static void main(String[] args) {
        String separator = File.separator;
        String directory = "myDir1" + separator + "myDir2";
        //System.out.println(file.getAbsoluteFile());
       File file1= new File(directory,"hehe.txt");
        //System.out.println(file1.getAbsoluteFile());
        if (!file1.exists()) {
            try {
                file1.getParentFile().mkdirs();
                file1.createNewFile();
                //file1.createNewFile();
            } catch (IOException e) {

            }
        } else
            System.out.println(file1.getAbsoluteFile());
    }
}
