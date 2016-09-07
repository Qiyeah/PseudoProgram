package com.ex.qi.utils;

import com.ex.qi.dao.DeviceInfoDao;
import com.ex.qi.dao.daoImpl.DayKwhDaoImpl;
import com.ex.qi.dao.daoImpl.DeviceInfoDaoImpl;
import com.ex.qi.dao.daoImpl.MonthKwhDaoImpl;
import com.ex.qi.dao.daoImpl.YearKwhDaoImpl;
import com.ex.qi.entity.Device;
import com.ex.qi.entity.DeviceInfo;

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

        BaseKwhDao dao = null;
        if (DAY_PUE == type){
            dao = new DayKwhDaoImpl();
        }else  if (MONTH_PUE == type){
            dao = new MonthKwhDaoImpl();
        }else  if (YEAR_PUE == type){
            dao = new YearKwhDaoImpl();
        }/*else  if (ACCUMDAY_PUE == type){
            dao = new DayKwhDaoImpl();
        }else  if (ACCUMMONTH_PUE == type){
            dao = new DayKwhDaoImpl();
        }else  if (ACCUMYEAR_PUE == type){
            dao = new DayKwhDaoImpl();
        }*/
        DeviceInfoDao infoDao = new DeviceInfoDaoImpl();
        List<Device> devices = new DeviceUtils().loadDevice();
        int size = devices.size();
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
            Device d = devices.get(i);
            String id = d.getId();
            List<DeviceInfo> infos = infoDao.findConfigByForeign(d.getId());
            /**
             * 遍历该设备的配置信息
             */
            for (int j = 0; j < infos.size(); j++) {
                DeviceInfo info = infos.get(j);
                int route = info.getPath();
                int attr = info.getPathAttr();
                int per = info.getPer() / 100;
                int symbol = info.getSymbol();
                /**
                 * 通过查询不同的表，得到计算PUE时的开始与结束电度值
                 */
                tempStart = dao.getDegreeByInfo( id, route, 0);
                tempEnd = dao.getDegreeByInfo(id, route, 1);
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
        return (totalEnd - totalStart) / (ITEnd - ITStart);
    }
}
