package test;

import com.ex.qi.utils.PUEUtils;

/**
 * Created by sunline on 2016/8/28.
 */
public class TestPueUtils {
    public static void main(String[] args) {
        PUEUtils utils = new PUEUtils();
        float pue = utils.calculatePue(PUEUtils.DAY_PUE);
        System.out.println(pue);
        pue = utils.calculatePue(PUEUtils.MONTH_PUE);
        System.out.println(pue);
        pue = utils.calculatePue(PUEUtils.YEAR_PUE);
        System.out.println(pue);
        pue = utils.calculatePue(PUEUtils.ACCUMDAY_PUE);
        System.out.println(pue);
        pue = utils.calculatePue(PUEUtils.ACCUMMONTH_PUE);
        System.out.println(pue);
        pue = utils.calculatePue(PUEUtils.ACCUMYEAR_PUE);
        System.out.println(pue);
    }
}