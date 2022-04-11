// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {
    function windowResize() {
        var leviPanel = document.getElementById("leviPanel");
        var leviPanelWidth = getComputedStyle(leviPanel).width;
        leviPanelWidth = parseInt(leviPanelWidth.substring(0, leviPanelWidth.length - 1));
        if(leviPanelWidth > window.innerWidth * 0.6) {
            leviPanel.style.marginLeft = "-200vw";
        } else {
            leviPanel.style.marginLeft = "0";
        }
    }
    
    windowResize();
    window.addEventListener('resize', windowResize);

    document.getElementById("zaboravljenaSifra").addEventListener('click', function() {
        document.getElementById("zaboravljenaSifraPozadina").style.display = "block";
        document.getElementById("zaboravljenaSifraPanelUsername").value = "";
    });

    document.getElementById("zaboravljenaSifraPozadina").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("zaboravljenaSifraPozadina").style.display = "none";
    });

    document.getElementById("zaboravljenaSifraPotvrdi").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("zaboravljenaSifraPozadina").style.display = "none";
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
    var desniPanel = document.getElementById("desniPanel");
    desniPanel.style.animationName = "animacijaLoginNestaje";
    desniPanel.style.animationPlayState = "running";
}


function animacijaRegistracijeNestaje() {
    var desniPanel = document.getElementById("desniPanel");
    desniPanel.style.animationName = "animacijaLoginNastaje";
    desniPanel.style.animationPlayState = "running";
}
