// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {

    // potvrda pretplate
    document.getElementById("ukljanjanjeKorisnikaDugme").addEventListener("click", function() {
        var prozor = document.getElementById("prozorZaPotvrdu");

        while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            prozor.children[0].remove();

        prozor.style.height = "500px";
        prozor.style.top = "calc(50vh - 500px / 2)";
        prozor.style.width = "300px";

        var divWrapper = document.createElement("div");
        divWrapper.style.width = "100%";
        divWrapper.style.minHeight = "444px";
        prozor.insertBefore(divWrapper, prozor.children[prozor.children.length - 1]);

        var dugme = document.createElement("input");
        dugme.setAttribute("type", "button");
        dugme.style.width = "100%";
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugme);

        dugme.value = "Izađi";
        dugme.addEventListener("click", function() {
            dugme.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        // prototip: korisnik za uklanjanje
        var korisnik = document.createElement("p");
        korisnik.innerHTML = "korisnik";
        korisnik.setAttribute("class", "korisniciBrisanje");
        korisnik.addEventListener("dblclick", function() {
            korisnik.remove();
        });
        divWrapper.append(korisnik);

        document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
    });
});