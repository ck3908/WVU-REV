 drop table userinfo cascade constraints;
 drop table caroffer cascade constraints;
 drop table cardetails cascade constraints;
 drop table usecars cascade constraints;
 drop table usecar cascade constraints;

   
  drop sequence userinfo_seq;
  drop sequence caroffer_seq;
   
  create table userinfo (
	id number(20)  primary key,  -- so we can use in the many to many table
	name varchar2(20) unique,  -- assume user login names are unqiue
	password varchar2(20) NULL,
	code varchar2(10) NULL
	);
  
  create table caroffer (
	id number(20)  primary key,		--- so we can use in the many to many table
	platenum number(20) unique,  -- referenced as foreign key from the many to many table
	nameperson varchar2(20) not null,
	offerprice number(20),
	status varchar2(10),
	downpayment number(20),
	findeal number(10)
	);
	
  create table cardetails (
	plate number(20) primary key,
	carname varchar2(20) not null,
	owned varchar2(10), -- easier to change to owner name if need to - for now it is T or F
	offersold number(20),  -- if owned, then this is sold price, if not it would be current offer price
	downpayment number(20), -- may not be necessary field
	totalpaid number(10,2),
	loantype number(10),  -- same as findeal above
	monthlydue number(10,2),  -- monthly payment based on term of the deal
	loanterm number(10),
	principalbal number(20,2) 
	);
    

  create table usercars (
    username varchar2(20),
	carplate number(20),
	constraint pk_useroffer primary key (username,carplate),
	constraint fk_useroffer_user foreign key (username) references userinfo(name),
	constraint fk_useroffer_offer foreign key (carplate) references caroffer(platenum)
	);
	
create sequence userinfo_seq;
create sequence caroffer_seq;
-- create sequence cardetails_seq;
-- create sequence usercar_seq;


-- Trigger
-- A PL/SQL block that executes under certain conditions
-- Triggers can execute before or after
-- Any DML statement (update, delete, or insert)
create or replace trigger userinfo_pk_trig
before insert or update on userinfo
for each row
begin
    if INSERTING then
        select userinfo_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger caroffer_pk_trig
before insert or update on caroffer
for each row
begin
    if INSERTING then
        select caroffer_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

commit;


	
  