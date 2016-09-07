package com.ex.qi.entity;

/**
 * Created by sunline on 2016/8/18.
 */
public class DC {
    private byte addr;
    private byte function;
    private byte memeryHi;
    private byte memeryLo;
    private byte countHi;
    private byte countLo;
    private byte crcHi;
    private byte crcLo;

    public DC() {
    }

    public DC(byte addr, byte function, byte memeryHi, byte memeryLo, byte countHi, byte countLo) {
        this.addr = addr;
        this.function = function;
        this.memeryHi = memeryHi;
        this.memeryLo = memeryLo;
        this.countHi = countHi;
        this.countLo = countLo;
    }

    public void setCrcHi(byte crcHi) {
        this.crcHi = crcHi;
    }

    public void setCrcLo(byte crcLo) {
        this.crcLo = crcLo;
    }

    public byte getAddr() {
        return addr;
    }

    public byte getFunction() {
        return function;
    }

    public byte getMemeryHi() {
        return memeryHi;
    }

    public byte getMemeryLo() {
        return memeryLo;
    }

    public byte getCountHi() {
        return countHi;
    }

    public byte getCountLo() {
        return countLo;
    }

    public byte getCrcHi() {
        return crcHi;
    }

    public byte getCrcLo() {
        return crcLo;
    }

    public void setAddr(byte addr) {
        this.addr = addr;
    }

    public void setFunction(byte function) {
        this.function = function;
    }

    public void setMemeryHi(byte memeryHi) {
        this.memeryHi = memeryHi;
    }

    public void setMemeryLo(byte memeryLo) {
        this.memeryLo = memeryLo;
    }

    public void setCountHi(byte countHi) {
        this.countHi = countHi;
    }

    public void setCountLo(byte countLo) {
        this.countLo = countLo;
    }

    public void parseCRC() {
        byte[] args = {addr,function,memeryHi,memeryLo,countHi,countLo};
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < args.length; i++) {
            CRC ^= ((int) args[i] & 0x000000ff);
            for (j = 0; j < 8; j++) {
                if ((CRC & 0x00000001) != 0) {
                    CRC >>= 1;
                    CRC ^= POLYNOMIAL;
                } else {
                    CRC >>= 1;
                }
            }
        }
        String crc = Integer.toHexString(CRC);
        crcLo = Byte.parseByte(crc.substring(0, crc.length() - 2), 16);
        crcHi = Byte.parseByte(crc.substring(crc.length() - 2), 16);
    }
    public byte[] produceCmdBytes(){
        return new byte[]{addr,function,memeryHi,memeryLo,countHi,countLo,crcHi,crcLo};
    }
}
