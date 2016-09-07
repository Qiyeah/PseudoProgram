package test;

/**
 * Created by sunline on 2016/8/23.
 */
public class TestArray {
    public static void main(String[] args) {
        /*byte[] arr1 = new byte[0];

        byte[] tmep = {1,2,3,4,5};
        for (int i = 0; i < 2; i++) {
           arr1 = copyArray(arr1,tmep);
        }
        for (byte b : arr1) {
            System.out.print(b+" ");
        }*/
       /* byte a  = 0x44;
        System.out.println(byte2Hex(a));
        byte[] bytes = {0x32,0x30,0x30,0x31,0x34,0x30
                ,0x34
                ,0x33
                ,0x45
                ,0x30
                ,0x30
                ,0x32
                ,0x30
                ,0x30
        };
        int sum = 0;
        for (byte b : bytes) {
            sum+=b;
        }*/
      /*  System.out.println(Float.intBitsToFloat(0x43f9599a));
        System.out.println(Integer.toHexString((~(451%65536)+1)&0xffff).toUpperCase());*/
        byte a = 67;
        byte b = 9;
        for (int i = 48;i < 128; i++) {
            //System.out.println("i = "+i+" "+(i/48)+""+(i%48));
            if (i<58){
                System.out.println("i = "+i+">"+Integer.toHexString(i%0x30));
            }else if (i>=65 && i<=70){
                System.out.println("i = "+i+">"+(char)i);
                //System.out.println("i = "+i+">"+Integer.toHexString(Integer.valueOf(Integer.toHexString((i%0x30)))));
            }
        }
    }
    public static byte[] copyArray(byte[] target,byte[] source){
        byte[] temp  = new byte[target.length+source.length];
        for (int i = 0; i < target.length; i++) {
            temp[i] = target[i];
        }
        for (int i = 0; i < source.length; i++) {
            temp[i+target.length] = source[i];
        }
        return temp;
    }
    public static byte byte2Hex(byte b){
        byte bHi = (byte) ((byte) (b & 0xf0)/16);
        byte bLo = (byte) (b & 0x0f);
        return Byte.valueOf(Integer.toHexString(b));
    }
 }
