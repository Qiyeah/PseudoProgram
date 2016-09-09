package test;

import com.ex.qi.utils.PUEUtils;

/**
 * Created by sunline on 2016/8/28.
 */
public class TestPueUtils {
    public static void main(String[] args) {
        for (int i = 0; i < 1000 * 50; i++) {
            System.out.println("第 【 "+i+" 】 次计算的PUE值");
            System.out.println("--***************************************************--");
            printPue();
        }
    }
    private static void printPue(){
        PUEUtils utils = new PUEUtils();
        float pue = utils.calculatePue(PUEUtils.DAY_PUE);
        System.out.println("\t   当日PUE:"+pue);
        pue = utils.calculatePue(PUEUtils.MONTH_PUE);
        System.out.println("\t   当月PUE:"+pue);
        pue = utils.calculatePue(PUEUtils.YEAR_PUE);
        System.out.println("\t   当年PUE:"+pue+"\n");
        pue = utils.calculatePue(PUEUtils.ACCUMDAY_PUE);
        System.out.println("21小时累计PUE:"+pue);
        pue = utils.calculatePue(PUEUtils.ACCUMMONTH_PUE);
        System.out.println("  30天累计PUE:"+pue);
        pue = utils.calculatePue(PUEUtils.ACCUMYEAR_PUE);
        System.out.println("   365累计PUE:"+pue+"\n");
    }
}
