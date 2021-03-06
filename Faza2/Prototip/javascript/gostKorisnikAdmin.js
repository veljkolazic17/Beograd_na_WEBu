// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {

    // otvaranje originalnog sajta za konkretni smeštaj
    document.getElementById("linkNaSlici").addEventListener('click', function() {
        window.open("https://www.4zida.rs/", "_blank");
    });

    // podesavanje prikazanih smestaja da prikazu svoju sliku na panelu za konkretni smestaj
    var smestaji = document.getElementsByClassName("smestaji");
    for(let i = 0; i < smestaji.length; i++) {
        smestaji[i].addEventListener("click", function(ev) {
            document.getElementById("prikazStana").style.display = "block";
            var evTarget = ev.target;
            if(evTarget.getAttribute("class") != "smestaji")
                evTarget = evTarget.parentElement;
            document.getElementById("slikaKonkretnogSmestaja")
            .setAttribute("src", evTarget.children[0].getAttribute("src"));
        });
    }

    // prikaz panela za filtere
    document.getElementById("dugmeFilteri").addEventListener('click', function() {
        document.getElementById("filteriPanelPozadina").style.display = "block";
    });

    // izlaz iz panela za prikaz konkretnog stana
    var emptyHeart = "<svg version=\"1.1\" class=\"Layer_1\" xmlns=\"http://www.w3.org/2000/svg\"" +
    "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" viewBox=\"0 0 500 472.44\"" +
    " enable-background=\"new 0 0 500 472.44\" xml:space=\"preserve\"><path fill=\"#F5F0F6\"" +
    " stroke=\"#F5F0F6\" stroke-width=\"2\" stroke-miterlimit=\"10\"" + 
    " d=\"M53.4,260.54C-88.12,122.11,105.22-75.54,246.74,62.89c136.1-139.14,340.48,60.77,204.37,199.91l-97.58,99.76l-95.76,97.89L53.4,260.54z\"/>" + 
    "<path fill=\"#FFFFFF\" stroke=\"#010202\" stroke-miterlimit=\"10\" d=\"M148.51,184.31\"/><path fill=\"#FFFFFF\" stroke=\"#010202\"" + 
    " stroke-miterlimit=\"10\" d=\"M282.97,115.94\"/></svg>";
    var fullHeart = "<svg version=\"1.1\" class=\"Layer_2\" xmlns=\"http://www.w3.org/2000/svg\" " + 
    "xmlns:xlink=\"http://www.w3.org/1999/xlink\" x=\"0px\" y=\"0px\" " +
    "viewBox=\"0 0 500 472.44\" enable-background=\"new 0 0 500 472.44\" xml:space=\"preserve\">" + 
    "<path fill=\"#DB1549\" stroke=\"#F5F0F6\" stroke-width=\"2\" stroke-miterlimit=\"10\" d=\"M53.4,260.54 " +
    "C-88.12,122.11,105.22-75.54,246.74,62.89c136.1-139.14,340.48,60.77,204.37,199.91l-97.58,99.76l-95.76,97.89L53.4,260.54z\"/></svg>";
    document.getElementById("prikazStana").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("prikazStana").style.display = "none";
        // izbaciti/promeniti u konacnoj verziji. Brise komentare i lajkove u svrhu prototipa.
        var rezimGosta = document.getElementById("komentariPanel").getAttribute("name");
        while(document.getElementsByClassName("komentari").length != 0) {
            if(rezimGosta != undefined) break; // za rezim gosta, ne brisu se komentari.

            if(document.getElementsByClassName("komentari")[0].children[0].tagName.toLowerCase() == "textarea") {
                document.getElementsByClassName("komentari")[0].children[1].children[1].click();
                break;
            }

            document.getElementById("komentariPanel")
            .removeChild(document.getElementsByClassName("komentari")[0]);
        }
        
        if(document.getElementById("dugmeZaKomentare").innerHTML.includes("Sakrij"))
            document.getElementById("dugmeZaKomentare").click();
        document.getElementById("lajkNaSlici").children[1].innerHTML = 0;
        document.getElementById("lajkNaSlici").children[0].innerHTML = emptyHeart;
    });

    // dugme za prikazivanje komentara
    document.getElementById("dugmeZaKomentare").addEventListener("click", function(ev) {
        var element = document.getElementById("komentariPanel");
        var dugme = document.getElementById("dugmeZaKomentare");

        if(window.getComputedStyle(element).display == "block") {
            element.style.display = "none";
            dugme.children[0].innerHTML = "Prikaži komentare";
            dugme.style.borderBottomLeftRadius = "20px";
            dugme.style.borderBottomRightRadius = "20px";
        } else {
            element.style.display = "block";
            dugme.children[0].innerHTML = "Sakrij komentare";
            dugme.style.borderBottomLeftRadius = "0";
            dugme.style.borderBottomRightRadius = "0";
        }
    });

    // izlaz iz panela za filtere
    document.getElementById("filteriPanelPozadina").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("filteriPanelPozadina").style.display = "none";
    });

    // ciscenje svih filtera
    function ocistiFiltere(ev) {
        var filterLista = document.getElementById("filteriPanel");
        var sviInputi = filterLista.getElementsByTagName("input");
        for (let i = 0; i < sviInputi.length; i++) {
            switch(sviInputi[i].getAttribute("type")) {
                case "text":
                    sviInputi[i].value = "";
                    break;
                case "checkbox":
                    sviInputi[i].checked = false;
                    break;
            }
        }
        var sviSelecti = filterLista.getElementsByTagName("select");
        for (let i = 0; i < sviSelecti.length; i++) {
            sviSelecti[i].selectedIndex = 0;
        }

        document.getElementById("izabraniFilteriWrapper").style.display = "none"; 
    }

    document.getElementById("ocistiFiltereDugme").addEventListener("click", ocistiFiltere);

    // primena filtera
    document.getElementById("potvrdiFiltereDugme").addEventListener("click", function(ev) {
        document.getElementById("izabraniFilteri").innerHTML = "&times;&nbsp;...";
        document.getElementById("izabraniFilteriWrapper").style.display = "block"; 
        document.getElementById("filteriPanelPozadina").style.display = "none";
    });

    // brisanje odrednjenog filtera
    document.getElementById("izabraniFilteriWrapper").addEventListener("click", function(ev) {
        document.getElementById("izabraniFilteriWrapper").style.display = "none";
        ocistiFiltere(null);
    });
});
