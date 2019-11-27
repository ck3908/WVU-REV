/**
 * 
 */

window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
}
console.log("is this true");
employee = JSON.parse(localStorage.getItem("empl"));
console.log(employee);

console.log("is this golbal priting supId");
console.log(employee.supId);
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