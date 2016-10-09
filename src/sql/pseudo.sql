CREATE TABLE Equipment (

id CHAR(32) NOT NULL,

name CHAR(32) NOT NULL,

port CHAR(10) NOT NULL,

rate CHAR(6) NOT NULL,

addr CHAR(3) NOT NULL,

timeout CHAR(6) NOT NULL,

data CHAR(2) NOT NULL DEFAULT '8',

stop CHAR(2) NOT NULL DEFAULT '1',

parity CHAR(2) NOT NULL DEFAULT '0',

switch CHAR(1) NOT NULL DEFAULT '1',

delayed CHAR(1) NOT NULL DEFAULT '1',

dt DATE NOT NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE EquipmentInfo (

id CHAR(32) NOT NULL,

route SMALLINT NULL,

name CHAR(32) NULL,

fk CHAR(32) NULL,

it_symbol SMALLINT NULL,

it_per SMALLINT NULL DEFAULT 100,

total_symbol SMALLINT NULL,

total_per CHARACTER VARYING(255) NULL,

dt date NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);

DROP TABLE RealKwh;

CREATE TABLE RealKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL NULL,

dt date NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);

DROP TABLE DayKwh;

CREATE TABLE DayKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt DATE NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE YearKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt DATE NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE AccumDayKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt TIME NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE AccumYearKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route SMALLINT NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt DATE NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE AccumMonthKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

num SMALLINT NULL,

dt DATE NULL DEFAULT getdate(),

PRIMARY KEY (id) 

);



CREATE TABLE MonthKwh (

id CHAR(32) NOT NULL,

fk CHAR(32) NULL,

route DECIMAL(3) NULL,

degree  REAL  NULL,

point SMALLINT NULL,

dt TIME NULL DEFAULT getdate(),

PRIMARY KEY (id ) 

);





ALTER TABLE EquipmentInfo ADD CONSTRAINT Equipment_config_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE RealKwh ADD CONSTRAINT real_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE YearKwh ADD CONSTRAINT year_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE DayKwh ADD CONSTRAINT day_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE AccumDayKwh ADD CONSTRAINT accumday_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE AccumYearKwh ADD CONSTRAINT accumyear_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE AccumMonthKwh ADD CONSTRAINT acmonth_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);

ALTER TABLE MonthKwh ADD CONSTRAINT month_Equipment_id FOREIGN KEY (fk) REFERENCES Equipment (id);



