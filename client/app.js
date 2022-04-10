document.getElementById('submit').addEventListener("click", loadText)


function loadText() {
    var xhr = new XMLHttpRequest();

    var form = document.forms['formId'];
    left = form.elements['leftOperand'].value;
    right = form.elements['rightOperand'].value;
    operation = form.elements['operation'].value;

    var url = 'http://localhost:8080/?leftOperand=' + left + '&rightOperand=' + right + '&operation=' + operation;

    xhr.open('GET', url, true)
    xhr.onload = function () {
        if (this.status == 200) {
            var solution = JSON.parse(xhr.responseText)

            document.getElementById('exp').innerHTML = solution.expression
            document.getElementById('res').innerHTML = solution.result
        }
    }
    xhr.send()
    
}