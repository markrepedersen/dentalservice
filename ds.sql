-- First drop any existing tables. Any errors are ignored.
--
drop table Employee cascade constraints;
drop table Receptionist cascade constraints;
drop table Hygienist cascade constraints;
drop table Dentist cascade constraints;
drop table WorksFor cascade constraints;
drop table Assists cascade constraints;
drop table Medicine cascade constraints;
drop table Customer cascade constraints;
drop table Treats cascade constraints;
drop table Appointment cascade constraints;
drop table Bill cascade constraints;
drop table Uses cascade constraints;
drop table login_details;
drop table attends cascade constraints;
--
-- Now add each table.
--
create table Employee(
	eid integer primary key,
	fname varchar2(255) NOT NULL,
	lname varchar2(255) NOT NULL,
	salary integer,
	age integer,
	sex char(1),
	phone_Num integer,
	isSupervisor integer,
	check(age > 0)
	);
create table Receptionist(
	rid integer primary key,
	fname varchar2(255) NOT NULL,
	lname varchar2(255) NOT NULL,
	salary integer,
	age integer,
	sex char(1),
	phone_Num integer,
	isSupervisor integer
	);
create table Hygienist(
	hid integer primary key,
	fname varchar2(255) not null,
	lname varchar2(255) not null,
	salary integer,
	age integer,
	sex char(1),
	phone_Num integer,
	isSupervisor integer
	);
create table Dentist(
	did integer primary key,
	fname varchar2(255) not null,
	lname varchar2(255) not null,
	salary integer,
	age integer,
	sex char(1),
	phone_Num integer,
	isSupervisor integer
	);
create table WorksFor(
	eid integer,
	sid integer,
	primary key(eid,sid),
	foreign key(eid) references Employee(eid) ON DELETE CASCADE,
	foreign key(sid) references Employee(eid) ON DELETE CASCADE
	);
create table Assists(
	hid integer,
	did integer,
	primary key(hid,did),
	foreign key(hid) references Hygienist(hid) ON DELETE CASCADE,
	foreign key(did) references Dentist(did) ON DELETE CASCADE
	);
create table Medicine(
	code integer primary key,
	cost numeric(10,2),
	description varchar2(255)
	);
create table Customer(
	cid integer primary key,
	fname varchar2(255),
	lname varchar2(255),
	phone_Num integer,
	dob	date,
	email varchar2(255),
	address varchar2(255)
	);
create table Treats(
	code integer,
	cid integer,
	primary key(code,cid),
	foreign key(code) references Medicine(code) ON DELETE CASCADE,
	foreign key(cid) references Customer(cid) ON DELETE CASCADE
	);

create table Attends(
	did integer,
	cid integer,
	primary key(did, cid),
	foreign key(did) references Dentist ON DELETE CASCADE,
	foreign key(cid) references Customer ON DELETE CASCADE
);

create table Appointment(
	num integer primary key,
	type char(4),
	from_Time timestamp,
	to_Time timestamp,
	rid integer not null,
	cid integer not null,
	foreign key(rid) references Receptionist(rid) ON DELETE CASCADE,
	foreign key(cid) references Customer(cid) ON DELETE CASCADE
	);
create table Bill(
	bid integer primary key,
	type char(4),
	amountPaid numeric(10,2),
	amountOwes numeric(10,2),
	dueDate date,
	isPaid Integer,
	cid integer not null,
	foreign key(cid) references Customer(cid) on delete cascade,
	check(isPaid = 1 or isPaid = 0),
	check(amountPaid < amountOwes)
	);
create table Uses(
	did integer,
	code integer,
	primary key(did,code),
	foreign key(did) references Dentist(did) ON DELETE CASCADE,
	foreign key(code) references Medicine(code) ON DELETE CASCADE
	);

	create table login_details(
		username Varchar2(255),
		hashpass Varchar2(255),
        salt Varchar2(255),
		type Varchar2(10),
		eid integer,
		foreign key(eid) references Employee(eid) ON DELETE CASCADE,
		primary key(username)
	);
