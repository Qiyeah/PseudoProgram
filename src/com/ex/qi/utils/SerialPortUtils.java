package com.ex.qi.utils;

import com.ex.qi.dao.daoImpl.DeviceDaoImpl;
import com.ex.qi.entity.Device;
import gnu.io.SerialPort;
import test.TestSerialPort;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by sunline on 2016/8/23.
 */
public class SerialPortUtils extends SerialReader {
    byte[] data = new byte[0];

    @Override
    public void transformData(byte[] data) {
        if (null != data && 0 < data.length) {
            this.data = data;
        } else {
            close();
        }
    }

    public float[] parseACDegrees() {
        if (data.length > 0) {
            byte[] data = Arrays.copyOfRange(this.data, 19, this.data.length - 1);
            byte[][] arr = new byte[5][8];
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
                    //e.printStackTrace();
                }
            }
            return result;
        } else
            //System.out.println("配置错误，没有接收到数据");
            return null;
    }

    public float[] parseDCDegrees() {
        String temp = "";
        if (null != data && 0 < data.length) {
            float[] degrees = new float[(this.data.length - 5) / 4];
            for (int i = 3; i < this.data.length - 2; i++) {
                temp += this.data[i];
               // System.out.println(temp);
                if ((i - 2) % 4 == 0) {
                    degrees[(i - 3) / 4] = Integer.parseInt(temp) / 10f;
                    temp = "";
                }
/*
                System.out.println(data.length);
                for (byte b : data) {
                    System.out.print(b + " ");
                }*/
            }
            return degrees;
        }
        return null;
    }

    public String byte2HexString(byte b) {
        if (b <= 57) {
            return Integer.toHexString(b % 0x30);
        }
        if (b >= 65 && b <= 70) {
            return "" + (char) b;
        }
        return "";
    }

    public byte[] getData() {
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