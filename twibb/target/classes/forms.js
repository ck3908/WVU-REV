window.onload = () => {
    console.log("On the Forms page.")
    addNavBar();
    getForms();
}

//not used this file in this application

// let xhttp2 = new XMLHttpRequest();
// xhttp2.onreadystatechange = parseEmployee;
// xhttp2.open('GET', 'employeeData');
// xhttp2.send();
// function parseEmployee() {
//     if (xhttp2.readyState === 4 && xhttp2.status === 200) {
//         let employees = xhttp2.responseText;
//         employees = JSON.parse(employees);
//         //console.log(books);
//         //forms.sort((b1, b2) => b1.id.localeCompare(b2.id));
//         console.log(employees);

//     }
// }

function getForms() {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = parseForm;
    xhttp.open('GET', 'formData');
    xhttp.send();
    function parseForm() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            let forms = xhttp.responseText;
            forms = JSON.parse(forms);
            //console.log(books);
            //forms.sort((b1, b2) => b1.id.localeCompare(b2.id));
            console.log(forms);
            forms.forEach(form => {
                addFormToTable(form);
            });
        }
    }
}

function addFormToTable(form) {
    let table = document.getElementById('forms');
    let tr = document.createElement('tr');
    let td;

    addTableDef(tr, form.id);
    addTableDef(tr, employees.first_name)
    addTableDef(tr, form.description);
    addTableDef(tr, form.date);
    td = document.createElement('td');
    let infoButton = document.createElement("button");
    tr.appendChild(td);
    td.appendChild(infoButton);
    infoButton.innerHTML = "More info.";
    infoButton.className = "btn btn-secondary emp-btn";
    infoButton.id = 'i_f_' + form.id;
    infoButton.addEventListener("click", infoForm);
    infoButton.disabled = employee ? false : true;

    table.appendChild(tr);
}

function addTableDef(tr, value) {
    let td = document.createElement('td');
    td.innerHTML = value;
    tr.appendChild(td);
}

function addListToTable(tr, array, display) {
    let td = document.createElement('td');
    let ul = document.createElement('ul');
    for (let i = 0; i < array.length; i++) {
        let li = document.createElement('li');
        li.innerHTML = display(array[i]);
        ul.appendChild(li);
    }
    td.appendChild(ul);
    tr.appendChild(td);
}

function infoForm() {
    let btn = event.target;
    console.log(btn);
    var id = btn.id.substring('i_f_'.length);
    console.log(id);
    window.location.href = 'infoForm/' + id;
}
