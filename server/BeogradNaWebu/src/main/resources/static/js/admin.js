// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {

    let postavljenEventHandlerAdmin = false;
    document.getElementById("ukljanjanjeKorisnikaDugme").addEventListener("click", function() {
        let pozadina = document.getElementById("prozorZaPotvrduPozadina");
        pozadina.style.display = "block"

        let panelAdmina = document.getElementById("panelAdmin");
        panelAdmina.style.display = "block";
        aktivniProzor = panelAdmina;

        if(!postavljenEventHandlerAdmin) {
            postavljenEventHandlerAdmin = true;

            let dugmeOtkazi = panelAdmina.getElementsByClassName("prozorZaPotvrduDugmadWrapper")[0].children[0];
            dugmeOtkazi.addEventListener("click", function() {
                panelAdmina.style.display = "none";
                pozadina.style.display = "none";
            });

            let korisniciZaBrisanje = panelAdmina.getElementsByClassName("korisniciBrisanje");
            for(let korisnik of korisniciZaBrisanje) {
                korisnik.addEventListener("dblclick", function(ev) {
                    // kod za AJAX
                    korisnik.remove();
                    ev.stopPropagation();
                });
            }
        }
    });
});