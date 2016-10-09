package test;

import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.utils.PUEUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunline on 2016/8/28.
 */
public class TestPueUtils {
    public static void main(String[] args) {
        for (int i = 0; i < 1; i++) {
            System.out.println("第 【 "+(i+1)+" 】 次计算的PUE值");
            System.out.println("--***************************************************--");
            printPue();
        }
    }

    private static void printPue() {
        PUEUtils utils = new PUEUtils();
        float pue = utils.calculatePue(PUEUtils.TABLE_DAYKWH);
        System.out.println("当日PUE:" + pue);
        pue = utils.calculatePue(PUEUtils.TABLE_MONTHKWH);
        System.out.println("当月PUE:" + pue);
        pue = utils.calculatePue(PUEUtils.TABLE_YEARKWH);
        System.out.println("当年PUE:" + pue + "\n");
        pue = utils.calculatePue(PUEUtils.TABLE_ACCDAYKWH);
        System.out.println("小时累计PUE:" + pue);
        pue = utils.calculatePue(PUEUtils.TABLE_ACCMONKWH);
        System.out.println("30天累计PUE:" + pue);
        pue = utils.calculatePue(PUEUtils.TABLE_ACCYEARKWH);
        System.out.println("365累计PUE:" + pue + "\n");
    }
}
