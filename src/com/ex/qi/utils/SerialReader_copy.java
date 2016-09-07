package com.ex.qi.utils;

import com.ex.qi.observer.SerialObserver;
import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created by sunline on 2016/7/20.
 */
public class SerialReader_copy extends Observable implements Runnable, SerialPortEventListener {
    // 端口读入数据事件触发后,等待n毫秒后再读取,以便让数据一次性读完
    public static final String PARAMS_DELAY = "delay read"; // 延时等待端口数据准备的时间
    public static final String PARAMS_TIMEOUT = "timeout"; // 超时时间
    public static final String PARAMS_PORT = "port name"; // 端口名称
    public static final String PARAMS_DATABITS = "data bits"; // 数据位
    public static final String PARAMS_STOPBITS = "stop bits"; // 停止位
    public static final String PARAMS_PARITY = "parity"; // 奇偶校验
    public static final String PARAMS_RATE = "rate"; // 波特率
    private static byte[] readBuffer = new byte[1024]; // 4k的buffer空间,缓存串口读入的数据
    int delayRead = 100;
    boolean isOpen = false;
    int numBytes; // buffer中的实际数据字节数
    InputStream inputStream;
    OutputStream outputStream;
    static SerialPort serialPort;
    HashMap serialParams;
    static CommPortIdentifier portId;

    public void open(HashMap params) {
        serialParams = params;

        if (!isOpen) {
            // 参数初始化
            int timeout = Integer.parseInt(serialParams.get(PARAMS_TIMEOUT)
                    .toString());
            int rate = Integer.parseInt(serialParams.get(PARAMS_RATE)
                    .toString());
            int dataBits = Integer.parseInt(serialParams.get(PARAMS_DATABITS)
                    .toString());
            int stopBits = Integer.parseInt(serialParams.get(PARAMS_STOPBITS)
                    .toString());
            int parity = Integer.parseInt(serialParams.get(PARAMS_PARITY)
                    .toString());
            delayRead = Integer.parseInt(serialParams.get(PARAMS_DELAY)
                    .toString());
            String port = serialParams.get(PARAMS_PORT).toString();
            try {
                // 打开端口
                portId = CommPortIdentifier.getPortIdentifier(port);
                serialPort = (SerialPort) portId.open("SerialReader", timeout);
                inputStream = serialPort.getInputStream();
                serialPort.notifyOnDataAvailable(true);
                serialPort.addEventListener(this);
                serialPort.setSerialPortParams(rate, dataBits, stopBits, parity);
                isOpen = true;
            } catch (NoSuchPortException e) {
                e.printStackTrace();
            } catch (PortInUseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TooManyListenersException e) {
                e.printStackTrace();
            } catch (UnsupportedCommOperationException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
        }
    }

    public void start() {
        try {
            outputStream = serialPort.getOutputStream();
            //System.out.println(serialPort.getName()+"    "+serialPort.getBaudRate());
        } catch (IOException e) {
            //e.printStackTrace();
        }
        try {
            Thread readThread = new Thread(this);
            readThread.start();
        } catch (Exception e) {

        }
    }  //start() end

    public void run(byte[] message) {
        try {
            Thread.sleep(4);
        } catch (InterruptedException e) {
        }
        try {
            if (message != null && message.length != 0) {
                //TODO 发送指令
                outputStream.write(message);
                outputStream.flush();
                //TODO 数据回转时间
                Thread.sleep(1000 * 1);
            }
        } catch (IOException e) {

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        if (isOpen) {
            try {
                serialPort.notifyOnDataAvailable(false);
                serialPort.removeEventListener();
                inputStream.close();
                serialPort.close();
                isOpen = false;
            } catch (IOException ex) {
                //"关闭串口失败";
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
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                    }

                    //打印接收到的字节数据的ASCII码
                    /*for (int i = 0; i < numBytes; i++) {
                         System.out.println("msg[" + numBytes + "]: [" +readBuffer[i] + "]:"+(char)readBuffer[i]);
                    }*/
//                    numBytes = inputStream.read( readBuffer );
                    changeMessage(readBuffer, numBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // 通过observer pattern将收到的数据发送给observer
    // 将buffer中的空字节删除后再发送更新消息,通知观察者
    public void changeMessage(byte[] message, int length) {
        setChanged();
        byte[] temp = new byte[length];
        System.arraycopy(message, 0, temp, 0, length);
        //TODO 通知并传值给Observer。
        notifyObservers(temp);
    }
    public void openSerialPort(byte[] message) {
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        //otherDAO odao=new otherDAO();
        String port = "COM4";//TODO 设置端口名称
        String rate = "19200";//TODO 设置波特率
        String dataBit = "" + SerialPort.DATABITS_8;
        String stopBit = "" + SerialPort.STOPBITS_1;
        String parity = "" + SerialPort.PARITY_NONE;
        int parityInt = SerialPort.PARITY_NONE;
        params.put(SerialReader_copy.PARAMS_PORT, port); // 端口名称
        params.put(SerialReader_copy.PARAMS_RATE, rate); // 波特率
        params.put(SerialReader_copy.PARAMS_DATABITS, dataBit); // 数据位
        params.put(SerialReader_copy.PARAMS_STOPBITS, stopBit); // 停止位
        params.put(SerialReader_copy.PARAMS_PARITY, parityInt); // 无奇偶校验
        params.put(SerialReader_copy.PARAMS_TIMEOUT, 100); // 设备超时时间 1秒
        params.put(SerialReader_copy.PARAMS_DELAY, 100); // 端口数据准备时间 1秒
        try {
            open(params);//打开串口
            //TODO 新建Observer对象
            SerialObserver cf = new SerialObserver();
            addObserver(cf);
            if (message != null && message.length != 0) {
                start();
                run(message);
            }
        } catch (Exception e) {
            //e.printStackTrace();
        }
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
}
