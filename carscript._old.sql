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
	id number(20)  primary key,		
	platenum number(20),  -- referenced as foreign key from the many to many table
	nameperson varchar2(20) not null, --- so we can use in the many to many table
	offerprice number(20),
	status varchar2(10),
	downpayment number(20),
	findeal number(10)
	);
	
  create table cardetails (
	id number(20) primary key,
	plate number(20) unique not null,
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
---------------------------------------

-- INSERT INTO Genre (GenreId, Name) VALUES (1, 'Rock');
-- INSERT INTO MediaType (MediaTypeId, Name) VALUES (1, 'MPEG audio file');

insert into userinfo (id, name, password, code) values (1, 'robert gan','pass','cust');
insert into userinfo (id, name, password, code) values (1, 'tom hanks','pass','cust');
insert into userinfo (id, name, password, code) values (1, 'jim jones','pass','emp');


insert into cardetails(plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (55, 'toyota', 'false', 35000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (88, 'ford mustang', 'false', 45000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (23, 'ford fiesta', 'false', 26000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (18, 'nissan rouge', 'false', 32000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (33, 'honda civic', 'true', 27000, 5000, 5000, 1, 800, 48, 26500);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (17, 'ford focus', 'true', 23000, 4000, 8000, 2, 650, 45, 13000);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (99, 'subaru forrester', 'true', 21000, 4000, 19000, 3, 700, 35, 12000);


insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal) values 
                    (1, 88, 'tom hanks', 39000,'pending',5000,1);
insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal) values 
                    (1, 23, 'tom hanks', 25000,'pending',700,1);  
insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal) values 
                    (1, 88, 'robert gan', 39000,'pending',7000,1); 
                    
insert into usercars (username,carplate) values ('tom hanks',17);
insert into usercars (username,carplate) values ('tom hanks',33);
insert into usercars (username,carplate) values ('robert gan',99);

commit;                


select * from usercars;

drop table usecars;
drop table usecar;

select * from cardetails;
select carname, plate, offersold from cardetails where owned = 'false';

select * from userinfo;

select * from usercars where username = 'tom hanks';

select * from cardetails where plate in (select carplate from usercars where username = 'tom hanks');

