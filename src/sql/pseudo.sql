CREATE TABLE Device (

id CHAR(32) NOT NULL,

name CHAR(32) NOT NULL,

port CHAR(10) NOT NULL,

rate CHAR(6) NOT NULL,

addr CHAR(3) NOT NULL,

timeout CHAR(6) NOT NULL,

data CHAR(2) NOT NULL 8,

stop CHAR(2) NOT NULL 1,

parity CHAR(2) NOT NULL 0,

switch CHAR(1) NOT NULL 1,

delayed CHAR(1) NOT NULL 1,

dt TIMESTAMP NOT NULL getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE DeviceInfo (

id CHAR(32) NOT NULL,

route SMALLINT NULL,

name CHAR(32) NULL,

fk CHAR(32) NULL,

it_symbol SMALLINT NULL,

it_per SMALLINT NULL 100,

total_symbol SMALLINT NULL,

total_per CHARACTER VARYING(255) NULL,

dt TIMESTAMP NULL getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE RealKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL NULL,

dt TIMESTAMP NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE DayKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt DATE NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE YearKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt DATE NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE AccumDayKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt TIME NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE AccumYearKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt DATE NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE AccumMonthKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt DATE NULL getdate(),

PRIMARY KEY (_id) 

);



CREATE TABLE MonthKwh (

_id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt TIME NULL getdate(),

PRIMARY KEY (_id) 

);





ALTER TABLE DeviceInfo ADD CONSTRAINT device_config_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE RealKwh ADD CONSTRAINT real_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE YearKwh ADD CONSTRAINT year_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE DayKwh ADD CONSTRAINT day_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE AccumDayKwh ADD CONSTRAINT accumday_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE AccumYearKwh ADD CONSTRAINT accumyear_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE AccumMonthKwh ADD CONSTRAINT acmonth_device_id FOREIGN KEY (fk) REFERENCES Device (id);

ALTER TABLE MonthKwh ADD CONSTRAINT month_device_id FOREIGN KEY (fk) REFERENCES Device (id);



