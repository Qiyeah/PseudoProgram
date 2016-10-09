package com.ex.qi.utils;

import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.entity.AC;
import com.ex.qi.entity.DC;
import com.ex.qi.entity.Equipment;
import gnu.io.SerialPort;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sunline on 2016/8/24.
 */
public class DeviceUtils {
    private String dataBit = "" + SerialPort.DATABITS_8;
    private String stopBit = "" + SerialPort.STOPBITS_1;
    private int parityInt = SerialPort.PARITY_NONE;
    byte[] data = new byte[0];

    /**
     * 查询数据库，加载所有已知的设备信息
     * @return
     */
    public Equipment[] loadDevice() {

        Equipment[] equipments = new EquipmentDaoImpl().findAllEquipments();

        return equipments;
    }

    /**
     * 通过设备信息，生成串口通信参数
     * @param equipment
     * @return
     */
    public Map<String, Comparable> parseToParams(Equipment equipment) {
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        params.put(SerialReader.PARAMS_PORT, equipment.getPort()); // 端口名称
        params.put(SerialReader.PARAMS_RATE, Integer.valueOf(equipment.getRate())); // 波特率
        params.put(SerialReader.PARAMS_DATABITS, Integer.valueOf(equipment.getDataBits())); // 数据位
        params.put(SerialReader.PARAMS_STOPBITS, Integer.valueOf(equipment.getStopBits())); // 停止位
        params.put(SerialReader.PARAMS_PARITY, Integer.valueOf(equipment.getParity())); // 无奇偶校验
        params.put(SerialReader.PARAMS_TIMEOUT, Integer.valueOf(equipment.getTimeOut())); // 设备超时时间 1秒
        params.put(SerialReader.PARAMS_DELAY, Integer.valueOf(equipment.getDelay())); // 端口数据准备时间 1秒
        return params;
    }

    /**
     * 通过设备信息，生成通信命令
     * @param equipment
     * @return
     */
    public  byte[] generateCommandsViaDevice(Equipment equipment) {
        String id = equipment.getId();
        int addr = Integer.parseInt(equipment.getAddr().trim());
        if (id.startsWith("AC")) {
            AC iec = new AC();
            if (!(addr > 0xf)) {
                iec.setADRHi((byte) 0x30);
                iec.setADRLo(Integer.toHexString(addr).getBytes()[0]);
            } else {
                iec.setADRHi(Integer.toHexString(addr / 16).getBytes()[0]);
                iec.setADRLo(Integer.toHexString(addr % 16).getBytes()[0]);
            }
            iec.parseCheksum();
            return iec.produceCmdBytes();
        } else if (id.startsWith("DC")) {
            DC mod = new DC();
            mod.setAddr((byte) addr);
            mod.setFunction((byte) 0x03);
            mod.setMemeryHi((byte) 0x02);
            mod.setMemeryLo((byte) 0x0E);
            mod.setCountHi((byte) 0x00);
            mod.setCountLo((byte) 0x20);
            mod.parseCRC();
            return mod.produceCmdBytes();
        }
        return null;
    }
}
