package test;

import com.ex.qi.entity.AC;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.DC;
import com.ex.qi.utils.DeviceUtils;
import com.ex.qi.utils.SerialPortUtils;

import java.util.*;

/**
 * Created by sunline on 2016/8/22.
 */
public class TestSerialPort {
    public static int IEC_TIMEOUT = 25;
    public static int MOD_TIMEOUT = 1;
    private static boolean isOpen = false;

    public static void main(String[] args) {
        //给五路三相多路表发送的命令，地址为01；
        byte[] mCmd1 = new byte[]{(byte) 0x7e, (byte) 0x32, (byte) 0x31, (byte) 0x30, (byte) 0x31
                , (byte) 0x39, (byte) 0x30, (byte) 0x38, (byte) 0x30, (byte) 0x30, (byte) 0x30, (byte) 0x30
                , (byte) 0x30, (byte) 0x46, (byte) 0x44, (byte) 0x41, (byte) 0x42, (byte) 0x0d};
        //给直流电间发送的命令，地址为01；
        byte[] mCmd2 = new byte[]{(byte) 0x01, (byte) 0x03, (byte) 0x02, (byte) 0x0e, (byte) 0x00
                , (byte) 0x20, (byte) 0x24, (byte) 0x69};
    /*    System.out.print("原始命令对照：");
        for (byte b : mCmd1) {
            System.out.print(b+"-");
        }
        System.out.println();*/

        DeviceUtils deviceUtils = new DeviceUtils();
        Equipment[] equipments = deviceUtils.loadDevice();

        for (int i = 0; i < equipments.length; i++) {
            float[] degrres = new float[0];
            SerialPortUtils utils = new SerialPortUtils();
            Equipment equipment = equipments[i];
            String id = equipment.getId();
            Map<String, Comparable> params = deviceUtils.parseToParams(equipment);
            utils.open(params);
            float[] data = null;
            if (id.startsWith("AC")) {
                AC iec = new AC();
                int addr = Integer.parseInt(equipment.getAddr());
                if (!(addr > 0xf)) {
                    iec.setADRHi((byte) 0x30);
                    iec.setADRLo(Integer.toHexString(addr).getBytes()[0]);
                } else {
                    iec.setADRHi(Integer.toHexString(addr / 16).getBytes()[0]);
                    iec.setADRLo(Integer.toHexString(addr % 16).getBytes()[0]);
                }
                iec.parseCheksum();
                byte[] msg = iec.produceCmdBytes();
                utils.sendMessage(msg, IEC_TIMEOUT);
                if (utils.getPortState()) {
                    degrres = utils.parseACDegrees();
                    Map<Integer,Float> history = findRecentData("DayKwh",id);
                    data = new float[degrres.length];
                    for (float degrre : degrres) {
                        System.out.print(degrre + " ");
                    }
                    for (int j = 1; j < degrres.length+1; j++) {
                        float hisValue = history.get(j);
                        float realValue = degrres[j-1];
                        data[j-1] = realValue-hisValue>1?realValue:hisValue+realValue;
                    }
                    System.out.println();
                }
            } else if (id.startsWith("DC")) {
                DC mod = new DC();
                int addr = Integer.parseInt(equipment.getAddr());
                mod.setAddr((byte) addr);
                mod.setFunction((byte) 0x03);
                mod.setMemeryHi((byte) 0x02);
                mod.setMemeryLo((byte) 0x0E);
                mod.setCountHi((byte) 0x00);
                mod.setCountLo((byte) 0x20);
                mod.parseCRC();
                byte[] msg = mod.produceCmdBytes();
                utils.sendMessage(msg, MOD_TIMEOUT);
                if (utils.getPortState()) {
                    degrres = utils.parseDCDegrees();
                    for (int j = 0; j < degrres.length; j++) {
                        System.out.print(degrres[j] + " ");
                    }
                }
            }
            utils.close();
        }
    }
    public static Map<Integer,Float> findRecentData(String tableName,String id){
        return null;

    }
}
