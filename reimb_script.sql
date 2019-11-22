drop table logemp cascade constraints;
 drop table finfo cascade constraints;
 drop table dept cascade constraints;
 drop table fstatus cascade constraints;
 drop table fapprove cascade constraints;
  drop table freject cascade constraints;
  drop table fgrade cascade constraints;
 drop table reqfc cascade constraints;
 drop table freviewer cascade constraints;
 drop table fattach cascade constraints;
 
 
 drop sequence logemp_seq;
  drop sequence finfo_seq;
  drop sequence dept_seq;
  drop sequence fstatus_seq;
  drop sequence fapprove_seq;
  drop sequence freject_seq;
  drop sequence fgrade_seq;
  drop sequence reqfc_seq;
  drop sequence freviewer_seq;
  drop sequence fattach_seq;

   
  create table logemp (
	id number(20)  primary key,  
	name varchar2(20),  
	password varchar2(20) NULL,
	empdept number(20),
	empsupid number(20),
	empdeptid number(20)
	);
  
  create table dept (
	id number(20) primary key,
	dept number(20),
	deptname varchar2(20),
	depthead number(20) references logemp(id)
	);
	
  create table fgrade (
	id number(20) primary key,
	gradingid number(20),
	gradingreq varchar2(50)
	);
	
  create table finfo (
	id number(20) primary key,
	submitter number(20) references logemp(id),
	submitdate datetime,
	location varchar2(50),
	reqamount number(20), -- he called it baseamount, may need to revisit
	reimbamount number(20),
	gradingfmtid number(20) references fgrade(gradingid)
  );
   
   create table fstatus (
	id number(20) primary key,
	submitter number(20) references logemp(id),
	formid number(20) references finfo(id),
	status number(20)
   );

   create table fapprove (
	id number(20) primary key,
	submitter number(20) references logemp(id),
	formid number(20) references finfo(id),
	approvedate datetime,
	override number(20)
   );
   
   create table freject(
	id number(20) primary key,
	formid number(20) references finfo(id),
	rejecterid number(20) references logemp(id),
	reason varchar2(100)
   );

    create table reqfc (
	id number(20) primary key,
	askerid number(20) references logemp(id),
	formid number(20) references finfo(id),
	question varchar2(100),
	answer varchar2(100)
	);
	
	create table freviewer(
	id number(20) primary key,
	formid number(20) references finfo(id),
	reviewid number(20) references logemp(id)
	);
	
	create table attachment(
	id number(20) primary key,
	formid number(20) references finfo(id),
	urlstring varchar2(100)
	);
	
 create sequence logemp_seq;
  create sequence finfo_seq;
  create sequence dept_seq;
  create sequence fstatus_seq;
  create sequence fapprove_seq;
  create sequence freject_seq;
  create sequence fgrade_seq;
  create sequence reqfc_seq;
  create sequence freviewer_seq;
  create sequence fattach_seq;
-- create sequence usercar_seq;


-- Trigger
-- A PL/SQL block that executes under certain conditions
-- Triggers can execute before or after
-- Any DML statement (update, delete, or insert)
create or replace trigger logemp_pk_trig
before insert or update on logemp
for each row
begin
    if INSERTING then
        select logemp_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger finfo_pk_trig
before insert or update on finfo
for each row
begin
    if INSERTING then
        select finfo_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger dept_pk_trig
before insert or update on dept
for each row
begin
    if INSERTING then
        select dept_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger fstatus_pk_trig
before insert or update on fstatus
for each row
begin
    if INSERTING then
        select fstatus_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger fapprove_pk_trig
before insert or update on fapprove
for each row
begin
    if INSERTING then
        select fapprove_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger freject_pk_trig
before insert or update on freject
for each row
begin
    if INSERTING then
        select freject_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger fgrade_pk_trig
before insert or update on fgrade
for each row
begin
    if INSERTING then
        select fgrade_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger reqfc_pk_trig
before insert or update on reqfc
for each row
begin
    if INSERTING then
        select reqfc_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

create or replace trigger freviewer_pk_trig
before insert or update on freviewer
for each row
begin
    if INSERTING then
        select freviewer_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/


create or replace trigger fattach_pk_trig
before insert or update on fattach
for each row
begin
    if INSERTING then
        select fattach_seq.nextVal into :new.id from dual;
    elsif UPDATING then
        select :old.id into :new.id from dual;
    end if;
end;
/

commit;