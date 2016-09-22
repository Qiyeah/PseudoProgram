package test;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by sunline on 2016/9/22.
 */
public class TestURLDecoder {
    public static void main(String[] args) {
        String str = "%7Baaaaa%7D";
        URLDecoder decoder = new URLDecoder();
        try {
            String res= decoder.decode(str,"gbk");
            System.out.println(res);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
