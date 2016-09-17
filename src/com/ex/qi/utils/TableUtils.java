package com.ex.qi.utils;

import com.ex.qi.dao.BaseDaoImpl;

import java.sql.SQLException;

/**
 * Created by sunline on 2016/8/18.
 */
public class TableUtils extends BaseDaoImpl {
    public boolean newRealKwhTable() {
        String sql = "CREATE TABLE RealKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route SMALLINT NULL," +
                "degree  REAL NULL," +
                "dt DATETIME  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE RealKwh ADD CONSTRAINT real_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newYearKwhTable() {
        String sql = "CREATE TABLE YearKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route SMALLINT NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATE NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE YearKwh ADD CONSTRAINT year_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newDayKwhTable() {
        String sql = "CREATE TABLE DayKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route DECIMAL(3) NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATETIME  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE DayKwh ADD CONSTRAINT day_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newMonthKwhTable() {
        String sql = "CREATE TABLE MonthKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route DECIMAL(3) NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATE  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE MonthKwh ADD CONSTRAINT month_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newDeviceTable() {
        String sql = "CREATE TABLE Equipment (" +
                "id varchar(32) NOT NULL," +
                "name varchar(32) NULL," +
                "port varchar(10) NULL," +
                "rate varchar(6) NULL," +
                "addr varchar(3) NULL," +
                "timeout varchar(4) DEFAULT '200'," +
                "data varchar(2) DEFAULT '8'," +
                "stop varchar(2) DEFAULT '1'," +
                "parity varchar(2) DEFAULT '0'," +
                "switch varchar(1) DEFAULT '1'," +
                "delayed varchar(1) DEFAULT '1'," +
                "dt DATE DEFAULT sysdate," +
                "primary key(id)" +
                ")";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newDeviceInfoTable() {
        String sql = "CREATE TABLE EquipmentInfo (" +
                "id varchar(32) NOT NULL," +
                "route NUMBER(2,0)," +
                "name varchar(32) ," +
                "attr NUMBER(2,0)," +
                "fk varchar(32)," +
                "per NUMBER(3,0) DEFAULT 100," +
                "symbol NUMBER(1,0) DEFAULT 1," +
                "dt date DEFAULT sysdate," +
                "PRIMARY KEY (id)" +
                ");" +
                "ALTER TABLE DeviceInfo ADD CONSTRAINT device_config_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newAccumDayKwhTable() {
        String sql = "CREATE TABLE AccumDayKwh (" +
                "id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route DECIMAL(3) NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATETIME  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE AccumDayKwh ADD CONSTRAINT accumday_device_id FOREIGN KEY (fk) REFERENCES Equipment (id)";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newAccumMonthKwhTable() {
        String sql = "CREATE TABLE AccumMonthKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route DECIMAL(3) NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATE  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE AccumMonthKwh ADD CONSTRAINT acmonth_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newAccumYearKwhTable() {
        String sql = "CREATE TABLE AccumYearKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route SMALLINT NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATE NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE AccumYearKwh ADD CONSTRAINT accumyear_device_id FOREIGN KEY (fk) REFERENCES Equipment (id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }
    public void init(){
        String sql = "declare num number; " +
                "begin" +
                "    select count(1) into num from user_tables where table_name='EQUIPMENT';" +
                "if num > 0 then " +
                "     dbms_output.put_line('存在!');" +
                "      execute immediate 'drop table EQUIPMENT'; " +
                "  end if;" +
                "   execute immediate 'CREATE TABLE EQUIPMENT (" +
                "    id varchar(32) primary key," +
                "    name varchar(32) NULL," +
                "    port varchar(10) NULL," +
                "    rate varchar(6) NULL," +
                "    addr varchar(3) NULL," +
                "    timeout varchar(4)," +
                "    data varchar(2)," +
                "    stop varchar(2)," +
                "    parity varchar(2)," +
                "    switch varchar(1)," +
                "    delayed varchar(1)," +
                "    dt DATE" +
                ")';" +
                "end; ";
        execute(sql);
        sql = "declare num1 number;" +
                "begin" +
                "    select count(1) into num1 from user_tables where table_name='EquipmentInfo'; " +
                "if num1 > 0 then  " +
                "      execute immediate 'drop table EquipmentInfo';" +
                "  end if;" +
                "execute immediate 'CREATE TABLE EquipmentInfo (" +
                "id varchar(32) primary key," +
                "route NUMBER(2,0)," +
                "name varchar(32) ," +
                "attr NUMBER(2,0)," +
                "fk varchar(32)," +
                "per NUMBER(3,0) ," +
                "symbol NUMBER(1,0)," +
                "dt date," +
                ")';" +
                "end; ";
        execute(sql);
    }
}
