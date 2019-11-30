/**
 * 
 */
window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
}

console.log("in review js");
employee = JSON.parse(localStorage.getItem("empl"));
console.log(employee);

baseURL = '/twib/';
console.log("executing get all formsid to review in js");
let xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = gotInfo;
xhttp.open('POST', baseURL + 'getRevFormIds');
xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
let reviewerId = employee.id;

//send the request

xhttp.send('reviwerId=' + reviewerId ); // x-www-form-urlencoded

function gotInfo() {
	 if (xhttp.readyState === 4 && xhttp.status === 200) {
//       let data = JSON.parse(xhttp.responseText);
//       console.log(data);
       console.log("got info, now do something with it");
	
	 }
}



	let mountains = [
	  { name: "Monte Falco", height: 1658, place: "Parco Foreste Casentinesi" },
	  { name: "Monte Falterona", height: 1654, place: "Parco Foreste Casentinesi" },
	  { name: "Poggio Scali", height: 1520, place: "Parco Foreste Casentinesi" },
	  { name: "Pratomagno", height: 1592, place: "Parco Foreste Casentinesi" },
	  { name: "Monte Amiata", height: 1738, place: "Siena" }
	];
	function generateTableHead(table, data) {
	  let thead = table.createTHead();
	  let row = thead.insertRow();
	  for (let key of data) {
	    let th = document.createElement("th");
	    let text = document.createTextNode(key);
	    th.appendChild(text);
	    row.appendChild(th);
	  }
	}
	function generateTable(table, data) {
	  for (let element of data) {
	    let row = table.insertRow();
	    for (key in element) {
	      let cell = row.insertCell();
	      let text = document.createTextNode(element[key]);
	      cell.appendChild(text);
	    }
	  }
	}
	
	let table = document.querySelector("table");
	let data = Object.keys(mountains[0]);
	generateTableHead(table, data);
	generateTable(table, mountains);
