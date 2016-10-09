package com.ex.qi.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by qi on 2016/9/17.
 */
public class LogUtils {
    public static final String CRLF = "\r\n";

    public static final String SPLIT1 = "--------------------------------------------------------日志开始标签--------------------------------------------------------";
    public static final String SPLIT2 = "------------------------------------------------------------------";
    public static final String SPLIT3 = "********************************************************日志结束标签********************************************************";
    public static final String separator = File.separator;
    public static File file = null;
    public static OutputStreamWriter writer = null;
    public static InputStreamReader reader = null;
    public static BufferedReader bufReader = null;
    public static FileInputStream fis = null;
    public static FileOutputStream fos = null;
    public static BufferedWriter bufWriter = null;
    public static StringBuilder sb = new StringBuilder();
    public static SimpleDateFormat sdf = null;

    /**
     * @param str
     */
    public void writeLog(String file,StringBuffer str) {
        openIO(file);
        try {
//            System.out.println(null == bufWriter);
            bufWriter.append(CRLF);
            bufWriter.append(date2Str());
            bufWriter.append(CRLF);
            bufWriter.append(SPLIT1);
            bufWriter.append(CRLF);
            bufWriter.append(new String(str.toString().getBytes(),"gbk"));
            bufWriter.append(CRLF);
            bufWriter.append(SPLIT3);
            bufWriter.append(CRLF);

            bufWriter.flush();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            closeIO();
        }
    }

    public String date2Str() {
        Date date = new Date();
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        return sdf.format(date);
    }

    private void openIO(String fileName) {
        try {
            File file = new File("e:"+separator+"PseudoProgram"+separator+"log"+separator+fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
//            System.out.println(file.getAbsoluteFile());
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis);
            bufReader = new BufferedReader(reader);
            fos = new FileOutputStream(file,true);
            writer = new OutputStreamWriter(fos);
            bufWriter = new BufferedWriter(writer);
        } catch (Exception e) {
            //System.out.println("创建父路径失败！");
        }
    }

    private void closeIO() {
        try {
            if (null != file) {
                file = null;
            }
            if (null != bufWriter) {
                bufWriter.close();
            }
            if (null != fos) {
                fos.close();
            }
            if (null != writer) {
                writer.close();
            }
            if (null != fis) {
                fis.close();
            }
            if (null != reader) {
                reader.close();
            }
            if (null != bufReader) {
                bufReader.close();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
