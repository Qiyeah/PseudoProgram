package com.ex.qi.dao;

import com.ex.qi.entity.RealKwh;

/**
 * Created by sunline on 2016/8/22.
 */
public interface RealKwhDao {
    boolean addRealDegree(RealKwh realKwh);

    float queryHistoryData(String foreign, int route);

}
/**
 * ResultSet queryDegrees(String startTime,String endTime);
 * <p>
 * <p>
 * float queryCurrentStartAllDegrees();
 * float queryCurrentStartDeviceDegrees();
 * <p>
 * float queryCurrentEndAllDegrees();
 * float queryCurrentEndDeviceDegrees();
 * <p>
 * float queryPastDayStartAllDegrees();
 * float queryPastDayStartDeviceDegrees();
 * <p>
 * float queryPastMonthStartAllDegrees();
 * float queryPastMonthStartDeviceDegrees();
 * <p>
 * float queryPastYearStartAllDegrees();
 * float queryPastYearStartDeviceDegrees();
 * <p>
 * float queryCurrentMonthStartAllDegrees();
 * float queryCurrentMonthStartDeviceDegrees();
 * <p>
 * float queryCurrentYearStartAllDegrees();
 * float queryCurrentYearStartDeviceDegrees();
 */