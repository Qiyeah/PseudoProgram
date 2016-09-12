package test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sunline on 2016/9/10.
 */
public class TestPrintLog {
    private static final String CRLF = "\r\n";
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            printErrorLog("error data "+i);
        }
    }
    private static void printErrorLog(String error) {
        File file = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedWriter bufWriter = null;
        try {
            file = new File("F:/java_space/PseudoProgram/out/artifacts/PseudoProgram_war_exploded/log1.txt");
            fis = new FileInputStream(file);
            reader = new InputStreamReader(fis);
            bufReader = new BufferedReader(reader);
            fos = new FileOutputStream(file,true);
            writer = new OutputStreamWriter(fos);
            bufWriter = new BufferedWriter(writer);
            String line = null;
            while (null != (line = bufReader.readLine())) {
                //bufWriter.write(line + "\r\n");
                System.out.println(line);
            }
            StringBuilder sb = new StringBuilder();
            sb.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date()));
            bufWriter.write(sb.toString());
            bufWriter.write(CRLF);
            bufWriter.write("-----------------------------------------------");
            bufWriter.write(CRLF);
            bufWriter.write(error + CRLF);
            bufWriter.write("-----------------------------------------------");
            bufWriter.write(CRLF);

        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
                            e1.printStackTrace();
        } finally {
            try {
                file = null;
                bufWriter.flush();
                bufWriter.close();
                fis.close();
                reader.close();
                bufReader.close();
                fos.close();
                writer.close();

            } catch (IOException e1) {
                                e1.printStackTrace();
            }
        }
    }
}
