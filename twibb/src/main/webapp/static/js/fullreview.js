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

// get form status first


var baseURL = '/twib/';
let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = recStat;
xhttp.open('POST', baseURL + 'getStatusInfo');  //go to servlet getStatusInfo in request dispatcher
xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
xhttp.send('getfid=' + getfid ); // x-www-form-urlencoded

function recStat(){ //make sure this function is within above function other xttp request undefined
	if (xhttp.readyState === 4 && xhttp.status === 200) {
        let stat = JSON.parse(xhttp.responseText);
        console.log(stat);
        document.getElementById("status").selectedIndex = stat-1;
        
	}
}

// get rest of form info 

fmarr.forEach(item => {
	if (item.id == getfid){ // get that object corresponding to formid number chosen
		document.getElementById("f_formid").value = item.id;
		document.getElementById("f_submitter").value = item.empId;
		//now set the userid based on the formid above
		// fetch info using xttp request to get info on the employee or submitter
		let empobj = getEmpbyId(item.empId);  //get the return obj for employee - doesn't work
//		console.log("empobj is ");
//		console.log(empobj);
		
		// date stuff - arrgghhh
		let today = new Date();
		let date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+'0'+today.getDate(); //add the zero to make it 2 digits, fix later
		document.getElementById("f_date").value = date;  //see if this works
		document.getElementById("f_location").value = item.empLoc;
		document.getElementById("f_reqamt").value = item.reqAmt;
		document.getElementById("f_reimbamt").value = item.reimbAmt;
		document.getElementById("purpose").selectedIndex = item.gradeFmt-1; //javascript or html drop down starts at 0		
		
//		console.log("submit person is in local storage? ");
//		console.log(localStorage);
//		let sp = JSON.parse(localStorage.getItem('submitperson'));  //get access to local storage
//		console.log(sp);
		let ep = JSON.parse(localStorage.getItem('empl'));  //get access to local storage
		document.getElementById("f_supervisor").value = ep.id; //the employee reviewing this is the supervisor	
		
	}
//console.log(item.empId);
});


function getEmpbyId(id){
//	baseURL = '/twib/';
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
        let sp = data   
        document.getElementById("f_name").value = sp.name;  //need to fetch this	
		document.getElementById("f_deptheadid").value = sp.deptHId; // this person reviews it next
		document.getElementById("f_deptid").value = sp.dept;       
        
 //       let submitter = data.employee;
 //       localStorage.setItem("submitperson",JSON.stringify(data));
 //       return submitter;
	}
}
}

document.getElementById('submitnow').addEventListener('click', function(submitForm){
	console.log("executing submitform by a supervisor in js");
	//check status change of form
	 
	 let fmid = document.getElementById("f_formid").value;
	 let submitter = document.getElementById("f_submitter").value;
	 let st = stat.options[stat.selectedIndex].value;
	if (st != 3){ //pending state didn't change then don't do upstate status table
		updateStaTable(fmid,submitter,st); //all others will update status table		
	}
	if (st == 1){
		console.log("executing rejection");
		let reason = document.getElementById('askcom').value;
		console.log(reason);
		let rejector = document.getElementById("f_supervisor").value;  //supervisor/depthead id rejecting the request
		rejFormReq(fmid,rejector,reason);
	}
	else if (st == 2){
		console.log("executing more info require");
		let question = document.getElementById('askcom').value;  //save both questions and answers  
		let answer = document.getElementById('anscom').value;  //probably need to append info
		let askerid = document.getElementById("f_supervisor").value;
		reqForCom(askid,fmid,question,answer);
	}
	else if (st > 3 && st <= 6){
		console.log("executing approval");
		// check if current approver not a department head
		if (st == 4 && document.getElementById("f_supervisor").value != document.getElementById("f_deptheadid").value) {
			// set form approved by supervisor, set form to review for the department head
			let approveId = document.getElementById("f_supervisor").value;
			let approveDt = document.getElementById("f_date").value;
			let deptHeadId = document.getElementById("f_deptheadid").value;  //need this for the other table form reviewer table
			let override = 0; //only HR can override
			partApprove(submitter,fmid,approveId,approveDt,override,deptHeadId);
		}
	}
	else{
		//nothing
	}
	
	function partApprove(submitter,fmid,approveId,approveDt,override,deptHeadId){
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = approveDone;
	    xhttp.open('POST', baseURL + 'ThisStepApproved'); // used in the delegates for servlets
	    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhttp.send('submitter=' + submitter + '&fmid=' + fmid 
	    		+ '&approveId=' + approveId + '&approveDt=' + approveDt 
	    		+ '&override=' + override + '&deptHeadId=' + deptHeadId); // x-www-form-urlencoded
		
	    function approveDone(){
	    	if (xhttp.readyState === 4 && xhttp.status === 200) {
//	            let data = JSON.parse(xhttp.responseText);
//	            console.log(data);
	            console.log("successful request approval for this step");
	           // window.location.href = 'emplogin.html';
	    	 }
	    	
	    } //function approveDone 
		
		
		
	} // end function Approve
	
	function reqForCom(askid,fmid,question,answer){
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = rfcDone;
	    xhttp.open('POST', baseURL + 'rfcRequest'); 
	    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhttp.send('askid=' + askid + '&fmid=' + fmid 
	    		+ '&question=' + question + '&answer=' + answer); // x-www-form-urlencoded
		
	    function rejUpdated(){
	    	if (xhttp.readyState === 4 && xhttp.status === 200) {
//	            let data = JSON.parse(xhttp.responseText);
//	            console.log(data);
	            console.log("successful request for comment done");
	           // window.location.href = 'emplogin.html';
	    	 }
	    	
	    } //function rejUpdated done
		
		
	}
	
	
	function rejFormReq(fmid,rejector,reason){
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = rejUpdated;
	    xhttp.open('POST', baseURL + 'rejForm'); 
	    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhttp.send('fmid=' + fmid + '&rejector=' + rejector 
	    		+ '&reason=' + reason); // x-www-form-urlencoded
	    
	    
	    function rejUpdated(){
	    	if (xhttp.readyState === 4 && xhttp.status === 200) {
//	            let data = JSON.parse(xhttp.responseText);
//	            console.log(data);
	            console.log("successful rejection");
	           // window.location.href = 'emplogin.html';
	    	 }
	    	
	    }
		
	}
	
	function updateStaTable(fmid,submitter,status){  //update status table
		let xhttp = new XMLHttpRequest();
	    xhttp.onreadystatechange = statUpdated;
	    xhttp.open('POST', baseURL + 'updateStatus');  //this will get mapped to delegates - servlets
	    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	    xhttp.send('submitter=' + submitter + '&fmid=' + fmid 
	    		+ '&status=' + status); // x-www-form-urlencoded
	    
	    function statUpdated(){
	    	
	    	 if (xhttp.readyState === 4 && xhttp.status === 200) {
//	            let data = JSON.parse(xhttp.responseText);
//	            console.log(data);
	            console.log("successful status code change");
	           // window.location.href = 'emplogin.html';
	    	 }
	    	 else{
	    		 if (xhttp.status >= 400) {
	                    reject(Error("Status wasn't changed something wrong"));
	                }
	    	 }
	    
	    
	    }  //end statupdated
	
	
	}
	
	
} );  //end event handler
