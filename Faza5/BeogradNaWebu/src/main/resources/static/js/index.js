/**
 * Marko Mirković 2019/197
 * Matija Milosevic 2019/0156
 * Jelena Lucic 2019/0268
 * Veljko Lazic 2019/0241
 */

document.addEventListener("DOMContentLoaded", function() {
    function windowResize() {
        var leviPanel = document.getElementById("leviPanel");
        var leviPanelWidth = getComputedStyle(leviPanel).width;
        leviPanelWidth = parseInt(leviPanelWidth.substring(0, leviPanelWidth.length - 1));

        var desniPanelWrapper = document.getElementById("desniPanelWrapper");
        var desniPanelWrapperWidth = getComputedStyle(desniPanelWrapper).width;
        desniPanelWrapperWidth = parseInt(desniPanelWrapperWidth.substring(0, desniPanelWrapperWidth.length - 1));

        if(document.body.clientWidth - leviPanelWidth <= desniPanelWrapperWidth) {
            leviPanel.style.marginLeft = "-" + leviPanelWidth + "px";
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
        var korisnickoIme = document.getElementById("zaboravljenaSifraPanelUsername").value;
        $.ajax({url: "userdata/passwordresetvialemail/" + korisnickoIme, type: "POST"})
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
    var desniPanelPokrivac = document.getElementById("desniPanelPokrivac");
    desniPanel.style.animationName = "animacijaLoginNestaje";
    desniPanel.style.animationPlayState = "running";
    desniPanelPokrivac.style.animationName = "animacijaLoginNestaje";
    desniPanelPokrivac.style.animationPlayState = "running";
}


function animacijaRegistracijeNestaje() {
    var desniPanel = document.getElementById("desniPanel");
    var desniPanelPokrivac = document.getElementById("desniPanelPokrivac");
    desniPanel.style.animationName = "animacijaLoginNastaje";
    desniPanel.style.animationPlayState = "running";
    desniPanelPokrivac.style.animationName = "animacijaLoginNastaje";
    desniPanelPokrivac.style.animationPlayState = "running";
}
