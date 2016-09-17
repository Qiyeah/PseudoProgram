package com.ex.qi.utils;

import com.ex.qi.dao.BaseKwhDao;
import com.ex.qi.dao.EquipmentInfoDao;
import com.ex.qi.dao.daoImpl.AccumKwhDao;
import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.dao.daoImpl.PresentKwhDao;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;

import java.util.List;

/**
 * Created by sunline on 2016/9/2.
 */
public class PUEUtils {
    public static final int DAY_PUE = 0x10;
    public static final int MONTH_PUE = 0x11;
    public static final int YEAR_PUE = 0x12;
    public static final int ACCUMDAY_PUE = 0x13;
    public static final int ACCUMMONTH_PUE = 0x14;
    public static final int ACCUMYEAR_PUE = 0x15;
    /**
     * 监测总线电路（总耗电）
     */
    public static final int TOTAL = 0x01;
    /**
     * 监测IT设备电路（IT耗电）
     */
    public static final int IT = 0x02;

    /**
     * 通过PUE类型计算出PUE
     * @param type
     * @return
     */
    public float calculatePue(int type) {
        int startNum = -1;
        int stopNum = -1;
        BaseKwhDao dao = null;
        if (DAY_PUE == type ||MONTH_PUE == type||YEAR_PUE == type){
            dao = new PresentKwhDao();
            startNum = 0;
            stopNum = 1;
        }else  if (ACCUMDAY_PUE == type||ACCUMMONTH_PUE == type||ACCUMYEAR_PUE == type){
            dao = new AccumKwhDao();
        }
        EquipmentInfoDao infoDao = new EquipmentInfoDaoImpl();
        List<Equipment> equipments = new DeviceUtils().loadDevice();
        int size = equipments.size();
        float totalStart = 0f;
        float ITStart = 0f;
        float totalEnd = 0f;
        float ITEnd = 0f;
        float tempStart = 0f;
        float tempEnd = 0f;
        /**
         * 遍历所有设备
         */

        for (int i = 0; i < size; i++) {
            Equipment d = equipments.get(i);
            String id = d.getId();
            List<EquipmentInfo> infos = infoDao.findConfigByForeign(d.getId());
           /* System.out.println("id = "+id);
            System.out.println();*/
            /**
             * 遍历该设备的配置信息
             */

            for (int j = 0; j < infos.size(); j++) {
                EquipmentInfo info = infos.get(j);
                int route = info.getRoute();
                int attr = info.getPathAttr();
                int per = info.getPer() / 100;
                int symbol = info.getSymbol();
             /*   System.out.println("route = "+route);
                System.out.println("attr = "+attr);
                System.out.println("per = "+per);
                System.out.println("symbol = "+symbol);
                System.out.println();*/
                /**
                 * 通过查询不同的表，得到计算PUE时的开始与结束电度值
                 */
                String table = "";
                if (DAY_PUE == type ){
                    table = BaseKwhDao.KWH_DAY;

                }else if (MONTH_PUE == type){
                    table = BaseKwhDao.KWH_MONTH;
                }else if (YEAR_PUE == type){
                    table = BaseKwhDao.KWH_YEAR;
                }else  if (ACCUMDAY_PUE == type){
                    table = BaseKwhDao.KWH_ACCUM_DAY;
                    stopNum = dao.findLastNum(table,id,route);
                    startNum = stopNum - 23;
                }else  if (ACCUMMONTH_PUE == type){
                    table = BaseKwhDao.KWH_ACCUM_MONTH;
                    stopNum = dao.findLastNum(table,id,route);
                    startNum = stopNum - 29;
                }else  if (ACCUMYEAR_PUE == type){
                    table = BaseKwhDao.KWH_ACCUM_YEAR;
                    stopNum = dao.findLastNum(table,id,route);
                    startNum = stopNum - 364;
                }
                tempStart = dao.getDegreeByNum(table, id, route,startNum);
                tempEnd =dao.getDegreeByNum(table, id, route,stopNum);
               /* System.out.println("tempStart = "+tempStart);
                System.out.println("tempEnd = "+tempEnd);
                System.out.println();*/
                /**
                 * 计算totalStart,totalEnd,ITStart,ITEnd
                 */
                if (TOTAL == attr) {
                    totalStart += symbol * tempStart * per;
                    totalEnd += symbol * tempEnd * per;
                } else if (IT == attr) {
                    ITStart += symbol * tempStart * per;
                    totalStart += symbol * tempStart * per;
                    ITEnd += symbol * tempEnd * per;
                    totalEnd += symbol * tempEnd * per;
                }
            }
        }
       /* System.out.println();
        System.out.println("totalEnd = "+totalEnd);
        System.out.println("totalStart = "+totalStart);
        System.out.println("ITEnd = "+ITEnd);
        System.out.println("ITStart = "+ITStart);*/
        float pue = (totalEnd - totalStart) / (ITEnd - ITStart);

        if (pue > 3.5){
            pue = 3.5f;
        }else if (pue < 1 ){
            pue = 1f;
        }

        return pue;
    }
}