--
-- done adding all of the tables, now add in some tuples
--
-- first, add in the employees
--
insert into Employee values (0000001, 'Natasha', 'Romanova', 48000, 34, 'f', 6047854321, 0);
insert into Employee values (0000002, 'Steve', 'Rogers', 39500, 40, 'm', 6044865768, 1);
insert into Employee values (0000003, 'Carol', 'Danvers', 44200, 30, 'f', 7784321897, 0);
insert into Employee values (0000004, 'Clinton', 'Barton', 37000, 38, 'm', 6047151212, 0);
insert into Employee values (0000005, 'Hank', 'Pym', 50000, 37, 'm', 6047142232, 0);
insert into Employee values (0000006, 'Anthony', 'Stark', 39000, 42, 'm', 7787142232, 0);
insert into Employee values (0000007, 'Thor', 'Odinson', 48000, 48, 'm', 6043321988, 0);
insert into Employee values (0000008, 'Richard', 'Jones', 42000, 32, 'm', 7786554786, 1);
insert into Employee values (0000009, 'Pietro', 'Maximoff', 35000, 27, 'm', 6042256123, 0);
insert into Employee values (0000010, 'Wanda', 'Maximoff', 29000, 35, 'f', 6049975544, 0);
insert into Employee values (0000011, 'Victor', 'Shade', 31000, 50, 'm', 6043944567, 0);
insert into Employee values (0000012, 'Hane', 'Whitman', 34000, 39, 'm', 7786815876, 0);
insert into Employee values (0000115, 'Charles', 'Xavier', 104000, 24, 'm', 6047894564, 1);
insert into Employee values (0000116, 'Robert', 'Drake', 95000, 30, 'm',  7781234567, 1);
insert into Employee values (0000117, 'Henry', 'McCoy', 110000, 20, 'm', 7784324887, 0);
insert into Employee values (0000118, 'Kurt', 'Wagner', 118000, 28, 'm', 6042347777, 0);
insert into Employee values (0000119, 'Scott', 'Summers', 107000, 27, 'm', 6047686891, 0);
insert into Employee values (0000120, 'Warren', 'Worthington', 127000, 27, 'm', 6047221341, 0);
insert into Employee values (0000121, 'Alexander', 'Summers', 114000, 27, 'm', 7786575555, 1);
insert into Employee values (0000122, 'Ororo', 'Monroe', 106000, 29, 'f', 7785432132, 0);
insert into Employee values (0000123, 'James', 'Howlett', 109000, 31, 'm', 7789965672, 0);
insert into Employee values (0000124, 'Piotr', 'Rasputin', 109000, 30, 'm', 6041118354, 0);
insert into Employee values (0000125, 'Anna', 'Marie', 118000, 34, 'f', 6044538911, 0);
insert into Employee values (0000126, 'Jean', 'Grey', 118000, 30, 'f', 7782345612, 0);
insert into Employee values (0000331, 'Diana', 'Prince', 184000, 44, 'f', 6045556758, 0);
insert into Employee values (0000332, 'Barry', 'Allen', 199000, 31, 'm', 7781148167, 0);
insert into Employee values (0000333, 'Arthur', 'Curry', 180000, 35, 'm', 7786232323, 0);
insert into Employee values (0000334, 'Bruce', 'Wayne', 200000, 48, 'm', 6048695531, 0);
insert into Employee values (0000345, 'John', 'Jones', 177000, 37, 'm', 6048145867, 0);
insert into Employee values (0000346, 'Clark', 'Kent', 189000, 41, 'm', 6047431222, 0);
insert into Employee values (0000347, 'Hal', 'Jordan', 184000, 34, 'm', 6043428888, 0);
insert into Employee values (0000348, 'Oliver', 'Queen', 199000, 39, 'm', 7785472323, 1);
insert into Employee values (0000349, 'Ray', 'Palmer', 192000, 40, 'm', 7785465757, 1);
insert into Employee values (0000350, 'Carter', 'Hall', 202000, 32, 'm', 7789651211, 0);
insert into Employee values (0000351, 'Laurel', 'Lance', 201000, 38, 'f', 7786572299, 0);
insert into Employee values (0000352, 'John', 'Smith', 210000, 50, 'm', 7785432714, 0);
--
-- now add in Dentist
--
insert into Dentist values (0000331, 'Diana', 'Prince', 184000, 44, 'f', 6045556758, 0);
insert into Dentist values (0000332, 'Barry', 'Allen', 199000, 31, 'm', 7781148167, 0);
insert into Dentist values (0000333, 'Arthur', 'Curry', 180000, 35, 'm', 7786232323, 0);
insert into Dentist values (0000334, 'Bruce', 'Wayne', 200000, 48, 'm', 6048695531, 0);
insert into Dentist values (0000345, 'John', 'Jones', 177000, 37, 'm', 6048145867, 0);
insert into Dentist values (0000346, 'Clark', 'Kent', 189000, 41, 'm', 6047431222, 0);
insert into Dentist values (0000347, 'Hal', 'Jordan', 184000, 34, 'm', 6043428888, 0);
insert into Dentist values (0000348, 'Oliver', 'Queen', 199000, 39, 'm', 7785472323, 1);
insert into Dentist values (0000349, 'Ray', 'Palmer', 192000, 40, 'm', 7785465757, 1);
insert into Dentist values (0000350, 'Carter', 'Hall', 202000, 32, 'm', 7789651211, 0);
insert into Dentist values (0000351, 'Laurel', 'Lance', 201000, 38, 'f',  7786572299, 0);
insert into Dentist values (0000352, 'John', 'Smith', 210000, 50, 'm', 7785432714, 0);
--
-- now add in Receptionist
--
insert into Receptionist values (0000001, 'Natasha', 'Romanova', 48000, 34, 'f', 6047854321, 0);
insert into Receptionist values (0000002, 'Steve', 'Rogers', 39500, 40, 'm', 6044865768, 1);
insert into Receptionist values (0000003, 'Carol', 'Danvers', 44200, 30, 'f', 7784321897, 0);
insert into Receptionist values (0000004, 'Clinton', 'Barton', 37000, 38, 'm',  6047151212, 0);
insert into Receptionist values (0000005, 'Hank', 'Pym', 50000, 37, 'm', 6047142232, 0);
insert into Receptionist values (0000006, 'Anthony', 'Stark', 39000, 42, 'm',  7787142232, 0);
insert into Receptionist values (0000007, 'Thor', 'Odinson', 48000, 48, 'm', 6043321988, 0);
insert into Receptionist values (0000008, 'Richard', 'Jones', 42000, 32, 'm', 7786554786, 1);
insert into Receptionist values (0000009, 'Pietro', 'Maximoff', 35000, 27, 'm',  6042256123, 0);
insert into Receptionist values (0000010, 'Wanda', 'Maximoff', 29000, 35, 'f', 6049975544, 0);
insert into Receptionist values (0000011, 'Victor', 'Shade', 31000, 50, 'm', 6043944567, 0);
insert into Receptionist values (0000012, 'Hane', 'Whitman', 34000, 39, 'm', 7786815876, 0);
--
-- now add in Hygienist
--
insert into Hygienist values (0000115, 'Charles', 'Xavier', 104000, 24, 'm', 6047894564, 1);
insert into Hygienist values (0000116, 'Robert', 'Drake', 95000, 30, 'm', 7781234567, 1);
insert into Hygienist values (0000117, 'Henry', 'McCoy', 110000, 20, 'm', 7784324887, 0);
insert into Hygienist values (0000118, 'Kurt', 'Wagner', 118000, 28, 'm', 6042347777, 0);
insert into Hygienist values (0000119, 'Scott', 'Summers', 107000, 27, 'm', 6047686891, 0);
insert into Hygienist values (0000120, 'Warren', 'Worthington', 127000, 27, 'm', 6047221341, 0);
insert into Hygienist values (0000121, 'Alexander', 'Summers', 114000, 27, 'm',  7786575555, 1);
insert into Hygienist values (0000122, 'Ororo', 'Monroe', 106000, 29, 'f', 7785432132, 0);
insert into Hygienist values (0000123, 'James', 'Howlett', 109000, 31, 'm', 7789965672, 0);
insert into Hygienist values (0000124, 'Piotr', 'Rasputin', 109000, 30, 'm', 6041118354, 0);
insert into Hygienist values (0000125, 'Anna', 'Marie', 118000, 34, 'f', 6044538911, 0);
insert into Hygienist values (0000126, 'Jean', 'Grey', 118000, 30, 'f', 7782345612, 0);
--
-- now add in the WorksFor
--
insert into WorksFor values (0000345, 0000352);
insert into WorksFor values (0000119, 0000345);
insert into WorksFor values (0000118, 0000345);
insert into WorksFor values (0000117, 0000345);
insert into WorksFor values (0000115, 0000345);
insert into WorksFor values (0000121, 0000347);
insert into WorksFor values (0000122, 0000347);
insert into WorksFor values (0000123, 0000347);
insert into WorksFor values (0000124, 0000346);
insert into WorksFor values (0000120, 0000346);
insert into WorksFor values (0000125, 0000346);
insert into WorksFor values (0000350, 0000346);
--
-- now add in the Assists
--
insert into Assists values (0000116, 0000334);
insert into Assists values (0000116, 0000345);
insert into Assists values (0000118, 0000331);
insert into Assists values (0000117, 0000333);
insert into Assists values (0000115, 0000334);
insert into Assists values (0000115, 0000345);
insert into Assists values (0000119, 0000346);
insert into Assists values (0000120, 0000347);
insert into Assists values (0000121, 0000348);
insert into Assists values (0000121, 0000349);
insert into Assists values (0000122, 0000349);
insert into Assists values (0000123, 0000351);
--
-- now add in Medicine
--
insert into Medicine values (558234, 12.50, 'Prescription Paste');
insert into Medicine values (578489, 38.99, 'Anesthesia');
insert into Medicine values (566666, 60.00, 'Acetaminophen');
insert into Medicine values (525111, 25.86, 'Corticosteroid');
insert into Medicine values (599634, 31.04, 'Aspirin');
insert into Medicine values (584546, 67.24, 'Hydrocodone');
insert into Medicine values (555129, 11.99, 'Antiseptic');
insert into Medicine values (598756, 54.44, 'Ibuprofen');
insert into Medicine values (521321, 89.21, 'Antibiotic');
insert into Medicine values (511111, 101.29, 'Saliva substitute');
insert into Medicine values (586662, 40.00, 'Antifungal');
insert into Medicine values (542321, 43.74, 'Fluoride');
--
-- now add in
--
insert into Customer values (111801, 'Mark', 'Pedersen', 6042347333, '1995-12-31', 'pedersen.mark@hotmail.com', '1326 Davie St, Vancouver, BC V6E 1N6');
insert into Customer values (111888, 'Mark', 'Pedersen', 6042347333, '1994-12-31', 'pedersen.mark@gmail.com', '1326 Davie St, Vancouver, BC V6E 1N6');
insert into Customer values (111999, 'Mark', 'Pedersen', 6042347333, '1993-12-31', 'pedersen.mark@live.com', '1326 Davie St, Vancouver, BC V6E 1N6');
insert into Customer values (111861, 'Liam', 'Adams', 7789215656, '1995-12-31', 'liamadams@yahoo.com', '1932 West 1st Avenue, Vancouver, BC V6J 1G6');
insert into Customer values (111993, 'Theodore', 'Lau', 6049691124, '1995-12-31', 'theodorel@gmail.com', '4551 Northey Road, Richmond, BC V6X 2G4');
insert into Customer values (111657, 'Abhinav', 'Behera', 6045479898, '1995-12-31', 'beabhinav22@me.com', '3812 Eton Street, Burnaby, BC V5C 1J4');
insert into Customer values (111811, 'No', 'Won', 6042004200, '1995-12-31', 'no1@gmail.com', '1326 Nowhere St, Nocity, BC V6R 1N1');
insert into Customer values (111834, 'Katharyn', 'Eisenmenger', 2505456533, '1995-12-31', 'eisenkat11@gmail.com', '1970 Coldstream Avenue, Vernon, BC V1T 6N1');
insert into Customer values (111921, 'Diane', 'Aguiar', 6042138495, '1995-12-31', 'dag42@yahoo.com', '1220 Jade St, West Vancouver, BC V7T 2W4');
insert into Customer values (111967, 'Torri', 'Robinette', 2503431939, '1995-12-31', 'robintor@hotmail.com', '2761 Yoho Valley Road, Field, BC V0A 1G0');
insert into Customer values (111542, 'Irma', 'Snowden', 4033801429, '1995-12-31', 'snowirma@gmail.com', '1894 9th Ave, Lethbridge, AB T1J 2J7');
insert into Customer values (111682, 'Issac', 'Shropshire', 2504154651, '1995-12-31', 'shropshire99@me.com', '2311 Burdett Avenue, Victoria, BC V8W 1B2');
insert into Customer values (111659, 'Dorie', 'Setzer', 6043135348, '1995-12-31', 'setzerdorie@me.com', '804 James Street, Vancouver, BC V5W 3C3');
insert into Customer values (111986, 'Ranae', 'Leung', 2504965085, '1995-12-31', 'ranae4879@gmail.com', '3354 Calgary Avenue, Penticton, BC V2A 2T6');

