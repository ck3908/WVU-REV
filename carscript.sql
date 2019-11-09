 drop table userinfo cascade constraints;
 

   
  drop sequence userinfo_seq;
   
  create table userinfo (
	id number(20)  primary key,
	name VARCHAR2(20) unique,  -- assume user login names are unqiue
	password VARCHAR2(20) NULL,
	code VARCHAR(10) NULL
	);
  
  create table caroffer (
	id number(20) primary key,
	platenum varchar2(20) not null,  -- referenced as foreign key from the many to many table
	nameperson varchar2(20) not null,
	offerprice number(20),
	status varchar(10),
	downpayment number(20),
	findeal number(10)
	);
	
  create table cardetails (
	id number(20) primary key,
	carname varchar2(20) not null,
	plate varchar2(20) primary key,
	owned varchar2(10), -- easier to change to owner name if need to - for now it is T or F
	offersold number(20),  -- if owned, then this is sold price, if not it would be current offer price
	downpayment number(20), -- may not be necessary field
	totalpaid number(10,2),
	loantype number(10),  -- same as findeal above
	monthlydue number(10,2),  -- monthly payment based on term of the deal
	loanterm number(10),
	principalbal number(20,2)
	);
	
  