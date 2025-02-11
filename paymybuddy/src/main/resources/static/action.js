function showAlert() {
    alert("The button was clicked!");
}

function createAccount(){ 
	alert("createAccount");
}

function logIn(){ 
	alert("logIn");
}

 document.addEventListener("DOMContentLoaded", function () {
        let errorToast = document.getElementById("errorToast");
        if (errorToast) {
            setTimeout(() => {
                errorToast.style.opacity = "0"; // Réduit l'opacité
                setTimeout(() => {
                    errorToast.style.display = "none"; 
                }, 2000);
            }, 3500);
        }
    });