--
-- now add in the Treats
--
insert into Attends values (0000331, 111542);
insert into Attends values (0000331, 111801);
insert into Attends values (0000331, 111888);
insert into Attends values (0000331, 111999);
insert into Attends values (0000331, 111861);
insert into Attends values (0000331, 111993);
insert into Attends values (0000331, 111657);
insert into Attends values (0000331, 111811);
insert into Attends values (0000331, 111834);
insert into Attends values (0000331, 111921);
insert into Attends values (0000331, 111967);
insert into Attends values (0000331, 111682);
insert into Attends values (0000331, 111659);
insert into Attends values (0000331, 111986);
insert into Attends values (0000347, 111801);
insert into Attends values (0000348, 111801);
insert into Attends values (0000346, 111993);
insert into Attends values (0000350, 111801);
insert into Attends values (0000352, 111921);
--
-- now add in the Treats
--
insert into Treats values (578489, 111861);
insert into Treats values (578489, 111993);
insert into Treats values (566666, 111657);
insert into Treats values (525111, 111811);
insert into Treats values (599634, 111811);
insert into Treats values (599634, 111967);
insert into Treats values (511111, 111659);
insert into Treats values (586662, 111682);
insert into Treats values (521321, 111542);
insert into Treats values (521321, 111834);
insert into Treats values (521321, 111801);
insert into Treats values (555129, 111986);
--
-- now add in Appointment
--
insert into Appointment values (4210, 'CHEK', '2017-10-30 16:00:00', '2016-10-30 16:20:00', 0000001, 111801);
insert into Appointment values (4228, 'CLEN', '2017-10-21 10:30:00', '2016-10-21 11:10:00', 0000001, 111801);
insert into Appointment values (4233, 'CAVT', '2016-1-19 12:10:00', '2016-11-19 12:50:00', 0000003, 111801);
insert into Appointment values (4277, 'DRIL', '2016-1-19 14:00:00', '2016-11-19 15:20:00', 0000004, 111801);
insert into Appointment values (4296, 'DRIL', '2016-11-24 15:00:00', '2016-11-24 16:20:00', 0000005, 111921);
insert into Appointment values (4242, 'CAVT', '2016-11-30 09:00:00', '2016-11-30 10:00:00', 0000006, 111542);
insert into Appointment values (4263, 'CHEK', '2016-12-01 09:00:00', '2016-12-01 10:20:00', 0000007, 111682);
insert into Appointment values (4288, 'CHEK', '2016-12-08 12:00:00', '2016-12-08 12:20:00', 0000008, 111659);
insert into Appointment values (4207, 'CHEK', '2016-12-25 15:00:00', '2016-12-25 15:20:00', 0000009, 111986);
insert into Appointment values (4255, 'EXTR', '2017-01-03 11:00:00', '2016-01-03 13:00:00', 0000010, 111967);
insert into Appointment values (4241, 'EXTR', '2017-01-08 15:00:00', '2016-01-08 17:00:00', 0000010, 111993);
insert into Appointment values (4274, 'FILL', '2017-01-10 10:00:00', '2016-01-10 10:40:00', 0000008, 111993);

