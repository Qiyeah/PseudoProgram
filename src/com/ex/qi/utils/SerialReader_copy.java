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
    // �˿ڶ��������¼�������,�ȴ�n������ٶ�ȡ,�Ա�������һ���Զ���
    public static final String PARAMS_DELAY = "delay read"; // ��ʱ�ȴ��˿�����׼����ʱ��
    public static final String PARAMS_TIMEOUT = "timeout"; // ��ʱʱ��
    public static final String PARAMS_PORT = "port name"; // �˿�����
    public static final String PARAMS_DATABITS = "data bits"; // ����λ
    public static final String PARAMS_STOPBITS = "stop bits"; // ֹͣλ
    public static final String PARAMS_PARITY = "parity"; // ��żУ��
    public static final String PARAMS_RATE = "rate"; // ������
    private static byte[] readBuffer = new byte[1024]; // 4k��buffer�ռ�,���洮�ڶ��������
    int delayRead = 100;
    boolean isOpen = false;
    int numBytes; // buffer�е�ʵ�������ֽ���
    InputStream inputStream;
    OutputStream outputStream;
    static SerialPort serialPort;
    HashMap serialParams;
    static CommPortIdentifier portId;

    public void open(HashMap params) {
        serialParams = params;

        if (!isOpen) {
            // ������ʼ��
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
                // �򿪶˿�
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
                //TODO ����ָ��
                outputStream.write(message);
                outputStream.flush();
                //TODO ���ݻ�תʱ��
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
                //"�رմ���ʧ��";
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

                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);
                    }
                    changeMessage(readBuffer, numBytes);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    // ͨ��observer pattern���յ������ݷ��͸�observer
    // ��buffer�еĿ��ֽ�ɾ�����ٷ��͸�����Ϣ,֪ͨ�۲���
    public void changeMessage(byte[] message, int length) {
        setChanged();
        byte[] temp = new byte[length];
        System.arraycopy(message, 0, temp, 0, length);
        //TODO ֪ͨ����ֵ��Observer��
        notifyObservers(temp);
    }
    public void openSerialPort(byte[] message) {
        HashMap<String, Comparable> params = new HashMap<String, Comparable>();
        //otherDAO odao=new otherDAO();
        String port = "COM4";//TODO ���ö˿�����
        String rate = "19200";//TODO ���ò�����
        String dataBit = "" + SerialPort.DATABITS_8;
        String stopBit = "" + SerialPort.STOPBITS_1;
        String parity = "" + SerialPort.PARITY_NONE;
        int parityInt = SerialPort.PARITY_NONE;
        params.put(SerialReader_copy.PARAMS_PORT, port); // �˿�����
        params.put(SerialReader_copy.PARAMS_RATE, rate); // ������
        params.put(SerialReader_copy.PARAMS_DATABITS, dataBit); // ����λ
        params.put(SerialReader_copy.PARAMS_STOPBITS, stopBit); // ֹͣλ
        params.put(SerialReader_copy.PARAMS_PARITY, parityInt); // ����żУ��
        params.put(SerialReader_copy.PARAMS_TIMEOUT, 100); // �豸��ʱʱ�� 1��
        params.put(SerialReader_copy.PARAMS_DELAY, 100); // �˿�����׼��ʱ�� 1��
        try {
            open(params);//�򿪴���
            //TODO �½�Observer����
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


    public HashSet<CommPortIdentifier> getAvailableSerialPorts()//����static
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
