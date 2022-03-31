// Marko Mirković 197/19
document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("zaboravljenaSifra").addEventListener('click', function() {
        document.getElementById("zaboravljenaSifraPozadina").style.display = "block";
        document.getElementById("zaboravljenaSifraPanelUsername").value = "";
    });
});


function predjiNaStranicu(register) {
    if(register == "register")
        window.open("glavnaStranicaKorisnik.html", "_self");
    if(document.getElementById("username").value == "admin")
        window.open("glavnaStranicaAdmin.html", "_self");
    else if(document.getElementById("username").value == "korisnik")
        window.open("glavnaStranicaKorisnik.html", "_self");
}


function animacijaRegistracijeNastaje() {
    document.getElementById("desniPanelWrapper").style.animationName = "animacijaRegistracijaWrapperGore";
    document.getElementById("desniPanelWrapper").style.animationPlayState = "running";
    
    document.getElementById("desniPanelRegistracija").style.animationName = "animacijaRegistracijaNastaje";
    document.getElementById("desniPanelRegistracija").style.animationPlayState = "running";

    document.getElementById("desniPanel").style.animationName = "animacijaLoginNestaje";
    document.getElementById("desniPanel").style.animationPlayState = "running";
}


function animacijaRegistracijeNestaje() {
    document.getElementById("desniPanelWrapper").style.animationName = "animacijaRegistracijaWrapperDole";
    document.getElementById("desniPanelWrapper").style.animationPlayState = "running";

    document.getElementById("desniPanelRegistracija").style.animationName = "animacijaRegistracijaNestaje";
    document.getElementById("desniPanelRegistracija").style.animationPlayState = "running";

    document.getElementById("desniPanel").style.animationName = "animacijaLoginNastaje";
    document.getElementById("desniPanel").style.animationPlayState = "running";

}


document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("zaboravljenaSifraPozadina").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("zaboravljenaSifraPozadina").style.display = "none";
    });
});


document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("zaboravljenaSifraPotvrdi").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("zaboravljenaSifraPozadina").style.display = "none";
    });
});
