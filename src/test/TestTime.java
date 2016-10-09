package test;

import java.sql.Date;

/**
 * Created by sunlines on 2016/10/8.
 */
public class TestTime {
    public static void main(String[] args) {
       Date date = new Date(System.currentTimeMillis()+3600*1000);
        System.out.println(new Date(System.currentTimeMillis()+3600*1000));
    }
}
