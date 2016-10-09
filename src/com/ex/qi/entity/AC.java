package com.ex.qi.entity;

/**
 * Created by sunline on 2016/8/18.
 */
public class AC {
    private byte SOI = (byte)0x7E;
    private byte VERHi = (byte)0x32;
    private byte VERLo = (byte)0x31;
    private byte ADRHi = (byte)0x30;
    private byte ADRLo = (byte)0x31;
    private byte CIDHi = (byte)0x39;
    private byte CIDLo = (byte)0x30;
    private byte RTNHi = (byte)0x38;
    private byte RTNLo = (byte)0x30;
    private byte LEN1Hi = (byte)0x30;
    private byte LEN1Lo = (byte)0x30;
    private byte LEN2Hi = (byte)0x30;
    private byte LEN2Lo = (byte)0x30;
    private byte CHE1Hi;
    private byte CHE1Lo ;
    private byte CHE2Hi;
    private byte CHE2Lo;
    private byte EOI = (byte)0x0D;

    public AC() {
    }

    public AC(byte VERHi, byte VERLo, byte ADRHi, byte ADRLo, byte CIDHi, byte CIDLo, byte RTNHi,
              byte RTNLo, byte LEN1Hi, byte LEN1Lo, byte LEN2Hi, byte LEN2Lo) {
        this.VERHi = VERHi;
        this.VERLo = VERLo;
        this.ADRHi = ADRHi;
        this.ADRLo = ADRLo;
        this.CIDHi = CIDHi;
        this.CIDLo = CIDLo;
        this.RTNHi = RTNHi;
        this.RTNLo = RTNLo;
        this.LEN1Hi = LEN1Hi;
        this.LEN1Lo = LEN1Lo;
        this.LEN2Hi = LEN2Hi;
        this.LEN2Lo = LEN2Lo;
    }

    public byte getSOI() {
        return SOI;
    }

    public byte getVERHi() {
        return VERHi;
    }

    public byte getVERLo() {
        return VERLo;
    }

    public byte getADRHi() {
        return ADRHi;
    }

    public byte getADRLo() {
        return ADRLo;
    }

    public byte getCIDHi() {
        return CIDHi;
    }

    public byte getCIDLo() {
        return CIDLo;
    }

    public byte getRTNHi() {
        return RTNHi;
    }

    public byte getRTNLo() {
        return RTNLo;
    }

    public byte getLEN1Hi() {
        return LEN1Hi;
    }

    public byte getLEN1Lo() {
        return LEN1Lo;
    }

    public byte getLEN2Hi() {
        return LEN2Hi;
    }

    public byte getLEN2Lo() {
        return LEN2Lo;
    }

    public byte getCHE1Hi() {
        return CHE1Hi;
    }

    public byte getCHE1Lo() {
        return CHE1Lo;
    }

    public byte getCHE2Hi() {
        return CHE2Hi;
    }

    public byte getCHE2Lo() {
        return CHE2Lo;
    }

    public byte getEOI() {
        return EOI;
    }

    public void setVERHi(byte VERHi) {
        this.VERHi = VERHi;
    }

    public void setVERLo(byte VERLo) {
        this.VERLo = VERLo;
    }

    public void setADRHi(byte ADRHi) {
        this.ADRHi = ADRHi;
    }

    public void setADRLo(byte ADRLo) {
        this.ADRLo = ADRLo;
    }

    public void setCIDHi(byte CIDHi) {
        this.CIDHi = CIDHi;
    }

    public void setCIDLo(byte CIDLo) {
        this.CIDLo = CIDLo;
    }

    public void setRTNHi(byte RTNHi) {
        this.RTNHi = RTNHi;
    }

    public void setRTNLo(byte RTNLo) {
        this.RTNLo = RTNLo;
    }

    public void setLEN1Hi(byte LEN1Hi) {
        this.LEN1Hi = LEN1Hi;
    }

    public void setLEN1Lo(byte LEN1Lo) {
        this.LEN1Lo = LEN1Lo;
    }

    public void setLEN2Hi(byte LEN2Hi) {
        this.LEN2Hi = LEN2Hi;
    }

    public void setLEN2Lo(byte LEN2Lo) {
        this.LEN2Lo = LEN2Lo;
    }

    public void parseCheksum() {
        byte[] bytes = new byte[]{VERHi, VERLo, ADRHi, ADRLo, CIDHi, CIDLo, RTNHi, RTNLo, LEN1Hi, LEN1Lo, LEN2Hi, LEN2Lo};
        long sum = 0;
        for (int i = 0; i < bytes.length; i++) {
            sum += bytes[i];
        }

        String check = Long.toHexString((~sum % 65536 + 1) & 0xFFFF).toUpperCase();
        byte[] checksum = new byte[check.length()];
        for (int i = 0; i < check.length(); i++) {
            checksum[i] = (byte)check.charAt(i)/* Integer.parseInt(Integer.toHexString())*/;
            /*System.out.println(checksum[i]);*/
        }
        CHE1Hi = checksum[0];
        CHE1Lo = checksum[1];
        CHE2Hi = checksum[2];
        CHE2Lo = checksum[3];
    }
    public byte[] produceCmdBytes(){
        return new byte[]{SOI,VERHi,VERLo,ADRHi,ADRLo,CIDHi,CIDLo,RTNHi,RTNLo,
                LEN1Hi,LEN1Lo,LEN2Hi,LEN2Lo,CHE1Hi,CHE1Lo,CHE2Hi,CHE2Lo,EOI};
    }
    public byte byte2Hex(byte b){
        byte bHi = (byte) ((byte) (b & 0xf0)/16);
        byte bLo = (byte) (b & 0x0f);
        return Byte.valueOf(Integer.toHexString(bHi)+Integer.toString(bLo));
    }
}
