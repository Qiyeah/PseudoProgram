package com.ex.qi.utils;

import com.ex.qi.dao.daoImpl.DeviceDaoImpl;
import com.ex.qi.entity.Device;
import gnu.io.SerialPort;
import test.TestSerialPort;

import javax.swing.*;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

/**
 * Created by sunline on 2016/8/23.
 */
public class SerialPortUtils extends SerialReader {
    int[] data = new int[0];
    private static final String CRLF = "\r\n";
    @Override
    public void transformData(int[] data) {
        if (null != data && 0 < data.length) {
            /*System.out.println();
            System.out.println();
            System.out.println("--****************原始数据*********************--");
            for (int i = 0; i < data.length; i++) {
                System.out.print(data[i] + " ");
            }
            System.out.println("\n--*********************************************--");
            System.out.println();
            System.out.println();*/
            this.data = data;
        } else {
            close();
        }
    }

    public float[] parseACDegrees() {
        if (data.length > 0) {
            int[] data = Arrays.copyOfRange(this.data, 19, this.data.length - 1);
            int[][] arr = new int[5][8];
            float[] result = new float[5];
            int x = 0;
            for (int i = 0; i < data.length; i++) {
                //取出电度值（取出每四十个数据中的前八个数据，一共取五次）
                if (i % 40 == 0 && x < 5) {
                    //取出前八个数据
                    arr[x] = Arrays.copyOfRange(data, i, i + 8);
                    //取出次数加1
                    x++;
                }
            }
            for (int i = 0; i < arr.length; i++) {
                String str4 = byte2HexString(arr[i][6]) + byte2HexString(arr[i][7]);
                String str3 = byte2HexString(arr[i][4]) + byte2HexString(arr[i][5]);
                String str2 = byte2HexString(arr[i][2]) + byte2HexString(arr[i][3]);
                String str1 = byte2HexString(arr[i][0]) + byte2HexString(arr[i][1]);
                try {
                    result[i] = Float.intBitsToFloat(Integer.parseInt((str4 + str3 + str2 + str1), 16));
                } catch (NumberFormatException e) {
                    printErrorLog(str4 + str3 + str2 + str1,str4 + str3 + str2 + str1);
                    //e.printStackTrace();
                }
            }
            return result;
        } else
            //System.out.println("配置错误，没有接收到数据");
            return null;
    }

    private int dCount = 0;

    public float[] parseDCDegrees() {
        dCount += 1;
        String temp = "";
        String error = " ";
        float[] degrees = new float[(this.data.length - 5) / 4];
        String str ="";
        for (int i : data) {
            str+=i+" ";
        }
        if (null != data && 0 < data.length) {
            for (int i = 3; i < this.data.length - 2; i++) {
                temp += Integer.toHexString(this.data[i]);
                error += this.data[i] + " ";
                if ((i - 2) % 4 == 0) {
                    try {
                        degrees[(i - 3) / 4] = Integer.parseInt(temp,16) / 10.0f;
//                        System.out.println( degrees[(i - 3) / 4]);
                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
                        System.out.println("=错误数据：=>>"+error);
                        printErrorLog(error,str);
                        return null;
                    }
                    temp = "";
                    error = null;
                }
            }
        }
        return degrees;
    }

    public String byte2HexString(int b) {
        if (b <= 57) {
            return Integer.toHexString(b % 0x30);
        }
        if (b >= 65 && b <= 70) {
            return "" + (char) b;
        }
        return "";
    }

    private static void printErrorLog(String error,String str) {
        File file = null;
        OutputStreamWriter writer = null;
        InputStreamReader reader = null;
        BufferedReader bufReader = null;
        FileInputStream fis = null;
        FileOutputStream fos = null;
        BufferedWriter bufWriter = null;
        try {
            file = new File("F:/java_space/PseudoProgram/out/artifacts/PseudoProgram_war_exploded/log.txt");
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
            bufWriter.write("-->>原数据");
            bufWriter.write("-----------------------------------------------");
            bufWriter.write(CRLF);
            bufWriter.write(str);
            bufWriter.write(CRLF);
            bufWriter.write("-->>出错字节");
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

    public int[] getData() {
        return data;
    }

}
/**
 * //给五路三相多路表发送的命令，地址为01；
 * byte[] mCmd1 = new byte[]{(byte) 0x7e, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x31
 * , (byte) 0x39, (byte) 0x30, (byte) 0x38, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30
 * , (byte) 0x30, (byte) 0x46, (byte) 0x44, (byte) 0x41, (byte) 0x42, (byte) 0x0d};
 * //给直流电间发送的命令，地址为01；
 * byte[] mCmd2 = new byte[]{(byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x0e, (byte) 0x00
 * , (byte) 0x20, (byte) 0x24, (byte) 0x69};
 */