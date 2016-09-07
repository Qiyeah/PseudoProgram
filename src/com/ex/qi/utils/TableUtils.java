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
                "ALTER TABLE RealKwh ADD CONSTRAINT real_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
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
                "dt DATE NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE YearKwh ADD CONSTRAINT year_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
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
                "dt DATETIME  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE DayKwh ADD CONSTRAINT day_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
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
                "dt DATE  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE MonthKwh ADD CONSTRAINT month_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newDeviceTable() {
        String sql = "CREATE TABLE Device (" +
                "_id CHAR(32) NOT NULL," +
                "name CHAR(32) NOT NULL," +
                "port CHAR(10) NOT NULL," +
                "rate CHAR(6) NOT NULL," +
                "addr CHAR(3) NOT NULL," +
                "timeout CHAR(6) NOT NULL DEFAULT '200'," +
                "data CHAR(2) NOT NULL DEFAULT '8'," +
                "stop CHAR(2) NOT NULL DEFAULT '1'," +
                "parity CHAR(2) NOT NULL DEFAULT '0'," +
                "switch CHAR(1) NOT NULL DEFAULT '1'," +
                "delayed CHAR(2) NOT NULL DEFAULT '0'," +
                "dt DATETIME NOT NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newDeviceInfoTable() {
        String sql = "CREATE TABLE DeviceInfo (" +
                "_id CHAR(32) NOT NULL," +
                "route SMALLINT NOT NULL," +
                "name CHAR(32) NOT NULL," +
                "attr SMALLINT NOT NULL," +
                "fk CHAR(32) NOT NULL," +
                "per SMALLINT NOT NULL DEFAULT 100," +
                "symbol SMALLINT NOT NULL DEFAULT 1," +
                "dt DATETIME NOT NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE DeviceInfo ADD CONSTRAINT info_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }

    public boolean newAccumDayKwhTable() {
        String sql = "CREATE TABLE AccumDayKwh (" +
                "_id CHAR(32) NOT NULL," +
                "fk CHAR(32) NULL," +
                "route DECIMAL(3) NULL," +
                "degree  REAL  NULL," +
                "point SMALLINT NULL," +
                "num SMALLINT NULL," +
                "dt DATETIME  NULL DEFAULT getdate()," +
                "PRIMARY KEY (_id) " +
                ");" +
                "ALTER TABLE AccumDayKwh ADD CONSTRAINT accumday_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
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
                "ALTER TABLE AccumMonthKwh ADD CONSTRAINT acmonth_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
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
                "ALTER TABLE AccumYearKwh ADD CONSTRAINT accumyear_device_id FOREIGN KEY (fk) REFERENCES Device (_id);";
        try {
            return update(sql);
        } catch (SQLException e) {

        }
        return false;
    }
}
