/*
This file is going to make sure that someone is logged in on whatever page.
and also allow people to log in.
*/
var navbar = `
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="navbar-brand">
        <span class="badge-primary">employee reimburse</span>
    </a>
	<ul class="navbar-nav mr-auto" id="authent">
	</ul>
</nav>`;
var unauthenticated = `
		<li class="nav-item">
			Username: <input type="text" id="username">
		</li>
		<li class="nav-item">
			Password: <input type="password" id="password">
		</li>
		<li class="nav-item">
			<button class="btn btn-primary" id="login">Login</button>
		</li>
        `;
var authenticated = `
		<li class="nav-item">
			Welcome <span id="authUserName"></span> 
		</li>
		<li class="nav-item">
			<button class="btn btn-danger" id="logout">Logout</button>
        </li>`;

employee = null;
customer = null;
baseURL = '/twib/';

function addNavBar() {
    let div = document.createElement('div');
    div.innerHTML = navbar;
    let body = document.getElementsByTagName('body')[0];
    body.insertBefore(div, body.childNodes[0]);
    document.getElementById('authent').innerHTML = unauthenticated;

    // add event listeners
    document.getElementById('login').addEventListener('click', authenticate);
    document.getElementById('password').onkeydown = checkPasswordEnter;
    authenticate();
}

function checkPasswordEnter() {
    if (event.which === 13)
        authenticate();
}
function authenticate() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = loginSuccess;
    xhttp.open('POST', baseURL + 'login');
    xhttp.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
    let user = document.getElementById('username').value;
    let pass = document.getElementById('password').value;
    xhttp.send('user=' + user + '&pass=' + pass); // x-www-form-urlencoded

    function loginSuccess() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            let data = JSON.parse(xhttp.responseText);
            console.log(data);
            let employee = data.employee;
            localStorage.setItem("empl",JSON.stringify(employee));
           
          //  customer = data.customer;
            document.getElementById("authent").innerHTML = authenticated;
            document.getElementById('logout').addEventListener('click', logout);    
                if (employee) {
                    document.getElementById("authUserName").innerHTML = employee.name + " of dept " + employee.dept;
                    let btns = document.getElementsByClassName("emp-btn");
                    for (let i = 0; i < btns.length; i++) {
                        btns[i].disabled = false;
                    }
                }
        }
    }
}

function logout() {
    console.log("logging out");
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = finish;
    xhttp.open("DELETE", baseURL + "login");
    xhttp.send();

    function finish() {
        if (xhttp.readyState === 4 && xhttp.status === 204) {
            document.getElementById("authent").innerHTML = unauthenticated;
            let btns = document.getElementsByClassName("emp-btn");
            for (let i = 0; i < btns.length; i++) {
                btns[i].disabled = true;
            }
            document.getElementById("login").addEventListener("click", authenticate);
            document.getElementById("password").onkeydown = checkPasswordEnter;
        }
    }
  //  window.location.href = 'emplogin.html';  //go back to login page not working yet
}