package test;

import com.ex.qi.utils.DateUtils;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunline on 2016/9/6.
 */
public class TestPoint {
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors
                .newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(new CustomTask(), 0, 3, TimeUnit.SECONDS);
    }
    private static void testPoint(){
        int hour = DateUtils.getHours();
        int day = DateUtils.getDay();
        int mon = DateUtils.getMonth();
        int year = DateUtils.getYear();
        System.out.println(hour+"--"+day+"--"+mon+"--"+year);
    }
    private static class CustomTask implements Runnable{

        @Override
        public void run() {
testPoint();
        }
    }
}
