/**
 * 
 */

window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
}
//console.log("is this true");
employee = JSON.parse(localStorage.getItem("empl"));
console.log(employee);

document.getElementById("f_submitter").value = employee.id;
document.getElementById("f_supervisor").value = employee.supId;
document.getElementById("f_deptheadid").value = employee.deptHId;
document.getElementById("f_deptid").value = employee.dept;

var today = new Date();
//var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
//var time = today.getHours() + ":" + today.getMinutes() + ":" + today.getSeconds();
//var dateTime = date+' '+time;
document.getElementById("f_date").valueAsDate = today;
//var newemployee = JSON.parse(window.localStorage.getItem("employobj"));

//document.getElementById('submitbtn').addEventListener('submit', submitForm());
event.stopPropagation();

baseURL = '/twib/';

function submitForm() {
	console.log("executing submitform in js");
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = submitSuccess;
    xhttp.open('POST', baseURL + 'formSubmit');
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    let submitter = document.getElementById('f_submitter').value;
    console.log("printing submitter");
    console.log(submitter);
    let supervisor = document.getElementById('f_supervisor').value;
    let formdate = document.getElementById('f_date').value;
    let formloc = document.getElementById('f_location');
    console.log(formloc);
    let reqamt = document.getElementById('f_reqamt').value;
    let gradefmt = document.getElementById('f_gradefmt');
    
    xhttp.send('submitter=' + submitter + '&supervisor=' + supervisor 
    		+ '&formdate=' + formdate + '&formloc=' + formloc 
    		+ '&reqamt=' +reqamt + '&gradefmt=' + gradefmt); // x-www-form-urlencoded

    function submitSuccess() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
//            let data = JSON.parse(xhttp.responseText);
//            console.log(data);
            console.log("successful submission")
//            let employee = data.employee;
//            localStorage.setItem("empl",JSON.stringify(employee));
//           
//          //  customer = data.customer;
//            document.getElementById("authent").innerHTML = authenticated;
//            document.getElementById('logout').addEventListener('click', logout);    
//                if (employee) {
//                    document.getElementById("authUserName").innerHTML = employee.name + " of dept " + employee.dept;
//                    let btns = document.getElementsByClassName("emp-btn");
//                    for (let i = 0; i < btns.length; i++) {
//                        btns[i].disabled = false;
//                    }
//                }
        }
    }
}