/**
 * 
 */

window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
}



console.log("in full review js");
employee = JSON.parse(localStorage.getItem("empl"));
console.log(employee);  //reviewer's info here
let getfid = parseInt(localStorage.getItem("formid"),10); //parse to integer
console.log(getfid);
let fmarr = JSON.parse(localStorage.getItem("fobjs"));
console.log(localStorage);

fmarr.forEach(item => {
	if (item.id == getfid){ // get that object corresponding to formid number chosen
		document.getElementById("f_formid").value = item.id;
		document.getElementById("f_submitter").value = item.empId;
		//now set the userid based on the formid above
		// fetch info using xttp request to get info on the employee or submitter
		let empobj = getEmpbyId(item.empId);  //get the return obj for employee - doesn't work
//		console.log("empobj is ");
//		console.log(empobj);
		console.log("submit person is in local storage? ");
		console.log(localStorage);
		let sp = JSON.parse(localStorage.getItem('submitperson'));  //get access to local storage
		let ep = JSON.parse(localStorage.getItem('empl'));  //get access to local storage
		
		document.getElementById("f_name").value = sp.name;  //need to fetch this
		document.getElementById("f_supervisor").value = ep.id; //the employee reviewing this is the supervisor
		document.getElementById("f_deptheadid").value = sp.deptHId; // this person reviews it next
		document.getElementById("f_deptid").value = sp.dept;
	
		// date stuff - arrgghhh
		let today = new Date();
		let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
		document.getElementById("f_date").value = date;  //see if this works
		document.getElementById("f_location").value = item.empLoc;
		document.getElementById("f_reqamt").value = item.reqAmt;
		document.getElementById("f_reimbamt").value = item.reimbAmt;
		document.getElementById("f_gradefmt").selectedIndex = item.gradeFmt;
		
	}
//console.log(item.empId);
});


function getEmpbyId(id){
	baseURL = '/twib/';
	console.log("executing get employee info");
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = recEmp;
	xhttp.open('POST', baseURL + 'getEmpInfo');  //go to servlet getEmpInfo
	xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	let empid = id;
	xhttp.send('empid=' + empid ); // x-www-form-urlencoded


function recEmp(){ //make sure this function is within above function other xttp request undefined
	if (xhttp.readyState === 4 && xhttp.status === 200) {
        let data = JSON.parse(xhttp.responseText);
        console.log(data);
 //       let submitter = data.employee;
        localStorage.setItem("submitperson",JSON.stringify(data));
 //       return submitter;
	}
}
}