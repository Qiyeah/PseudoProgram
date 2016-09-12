package com.ex.qi.utils;

import com.ex.qi.dao.daoImpl.DeviceDaoImpl;
import com.ex.qi.entity.AC;
import com.ex.qi.entity.DC;
import com.ex.qi.entity.Device;
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
    public List<Device> loadDevice() {
        ResultSet mResultSet;
        mResultSet = new DeviceDaoImpl().queryAllDevice();
        List<Device> devices = new ArrayList<Device>();
        try {
            while (mResultSet.next()) {
                String id = mResultSet.getString("_id").trim();
                String name = mResultSet.getString("name").trim();
                String port = mResultSet.getString("port").trim();
                String rate = mResultSet.getString("rate").trim();
                String addr = mResultSet.getString("addr").trim();
                String timeout = mResultSet.getString("timeout").trim();
                String data = mResultSet.getString("data").trim();
                String stop = mResultSet.getString("stop").trim();
                String parity = mResultSet.getString("parity").trim();
                String state = mResultSet.getString("switch").trim();
                String delay = mResultSet.getString("delayed").trim();
                devices.add(new Device(id, name, port, rate, addr, timeout, data, stop, parity, state, delay));
            }
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return devices;
    }

    /**
     * 通过设备信息，生成串口通信参数
     * @param device
     * @return
     */
    public Map<String, Comparable> parseToParams(Device device) {
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        params.put(SerialReader.PARAMS_PORT, device.getPort()); // 端口名称
        params.put(SerialReader.PARAMS_RATE, Integer.valueOf(device.getRate())); // 波特率
        params.put(SerialReader.PARAMS_DATABITS, Integer.valueOf(device.getDataBits())); // 数据位
        params.put(SerialReader.PARAMS_STOPBITS, Integer.valueOf(device.getStopBits())); // 停止位
        params.put(SerialReader.PARAMS_PARITY, Integer.valueOf(device.getParity())); // 无奇偶校验
        params.put(SerialReader.PARAMS_TIMEOUT, Integer.valueOf(device.getTimeOut())); // 设备超时时间 1秒
        params.put(SerialReader.PARAMS_DELAY, Integer.valueOf(device.getDelay())); // 端口数据准备时间 1秒
        return params;
    }

    /**
     * 通过设备信息，生成通信命令
     * @param device
     * @return
     */
    public  byte[] generateCommandsViaDevice(Device device) {
        String id = device.getId();
        int addr = Integer.parseInt(device.getAddr().trim());
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
