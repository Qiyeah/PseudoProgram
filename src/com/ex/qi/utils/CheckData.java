package com.ex.qi.utils;


import java.io.UnsupportedEncodingException;
import java.util.Arrays;

/**
 * Created by YJJ on 2016/9/26.
 * 校验采集的数据
 */
public class CheckData  {
    StringBuffer s =new StringBuffer();
    String s1="AC table checksum check success";
    String s2="AC table checksum check failed";
    String s3="AC the version or address error";
    String s4="AC the length error";
    String s5="DC table CRC check success";
    String s6="DC table CRC check failed";
    String s7="DC the length error";
    String s8="DC the function code or data length error ";
    String s9="the data is wrong ";


    public  boolean checkData(int[] data) throws UnsupportedEncodingException {
        LogUtils log =new LogUtils();
        if(data[0]==126&&data[data.length-1]==13){//数据头尾，此为AC表
            //System.out.println("data.length="+data.length);
            if (data.length==2264){//判断长度
                if(data[1]==50&&data[2]==49 ){//版本号和地址
                    if (checksum(data)){//算校验码
                      //  s.append(new String(s1.getBytes(),"GBK"));
                      //  log.writeLog("check.log",s);
                        return true;
                    } else {
                        s.append(new String(s2.getBytes(),"GBK"));
                        log.writeLog("check.log",s);
                        return false;
                    }//
                }else {
                    s.append(new String(s3.getBytes(),"GBK"));
                    log.writeLog("check.log",s);
                    return false;
                }//else//否则不对，重发
             }else{
                s.append(new String(s4.getBytes(),"GBK"));
                log.writeLog("check.log",s);
                return false;
            }//else//否则不对，重发
        }else if(data[0]==1){//否则判断是否是DC表
            if (data[1]==3 && data[2]== 64){//功能码和数据长度
              //  System.out.println();
                if (data.length==(3+64+2)){//判断信息长度
                    if (CRCcheck(data)){
                     //   s.append(new String(s5.getBytes(),"GBK"));
                      //  log.writeLog("check.log",s);
                        return  true;
                    }else{
                        s.append(new String(s6.getBytes(),"GBK"));
                        log.writeLog("check.log",s);
                        return false;
                    }
                }else{
                    s.append(new String(s7.getBytes(),"GBK"));
                    log.writeLog("check.log",s);
                    return false;
                }
            }else{
                s.append(new String(s8.getBytes(),"GBK"));
                log.writeLog("check.log",s);
                return false;
            }
        }else {
            s.append(new String(s9.getBytes(),"GBK"));
            log.writeLog("check.log",s);
            return false;
        }

    }

    /*AC表验证校验码checksum
    *返回布尔值
    *
    * */
    public boolean checksum(int[] data){
      //  System.out.println("data.length="+data.length);
        int[]  a = Arrays.copyOfRange(data, 1, data.length - 5);
        int[]  b = Arrays.copyOfRange(data,data.length-5,data.length-1);
        String str ="";
        for (int i = 0; i <b.length ; i++) {
            str=str+(char)b[i];
          //  System.out.println("校验码="+str);
        }
        int sum =0;
        for(int i=0;i<a.length;i++){
            sum+=a[i];
        }
      //  System.out.println("a.length="+a.length);
        String check = Long.toHexString((~sum % 65536 + 1) & 0xFFFF).toUpperCase();
      //  System.out.println("计算解得验证和="+check);
        if (check.equalsIgnoreCase(str)){
            return true;
        }else return false;
    }


    /*DC表验证校验CRC
  *返回布尔值
  * */
    public boolean CRCcheck(int[] data){
        int[] cut = Arrays.copyOfRange(data,0,data.length-2);
        int[]  arry=new int[cut.length];
        for (int i = 0; i <cut.length ; i++) {
            String s =Integer.toHexString(cut[i]);
            arry[i]=Integer.valueOf(s,16);
        }
        int CRC = 0x0000ffff;
        int POLYNOMIAL = 0x0000a001;
        int i, j;
        for (i = 0; i < cut.length; i++) {
            CRC ^= ((int) cut[i] & 0x000000ff);
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
        int crcLo = Integer.parseInt(crc.substring(0, crc.length() - 2), 16);
        int crcHi = Integer.parseInt(crc.substring(crc.length() - 2), 16);
        if (crcLo==data[data.length-1]&&crcHi==data[data.length-2]){
            return true;
        }else return false;
    }
}
