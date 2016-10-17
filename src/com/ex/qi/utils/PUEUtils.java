package com.ex.qi.utils;

import com.ex.qi.dao.BaseKwhDao;
import com.ex.qi.dao.EquipmentDao;
import com.ex.qi.dao.EquipmentInfoDao;
import com.ex.qi.dao.daoImpl.AccumKwhDao;
import com.ex.qi.dao.daoImpl.EquipmentDaoImpl;
import com.ex.qi.dao.daoImpl.EquipmentInfoDaoImpl;
import com.ex.qi.dao.daoImpl.PresentKwhDao;
import com.ex.qi.entity.Equipment;
import com.ex.qi.entity.EquipmentInfo;
import com.ex.qi.entity.PUE;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by sunline on 2016/9/2.
 */
public class PUEUtils {
    public static String TABLE_DAYKWH = "DayKwh";
    public static String TABLE_MONTHKWH = "MonthKwh";
    public static String TABLE_YEARKWH = "YearKwh";
    public static String TABLE_ACCDAYKWH ="AccumDayKwh";
    public static String TABLE_ACCMONKWH = "AccumMonthKwh";
    public static String TABLE_ACCYEARKWH = "AccumYearKwh";
    /**
     * 监测总线电路（总耗电）
     */
    public static final int TOTAL = 0x01;
    /**
     * 监测IT设备电路（IT耗电）
     */
    public static final int IT = 0x02;
    Map<String, EquipmentInfo[]> map = null;
    public PUEUtils() {
        map = loadInfos();
    }

    public Map<String, EquipmentInfo[]> loadInfos() {
        Map<String, EquipmentInfo[]> map = new HashMap<String, EquipmentInfo[]>();

        EquipmentDao dao = new EquipmentDaoImpl();

        Equipment[] equipments = dao.findAllEquipments();

        EquipmentInfoDao infoDao = new EquipmentInfoDaoImpl();

        for (Equipment equipment : equipments) {

            String id = equipment.getId();

            EquipmentInfo[] infos = infoDao.findEquipmentInfos(id);

            if (null != infos && 0 != infos.length) {

                map.put(id, infos);

            }
        }
        return map;
    }

    public float calculatePue(String table) {

        int startNum = -1;
        int stopNum = -1;

        BaseKwhDao dao = null;

        float totalStart = 0f;
        float ITStart = 0f;
        float totalEnd = 0f;
        float ITEnd = 0f;

        float tempStart = 0f;
        float tempEnd = 0f;


        if (table.equals(TABLE_DAYKWH) || table.equals(TABLE_MONTHKWH)||table.equals(TABLE_YEARKWH)) {
            startNum = 0;
            stopNum = 1;
            dao = new PresentKwhDao();
        } else if (table.equals(TABLE_ACCDAYKWH) ||table.equals(TABLE_ACCMONKWH) ||table.equals(TABLE_ACCYEARKWH) ) {
            dao = new AccumKwhDao();
        }

        Set<Map.Entry<String, EquipmentInfo[]>> entrySet = map.entrySet();

        Iterator<Map.Entry<String, EquipmentInfo[]>> it = entrySet.iterator();

        while (it.hasNext()) {

            Map.Entry<String, EquipmentInfo[]> entry = it.next();

            String key = entry.getKey();
            stopNum = dao.findLastNum(table, key);
            if (-1!=stopNum){
                if (table.equals(TABLE_ACCDAYKWH)) {
                    startNum = stopNum - 23;
                } else if (table.equals(TABLE_ACCMONKWH)) {
                    startNum = stopNum - 29;
                } else if (table.equals(TABLE_ACCYEARKWH)) {
                    startNum = stopNum - 364;
                }
            }
            EquipmentInfo[] infos = entry.getValue();

            //System.out.println(infos.length);
            for (EquipmentInfo info : infos) {
                int totalSymbol = info.getTotalSymbol();
                float totalPer = info.getTotalPer() / 100.0f;
                int itSymbol = info.getITSymbol();
                float itPer = info.getITPer() / 100.0f;
                tempStart = dao.getDegreeByNum(table, key, info.getRoute(), startNum);
                tempEnd = dao.getDegreeByNum(table, key,info.getRoute(), stopNum);
                if (0 != totalPer){
                    totalStart += totalSymbol * tempStart * totalPer;
                    totalEnd += totalSymbol * tempEnd * totalPer;
                }
                if (0 != itPer){
                    ITStart += itSymbol * tempStart * itPer;
                    ITEnd += itSymbol * tempEnd * itPer;
                }
            }
        }
        float pue = 0f;
        if (0 < (ITEnd - ITStart)) {
            pue = (totalEnd - totalStart) / (ITEnd - ITStart);
            if (pue > 3.5) {
                return 3.5f;
            } else if (pue < 1) {
                return 1f;
            }
        }
        DecimalFormat df = new DecimalFormat("#.00");
        pue = Float.valueOf(df.format(pue));

        return pue;
    }
    public PUE getPUE(){
        return new PUE(
                calculatePue(TABLE_DAYKWH),
                calculatePue(TABLE_MONTHKWH),
                calculatePue(TABLE_YEARKWH),
                calculatePue(TABLE_ACCDAYKWH),
                calculatePue(TABLE_ACCMONKWH),
                calculatePue(TABLE_ACCYEARKWH)
                );
    }
}
