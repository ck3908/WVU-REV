/**
 * 
 */

window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
}

baseURL = '/twib/';
console.log("in full review js");
employee = JSON.parse(localStorage.getItem("empl"));
console.log(employee);  //reviewer's info here
let getfid = parseInt(localStorage.getItem("formid"),10); //parse to integer
console.log(getfid);
let fmarr = JSON.parse(localStorage.getItem("fobjs"));

fmarr.forEach(item => {
console.log(item.empId);
});