
--- insert info after db structure have been created

insert into userinfo (id, name, password, code) values (1, 'robert gan','pass','cust');
insert into userinfo (id, name, password, code) values (1, 'tom hanks','pass','cust');
insert into userinfo (id, name, password, code) values (1, 'jim jones','pass','emp');


insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1,55, 'toyota', 'false', 35000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 88, 'ford mustang', 'false', 45000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 23, 'ford fiesta', 'false', 26000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 18, 'nissan rouge', 'false', 32000, 0, 0, 0, 0, 0, 0);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 33, 'honda civic', 'true', 27000, 5000, 5000, 1, 800, 48, 26500);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 17, 'ford focus', 'true', 23000, 4000, 8000, 2, 650, 45, 13000);
insert into cardetails(id, plate, carname, owned, offersold, downpayment, totalpaid, loantype, monthlydue, loanterm, principalbal) values 
                    (1, 99, 'subaru forrester', 'true', 21000, 4000, 19000, 3, 700, 35, 12000);


insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal, carname) values 
                    (1, 88, 'tom hanks', 39000,'pending',5000,1,'ford mustang');
insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal, carname) values 
                    (1, 23, 'tom hanks', 25000,'pending',700,1,'ford fiesta');  
insert into caroffer (id, platenum, nameperson, offerprice, status, downpayment, findeal, carname) values 
                    (1, 88, 'robert gan', 39000,'pending',7000,1,'ford mustang'); 
					
insert into ownercars (username,carplate) values ('tom hanks',17);
insert into ownercars (username,carplate) values ('tom hanks',33);
insert into ownercars (username,carplate) values ('robert gan',99);
                    

commit; 