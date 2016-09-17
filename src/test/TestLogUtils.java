package test;

import com.ex.qi.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by qi on 2016/9/17.
 */
public class TestLogUtils {
    public static void main(String[] args) {
        LogUtils utils = new LogUtils();
        StringBuilder sb = new StringBuilder();
        sb.append("这是日志输出目录");
        //utils.writeLog("log.txt",sb);
        /*File file = new File("G:/log/log.txt");
        try {
           if (file.exists()){

           }else {
               file.getParentFile().mkdirs();
               file.createNewFile();
           }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
