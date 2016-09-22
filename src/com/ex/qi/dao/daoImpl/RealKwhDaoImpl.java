package com.ex.qi.dao.daoImpl;

import com.ex.qi.dao.BaseDaoImpl;
import com.ex.qi.dao.RealKwhDao;
import com.ex.qi.entity.RealKwh;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/25.
 */
public class RealKwhDaoImpl extends BaseDaoImpl implements RealKwhDao {
    @Override
    public boolean addRealDegree( RealKwh realKwh){
        String sql = "insert into RealKwh (id,fk,route,degree) values(?,?,?,?)";
        Object[] objs = new Object[]{realKwh.getId(), realKwh.getfId(), realKwh.getRoute(), realKwh.getDegree()};
        try {
            return update(sql, objs);
        } catch (SQLException e) {
            //e.printStackTrace();
        }
        return false;
    }

    @Override
    public float queryHistoryData(String foreign, int route){
        String sql = "select top 1 degree from RealKwh where fk = ? and route = ? order by dt desc ";
        ResultSet set = query(sql,new Object[]{foreign,route});
        try {
            while (set.next()){
                return set.getFloat("degree");
            }
        } catch (SQLException e) {
        }
        return 0f;
    }
}



/*
/* *//**
 * ���쿪ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryCurrentStartAllDegrees() {
       //ResultSet rs = queryDegrees()
        return 0;
    }

    *//**
 * ���쿪ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryCurrentStartDeviceDegrees() {
        return 0;
    }
    *//**
 * �ܽ������ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryCurrentEndAllDegrees() {
        return 0;
    }
    *//**
 * �ܽ�����IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryCurrentEndDeviceDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�쿪ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryPastDayStartAllDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�쿪ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryPastDayStartDeviceDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�¿�ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryPastMonthStartAllDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�¿�ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryPastMonthStartDeviceDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�꿪ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryPastYearStartAllDegrees() {
        return 0;
    }
    *//**
 * ��ȥһ�꿪ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryPastYearStartDeviceDegrees() {
        return 0;
    }
    *//**
 * ���¿�ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryCurrentMonthStartAllDegrees() {
        return 0;
    }
    *//**
 * ���¿�ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryCurrentMonthStartDeviceDegrees() {
        return 0;
    }
    *//**
 * ���꿪ʼ���ܵ��ֵ
 * @return
 *//*
    @Override
    public float queryCurrentYearStartAllDegrees() {
        return 0;
    }
    *//**
 * ���꿪ʼ��IT�豸���ֵ
 * @return
 *//*
    @Override
    public float queryCurrentYearStartDeviceDegrees() {
        return 0;
    }
    *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ�����ȵ��ռ��

    *//**
 * @return ��ǰ���ڿ�ʼ����pue����ʼʱ��
 *//*
    public Date startTime_Day_Began() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    *//**
 * @return ��ǰ���ڿ�ʼ����pue������ʱ��
 *//*
    public Date endTime_Day_Began() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        cal.set(Calendar.DAY_OF_YEAR, day);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    *//**
 * @return ��ǰ����ֹͣ����pue����ʼʱ��
 *//*
    public Date startTime_Day_Stop() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.HOUR_OF_DAY, -1);
        return cal.getTime();
    }

    *//**
 * @return ��ǰ����ֹͣ����pue������ʱ��
 *//*
    public Date endTime_Day_Stop() {
        return new Date();
    }

    *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ���µ�ȵ��ռ��

    *//**
 * @return ���¿�ʼ����pue����ʼʱ��
 *//*
    public Date startTime_Month_Began() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    *//**
 * @return ���¿�ʼ����pue������ʱ��
 *//*
    public Date endTime_Month_Began() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ�����ȵ��ռ��

    *//**
 * @return ���꿪ʼ����pue����ʼʱ��
 *//*
    public Date startTime_Year_Began() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    *//**
 * @return ���꿪ʼ����pue������ʱ��
 *//*
    public Date endTime_Year_Began() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

     *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ��ȥ24Сʱ��ȵ��ռ��

    *//**
 * @return ��ȥ24Сʱ��ʼ����pue����ʼʱ��
 *//*
    public Date startTime_PastDay_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        return cal.getTime();
    }

    *//**
 * @return ��ȥ24Сʱ��ʼ����pue������ʱ��
 *//*
    public Date endTime_PastDay_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.DAY_OF_YEAR, -1);
        cal.roll(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }

    *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ��ȥ30���ȵ��ռ��

    *//**
 * @return ��ȥ30�쿪ʼ����pue����ʼʱ��
 *//*
    public Date startTime_PastMonth_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.MONTH, -1);
        return cal.getTime();
    }

    *//**
 * @return ��ȥ30�쿪ʼ����pue������ʱ��
 *//*
    public Date endTime_PastMonth_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.MONTH, -1);
        cal.roll(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }

 *//*-----------------------------------------------------------------------------------*//*
    //�����ѯ��ȥһ���ȵ��ռ��

    *//**
 * @return ��ȥһ�꿪ʼ����pue����ʼʱ��
 *//*
    public Date startTime_PastYear_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.YEAR, -1);
        return cal.getTime();
    }

    *//**
 * @return ��ȥһ�꿪ʼ����pue������ʱ��
 *//*
    public Date endTime_PastYear_Began() {
        Calendar cal = Calendar.getInstance();
        cal.roll(Calendar.YEAR, -1);
        cal.roll(Calendar.HOUR_OF_DAY, 1);
        return cal.getTime();
    }


    *//**
 * @return ��ǰʱ��
 *//*
    public Date getCurrentTime() {
        return new Date();
    }

    *//**
 * @return ��ǰ�µ�һ������
 *//*
    public Date getZeroPointOfCurrentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }*/
/**
 public String parseDate(Date date) {
 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 return sdf.format(date);
 }
 */