-- now add in Bill
--
insert into Bill values (48232, 'BANK', 10.00, 87.61, '2015-12-31', 1, 111801);
insert into Bill values (48251, 'INST', 20.00, 799.99, '2015-12-31',  1,  111801);
insert into Bill values (48233, 'INST', 30.00, 51.23, '2015-12-31', 0,  111801);
insert into Bill values (48248, 'CHEQ', 40.00, 134.45, '2017-12-31', 0,  111801);
insert into Bill values (48291, 'ONLI', 10.00, 21.49, '2017-12-31', 1,  111659);
insert into Bill values (48304, 'INST', 10.00, 91.89, '2017-12-31', 1,  111682);
insert into Bill values (48322, 'BANK', 10.00, 78.69, '2017-12-31', 1,  111967);
insert into Bill values (48339, 'ONLI', 10.00, 43.59, '2017-12-31', 1,  111993);
insert into Bill values (48340, 'ONLI', 10.00, 21.49, '2017-12-31', 1,  111921);
insert into Bill values (48351, 'CHEQ', 10.00, 799.99, '2017-12-31', 1,  111921);
insert into Bill values (48357, 'CHEQ', 100.00, 629.05, '2017-12-31', 0,  111659);
insert into Bill values (48359, 'ONLI', 100.00, 799.82, '2017-12-31', 0,  111657);
--
-- now add in the Uses
--
insert into Uses values (0000331, 558234);
insert into Uses values (0000332, 599634);
insert into Uses values (0000333, 599634);
insert into Uses values (0000334, 511111);
insert into Uses values (0000345, 586662);
insert into Uses values (0000346, 542321);
insert into Uses values (0000346, 566666);
insert into Uses values (0000347, 598756);
insert into Uses values (0000348, 598756);
insert into Uses values (0000350, 598756);
insert into Uses values (0000350, 511111);
insert into Uses values (0000351, 558234);



