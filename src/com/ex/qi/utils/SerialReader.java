package com.ex.qi.utils;

import gnu.io.*;
import test.TestAddDataToDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by sunline on 2016/7/20.
 */
public abstract class SerialReader extends Observable implements SerialPortEventListener {
    StringBuilder sb ;
    String CRLF = "\r\n";
    // 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完
    public static final String PARAMS_DELAY = "delay read"; // 延时等待端口数据准备的时间
    public static final String PARAMS_TIMEOUT = "timeout"; // 超时时间
    public static final String PARAMS_PORT = "port name"; // 端口名称
    public static final String PARAMS_DATABITS = "data bits"; // 数据位
    public static final String PARAMS_STOPBITS = "stop bits"; // 停止位
    public static final String PARAMS_PARITY = "parity"; // 奇偶校验
    public static final String PARAMS_RATE = "rate"; // 波特率
    private static byte[] bytes = new byte[1024]; // 4k的buffer空间,缓存串口读入的数据
    int delayRead = 100;

    int len; // buffer中的实际数据字节数

    InputStream inputStream = null;
    OutputStream outputStream = null;
    static SerialPort serialPort;
    Map serialParams;
    static CommPortIdentifier portId;
    private boolean isOpen;

    public SerialReader(StringBuilder sb) {
        this.sb = sb;
    }

    public SerialReader() {
    }

    public void open(Map params) {
        //TODO 打印设备通信参数
        /*try {
            Set<String> keySet = params.keySet();
            Iterator<String> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                Object value = params.get(key);
                System.out.println(key +" = "+value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        serialParams = params;
        if (!isOpen) {
            // 参数初始化
            int timeout = Integer.parseInt(serialParams.get(PARAMS_TIMEOUT).toString());
            int rate = Integer.parseInt(serialParams.get(PARAMS_RATE).toString());
            int dataBits = Integer.parseInt(serialParams.get(PARAMS_DATABITS).toString());
            int stopBits = Integer.parseInt(serialParams.get(PARAMS_STOPBITS).toString());
            int parity = Integer.parseInt(serialParams.get(PARAMS_PARITY).toString());
            delayRead = Integer.parseInt(serialParams.get(PARAMS_DELAY).toString());
            String port = serialParams.get(PARAMS_PORT).toString().trim();
//            System.out.println(port);
            try {
                // 打开端口
                portId = CommPortIdentifier.getPortIdentifier(port);
                //System.out.println(port);
                serialPort = (SerialPort) portId.open("SerialReader", timeout);
                inputStream = serialPort.getInputStream();
                outputStream = serialPort.getOutputStream();
                serialPort.notifyOnDataAvailable(true);
                serialPort.addEventListener(this);
                serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
                isOpen = true;
            }  catch (NoSuchPortException e) {
                System.out.println("SerialReader 端口未找到" + e.getMessage());
            } catch (IOException e) {
                System.out.println("SerialReader 未获取到输入或输出流" + e.getMessage());
            }catch (PortInUseException e) {
                System.out.println("SerialReader 端口被占用"+e.getMessage());
            }catch (UnsupportedCommOperationException e) {
                System.out.println("SerialReader 通信参数设置错误："+e.getMessage());
            } catch (TooManyListenersException e) {
                System.out.println("SerialReader 监听添加失败：" + e.getMessage());
            }
        }
    }
    public void run(byte[] message, int delay) {
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
        }
        try {
            if (message != null && message.length != 0) {
                //TODO 发送指令
                outputStream.write(message);
              /*  System.out.println("发送的命令：");
                for (byte b : message) {
                    System.out.print(b + " ");
                }
                System.out.println();*/
               /* sb.append(CRLF);
                sb.append(CRLF);*/
                outputStream.flush();
                //TODO 数据回转时间
                Thread.sleep(100 * delay);
                //sb.append("传递到电度解析的数据:");
                if (null != data && 0 != data.length){
                    /*System.out.println("传递到电度解析的数据:");
                    for (int i : data) {
                        System.out.print(i + " ");
                    }
                    System.out.println();*/
                    //sb.append(CRLF);
                    CheckData cd =new CheckData();
                    if (cd.checkData(data)){
                        transformData(data);
                        data =new int[0];
                    }else{
                        data =new int[0];
                        run(message,delay);
                    }
                }else {
                    System.out.println("通信故障！正在尝试重新连接...");
                    data =new int[0];
                    run(message,delay);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("SerialReader 程序中断");
        }
    }

