package test;

import com.ex.qi.entity.Device;
import com.ex.qi.entity.DC;
import com.ex.qi.utils.DeviceUtils;
import com.ex.qi.utils.SerialPortUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by sunline on 2016/8/24.
 */
public class Test {
    private boolean isOpen = false;
    public static final int MOD_TIMEOUT = 1;

    public static void main(String[] args) {
        Test test = new Test();
        test.collectDataByConfig();
    }
    private byte[] generateMODMessage(Device device) {
        DC mod = new DC();
        int addr = Integer.parseInt(device.getAddr());
        mod.setAddr((byte) addr);
        mod.setFunction((byte) 0x03);
        mod.setMemeryHi((byte) 0x02);
        mod.setMemeryLo((byte) 0x0E);
        mod.setCountHi((byte) 0x00);
        mod.setCountLo((byte) 0x20);
        mod.parseCRC();
        return mod.produceCmdBytes();
    }
    private void collectDataByConfig() {
        /**
         * �������������ļ��Ĺ���,���������ñ�
         */
        DeviceUtils utils = new DeviceUtils();
        List<Device> devices = utils.loadDevice();
        /**
         * �������ñ�
         */
        for (int i = 0; i < devices.size(); i++) {
            Device device = devices.get(i);
            /**
             * ͨ�����ù��߽����豸ͨѶ����
             */
            Map<String,Comparable> params = utils.parseToParams(device);
            /**
             * ���ڸ�������,�򿪴��ڡ�������������豸���ݲ��������رմ���
             */
            SerialPortUtils serialPortUtils = new SerialPortUtils();
            serialPortUtils.open(params);//�򿪴���
            isOpen = true;
           /* if (device.getId().startsWith("IEC")){
                serialPortUtils.sendMessage(generateIECMessage(device), AC_TIMEOUT);//��������
                float[] values = serialPortUtils.parseACDegrees();//�����豸���ݲ�����
                if (null != values &&values.length>0 ){
                    for (int j = 0; j < values.length; j++) {
                        System.out.println(values[j]);
                    }
                }
            }else */
            System.out.println(device.getId());
            if (device.getId().startsWith("MOD")){
                System.out.println(null == serialPortUtils);
                serialPortUtils.sendMessage(generateMODMessage(device), MOD_TIMEOUT);//��������
                float[] values = serialPortUtils.parseDCDegrees();//�����豸���ݲ�����
                if (null != values &&values.length>0 ){
                    for (int j = 0; j < values.length; j++) {
                        System.out.println(values[j]);
                    }
                }
            }

            serialPortUtils.close();//�رմ���
        }
    }
}