--
-- Insertions for login_details will be done dynamically as employees are
-- registered into system
--

--
-- End of inserts

--
-- Procedures
--
-- CREATE OR REPLACE PROCEDURE Emp_Insert ( eid integer,
--                                        	fname varchar2(255) NOT NULL,
--                                         	lname varchar2(255) NOT NULL,
--                                         	salary integer,
--                                         	age integer,
--                                         	sex char(1),
--                                         	phone_Num integer,
--                                         	isSupervisor integer )
--AS language Java
--name 'EmpInsertTrigger.insEmpTrigger (int, java.lang.String, java.lang.String, int, int, java.lang.String, long, int)';

--CREATE OR REPLACE PROCEDURE Emp_Delete ( eid integer)
--AS language Java
--name 'EmpDeleteTrigger.delEmpTrigger (int)';

--CREATE OR REPLACE PROCEDURE Emp_Update ( eid integer,
--                                         	fname varchar2(255) NOT NULL,
--                                         	lname varchar2(255) NOT NULL,
--                                        	salary integer,
--                                         	age integer,
--                                         	sex char(1),
--                                         	phone_Num integer,
--                                         	isSupervisor integer )
--AS language Java
--name 'EmpUpdateTrigger.upEmpTrigger (int, java.lang.String, java.lang.String, int, int, java.lang.String, long, int)';
--
-- end of procedures
--

--
-- Triggers
--
--CREATE OR REPLACE TRIGGER Emp_Insert_Trigger
--AFTER INSERT ON Employee
--FOR EACH ROW
--CALL Emp_Insert(:new.eid, :new.fname, :new.lname, :new.salary, :new.age, :new.sex, :new.phone_Num, :new.isSupervisor);
--/

--CREATE OR REPLACE TRIGGER Emp_Delete_Trigger
--AFTER DELETE ON Employee
--FOR EACH ROW
--CALL Emp_Delete(:old.eid);
--/

--CREATE OR REPLACE TRIGGER Emp_Update_Trigger
--AFTER UPDATE ON Employee
--FOR EACH ROW
--CALL Emp_Update(:old.eid, :new.fname, :new.lname, :new.salary, :new.age, :new.sex, :new.phone_Num, :new.isSupervisor);
--/

--
-- End of triggers
--
-- End of Data
--

commit;