    public void close() {
        if (isOpen) {
            try {
                inputStream.close();
                outputStream.close();
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                serialPort.close();
                isOpen = false;
            } catch (IOException ex) {
                //"关闭串口失败";
                System.out.println("关闭串口失败");
            }
        }
    }
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        switch (serialPortEvent.getEventType()) {
            case SerialPortEvent.BI: // 10
            case SerialPortEvent.OE: // 7
            case SerialPortEvent.FE: // 9
            case SerialPortEvent.PE: // 8
            case SerialPortEvent.CD: // 6
            case SerialPortEvent.CTS: // 3
            case SerialPortEvent.DSR: // 4
            case SerialPortEvent.RI: // 5
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY: // 2
                break;
            case SerialPortEvent.DATA_AVAILABLE: // 1
                try {
                    // 多次读取,将所有数据读入
                    while ( 0 < (len = inputStream.read(bytes))) {
                        onDataReceived(bytes, len);
                        //System.out.println();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private int[] data = new int[0];

    // 通过observer pattern将收到的数据发送给observer
    // 将buffer中的空字节删除后再发送更新消息,通知观察者
    public void onDataReceived(byte[] source, int length) {
        byte[] temp = Arrays.copyOfRange(source, 0, length);
        /*System.out.print("收到的数据：" + length + "个");
        for (byte b : temp) {
            if (b<0){
                System.out.print((b+256)+" ");
            }
            else
                System.out.print(b+" ");
        }
        System.out.println();*/
        data = copyArray(data, temp);
       /* System.out.println();
        System.out.print("拼接过程：");
        for (int i : data) {
            System.out.print(i+" ");
        }
        System.out.println();*/
    }

    public int[] copyArray(int[] target, byte[] source) {
        int[] temp = new int[target.length + source.length];
        for (int i = 0; i < target.length; i++) {
            temp[i] = target[i];
        }
        for (int i = 0; i < source.length; i++) {
            if (source[i]<0){
                temp[i + target.length] = source[i]+256;
            }else {
                temp[i + target.length] = source[i];
            }
        }
        return temp;
    }

    static String getPortTypeName(int portType) {
        switch (portType) {
            case CommPortIdentifier.PORT_I2C:
                return "I2C";
            case CommPortIdentifier.PORT_PARALLEL:
                return "Parallel";
            case CommPortIdentifier.PORT_RAW:
                return "Raw";
            case CommPortIdentifier.PORT_RS485:
                return "RS485";
            case CommPortIdentifier.PORT_SERIAL:
                return "Serial";
            default:
                return "unknown type";
        }
    }

    public void sendMessage(byte[] message, int delay) {
        try {
            if (message != null && message.length != 0) {
                run(message, delay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashSet<CommPortIdentifier> getAvailableSerialPorts()//本来static
    {
        HashSet<CommPortIdentifier> h = new HashSet<CommPortIdentifier>();
        Enumeration thePorts = CommPortIdentifier.getPortIdentifiers();
        while (thePorts.hasMoreElements()) {
            CommPortIdentifier com = (CommPortIdentifier) thePorts
                    .nextElement();
            switch (com.getPortType()) {
                case CommPortIdentifier.PORT_SERIAL:
                    try {
                        CommPort thePort = com.open("CommUtil", 50);
                        thePort.close();
                        h.add(com);
                    } catch (PortInUseException e) {
                        System.out.println("Port, " + com.getName()
                                + ", is in use.");
                    } catch (Exception e) {
                        System.out.println("Failed to open port "
                                + com.getName() + e);
                    }
            }
        }
        return h;
    }
    public abstract void transformData(int[] data);
    public boolean getPortState(){
        return isOpen;
    }
}
