// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {
    var lista;
    // promena glavnog prozora.
    document.getElementById("dugmeSviSmestaji").addEventListener("click", function() {
        document.getElementById("pregledSmestaja").style.display = "flex";
        document.getElementById("pregledPredlozenogSmestaja").style.display = "none";
        document.getElementById("dugmePredlozeniSmestaji").style.backgroundColor = "#bf6943";
        document.getElementById("dugmeSviSmestaji").style.backgroundColor = "#ef8354";

    });

    document.getElementById("dugmePredlozeniSmestaji").addEventListener("click", function prikaziSvePredlozeneSmestaje() {
        document.getElementById("pregledSmestaja").style.display = "none";
        document.getElementById("pregledPredlozenogSmestaja").style.display = "flex";

        document.getElementById("dugmePredlozeniSmestaji").style.backgroundColor = "#ef8354";
        document.getElementById("dugmeSviSmestaji").style.backgroundColor = "#bf6943";


    });

    // prikaz prozora za potvrdu
    document.getElementById("dugmeNalog").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("nalogPanelPozadina").style.display = "block";
    });

    // izlaz iz prozora za potvrdu 
    document.getElementById("prozorZaPotvrduPozadina").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        while(document.getElementById("prozorZaPotvrdu").children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            document.getElementById("prozorZaPotvrdu").children[0].remove();
        while(document.getElementById("prozorZaPotvrduDugmadWrapper").children.length > 0)
            document.getElementById("prozorZaPotvrduDugmadWrapper").children[0].remove();

        document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
    });

    // izlaz iz prozora za nalog
    document.getElementById("nalogPanelPozadina").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("nalogPanelPozadina").style.display = "none";
    });

    // promena sifre
    document.getElementById("promenaSifreDugme").addEventListener("click", function() {
        var prozor = document.getElementById("prozorZaPotvrdu");

        while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            prozor.children[0].remove();

        prozor.style.height = "250px";
        prozor.style.top = "calc(50vh - 250px / 2)";
        prozor.style.width = "440px";

        var div1 = document.createElement("div");
        div1.style.width = "100%";
        div1.style.height = "calc(33.333333% - 50px / 3)";
        div1.style.padding = "10px";
        var p1 = document.createElement("p");
        p1.innerHTML = "Stara šifra: ";
        div1.append(p1);
        var input1 = document.createElement("input");
        input1.setAttribute("type", "password");
        div1.append(input1);
        prozor.insertBefore(div1, prozor.children[prozor.children.length - 1]);

        var div2 = document.createElement("div");
        div2.style.width = "100%";
        div2.style.height = "calc(33.333333% - 50px / 3)";
        div2.style.padding = "10px";
        var p2 = document.createElement("p");
        p2.innerHTML = "Nova šifra: ";
        div2.append(p2);
        var input2 = document.createElement("input");
        input2.setAttribute("type", "password");
        div2.append(input2);
        prozor.insertBefore(div2, prozor.children[prozor.children.length - 1]);

        var div3 = document.createElement("div");
        div3.style.width = "100%";
        div3.style.height = "calc(33.333333% - 50px / 3)";
        div3.style.padding = "10px";
        var p3 = document.createElement("p");
        p3.innerHTML = "Ponovite novu šifru: ";
        div3.append(p3);
        var input3 = document.createElement("input");
        input3.setAttribute("type", "password");
        div3.append(input3);
        prozor.insertBefore(div3, prozor.children[prozor.children.length - 1]);

        var dugmeZaObustavljanje = document.createElement("input");
        var dugmeZaPotvrdu = document.createElement("input");
        dugmeZaObustavljanje.setAttribute("type", "button");
        dugmeZaPotvrdu.setAttribute("type", "button");
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaObustavljanje);
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaPotvrdu);

        dugmeZaObustavljanje.value = "Otkaži";
        dugmeZaPotvrdu.value = "Potvrdi";

        dugmeZaObustavljanje.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        dugmeZaPotvrdu.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
    });

    // promena email-a
    document.getElementById("promenaEmailaDugme").addEventListener("click", function() {
        var prozor = document.getElementById("prozorZaPotvrdu");

        while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            prozor.children[0].remove();

        prozor.style.height = "180px";
        prozor.style.top = "calc(50vh - 180px / 2)";
        prozor.style.width = "550px";

        var div1 = document.createElement("div");
        div1.style.width = "100%";
        div1.style.height = "calc(50% - 50px / 2)";
        div1.style.padding = "10px";
        var p1 = document.createElement("p");
        p1.innerHTML = "Stari email: ";
        div1.append(p1);
        var input1 = document.createElement("input");
        input1.setAttribute("type", "email");
        input1.style.width = "75%";
        div1.append(input1);
        prozor.insertBefore(div1, prozor.children[prozor.children.length - 1]);

        var div2 = document.createElement("div");
        div2.style.width = "100%";
        div2.style.height = "calc(50% - 50px / 2)";
        div2.style.padding = "10px";
        var p2 = document.createElement("p");
        p2.innerHTML = "Novi email: ";
        div2.append(p2);
        var input2 = document.createElement("input");
        input2.setAttribute("type", "email");
        input2.style.width = "75%";
        div2.append(input2);
        prozor.insertBefore(div2, prozor.children[prozor.children.length - 1]);

        var dugmeZaObustavljanje = document.createElement("input");
        var dugmeZaPotvrdu = document.createElement("input");
        dugmeZaObustavljanje.setAttribute("type", "button");
        dugmeZaPotvrdu.setAttribute("type", "button");
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaObustavljanje);
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaPotvrdu);

        dugmeZaObustavljanje.value = "Otkaži";
        dugmeZaPotvrdu.value = "Potvrdi";

        dugmeZaObustavljanje.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        dugmeZaPotvrdu.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();

            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";


        });

        document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
    });

    // potvrda pretplate
    document.getElementById("potvrdaPretplateDugme").addEventListener("click", function() {
        var prozor = document.getElementById("prozorZaPotvrdu");

        while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            prozor.children[0].remove();

        prozor.style.height = "130px";
        prozor.style.top = "calc(50vh - 130px / 2)";
        prozor.style.width = "550px";

        var div1 = document.createElement("div");
        div1.style.width = "100%";
        div1.style.height = "calc(100% - 50px)";
        div1.style.padding = "10px";
        var p1 = document.createElement("p");
        p1.style.textAlign = "center";
        p1.style.width = "100%";
        if(document.getElementById("potvrdaPretplateDugme").value == "Pretplati se") {
            p1.innerHTML = "Potvrđivanjem ove opcije prihvatate da Vas aplikacija \"Beograd na WEB-u\" obaveštava o novom smeštaju putem Vašeg mail-a.";
        } else {
            p1.innerHTML = "Potvrđivanjem ove opcije aplikacija \"Beograd na WEB-u\" će prestati da Vas obaveštava o novom smeštaju putem Vašeg mail-a.";
        }

        div1.append(p1);
        prozor.insertBefore(div1, prozor.children[prozor.children.length - 1]);

        var dugmeZaObustavljanje = document.createElement("input");
        var dugmeZaPotvrdu = document.createElement("input");
        dugmeZaObustavljanje.setAttribute("type", "button");
        dugmeZaPotvrdu.setAttribute("type", "button");
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaObustavljanje);
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaPotvrdu);

        dugmeZaObustavljanje.value = "Otkaži";
        dugmeZaPotvrdu.value = "Potvrdi";

        dugmeZaObustavljanje.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        dugmeZaPotvrdu.addEventListener("click", function() {
            if(document.getElementById("potvrdaPretplateDugme").value == "Pretplati se") {
                document.getElementById("potvrdaPretplateDugme").value = "Prekini pretplatu";
            } else {
                document.getElementById("potvrdaPretplateDugme").value = "Pretplati se";
            }
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
    });

    // potvrda pretplate
    document.getElementById("obrisiIstorijuDugme").addEventListener("click", function() {
        var prozor = document.getElementById("prozorZaPotvrdu");

        while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
            prozor.children[0].remove();

        prozor.style.height = "130px";
        prozor.style.top = "calc(50vh - 130px / 2)";
        prozor.style.width = "450px";

        var div1 = document.createElement("div");
        div1.style.width = "100%";
        div1.style.height = "calc(100% - 50px)";
        div1.style.padding = "10px";
        var p1 = document.createElement("p");
        p1.style.textAlign = "center";
        p1.style.width = "100%";
        p1.innerHTML = "Da li ste sigurni da želite da obrišete istoriju sviđanja?<br/ ><b>Ovime ćete uticati na algoritam preporuke.</b>";

        div1.append(p1);
        prozor.insertBefore(div1, prozor.children[prozor.children.length - 1]);

        var dugmeZaObustavljanje = document.createElement("input");
        var dugmeZaPotvrdu = document.createElement("input");
        dugmeZaObustavljanje.setAttribute("type", "button");
        dugmeZaPotvrdu.setAttribute("type", "button");
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaObustavljanje);
        document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaPotvrdu);

        dugmeZaObustavljanje.value = "Otkaži";
        dugmeZaPotvrdu.value = "Potvrdi";

        dugmeZaObustavljanje.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
        });

        dugmeZaPotvrdu.addEventListener("click", function() {
            dugmeZaObustavljanje.remove();
            dugmeZaPotvrdu.remove();
            document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
            $.ajax({url:"userdata/",type:"POST"});
            window.location.reload(true);
            return false;


        });

        document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
    });

    // ubacivanje novog komentara.
    document.getElementById("noviKomentar").addEventListener("click", function(ev) {
        var noviKomentar = document.createElement("div");
        noviKomentar.classList.add("komentari");
        noviKomentar.style.minHeight = "0";
        noviKomentar.style.height = "70px";

        var tekstKomentara = document.createElement("textarea");
        noviKomentar.append(tekstKomentara);

        var dugmadWrapper = document.createElement("div");

        var dugmePotvrdi = document.createElement("input");
        dugmePotvrdi.setAttribute("type", "button");
        dugmePotvrdi.setAttribute("value", "Objavi");
        dugmePotvrdi.addEventListener("click", function(ev) {
            if(tekstKomentara.value.length == 0) {
                return;
            }
            document.getElementById("noviKomentar").style.display = "block";
            var noviKomentarBox = document.createElement("div");
            noviKomentarBox.classList.add("komentari");

            var noviKomentarTekst = document.createElement("p");
            noviKomentarTekst.innerHTML = tekstKomentara.value.replaceAll("\n", "<br />");

            var noviWrapper = document.createElement("div");
            noviWrapper.setAttribute("class", "dugmadNaKomentarimaWrapper");

            var divZaLajk = document.createElement("div");
            divZaLajk.setAttribute("class", "lajkNaKomentarima");
            divZaLajk.addEventListener("click", function() {
                if(noviDivZaSVG.children[0].getAttribute("class") == "Layer_1") {
                    lajkBrojac.innerHTML = parseInt(lajkBrojac.innerHTML) + 1;
                    noviDivZaSVG.innerHTML = fullHeart;
                } else {
                    lajkBrojac.innerHTML = parseInt(lajkBrojac.innerHTML) - 1;
                    noviDivZaSVG.innerHTML = emptyHeart;
                }
            });

            var noviDivZaSVG = document.createElement("div");
            noviDivZaSVG.innerHTML = emptyHeart;

            divZaLajk.append(noviDivZaSVG);
            var lajkBrojac = document.createElement("p");
            lajkBrojac.innerHTML = 0;

            noviWrapper.append(divZaLajk);
            divZaLajk.append(lajkBrojac);

            var noviKomentarObrisiDugme = document.createElement("input");
            noviKomentarObrisiDugme.setAttribute("value", "Obriši");
            noviKomentarObrisiDugme.setAttribute("type", "button");
            noviKomentarObrisiDugme.addEventListener("click", function() {
                // potvrda brisanja komentara
                var prozor = document.getElementById("prozorZaPotvrdu");

                while(prozor.children[0].getAttribute("id") != "prozorZaPotvrduDugmadWrapper")
                    prozor.children[0].remove();

                prozor.style.height = "110px";
                prozor.style.top = "calc(50vh - 110px / 2)";
                prozor.style.width = "550px";

                var div1 = document.createElement("div");
                div1.style.width = "100%";
                div1.style.height = "calc(50% - 50px / 2)";
                div1.style.padding = "10px";
                var p1 = document.createElement("p");
                p1.innerHTML = "Da li ste sigurni da želite da obrišete komentar?";
                p1.style.width = "100%";
                p1.style.textAlign = "center";
                div1.append(p1);
                prozor.insertBefore(div1, prozor.children[prozor.children.length - 1]);

                var dugmeZaObustavljanje = document.createElement("input");
                var dugmeZaPotvrdu = document.createElement("input");
                dugmeZaObustavljanje.setAttribute("type", "button");
                dugmeZaPotvrdu.setAttribute("type", "button");
                document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaObustavljanje);
                document.getElementById("prozorZaPotvrduDugmadWrapper").append(dugmeZaPotvrdu);

                dugmeZaObustavljanje.value = "Otkaži";
                dugmeZaPotvrdu.value = "Potvrdi";

                dugmeZaObustavljanje.addEventListener("click", function() {
                    dugmeZaObustavljanje.remove();
                    dugmeZaPotvrdu.remove();
                    document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
                });

                dugmeZaPotvrdu.addEventListener("click", function() {
                    dugmeZaObustavljanje.remove();
                    dugmeZaPotvrdu.remove();
                    noviKomentarBox.remove();
                    document.getElementById("prozorZaPotvrduPozadina").style.display = "none";
                });

                document.getElementById("prozorZaPotvrduPozadina").style.display = "block";
            });

            noviWrapper.append(noviKomentarObrisiDugme);
            noviKomentarBox.append(noviWrapper);

            noviKomentarBox.append(noviKomentarTekst);

            document.getElementById("komentariPanel").removeChild(noviKomentar);
            document.getElementById("noviKomentar").before(noviKomentarBox);
        });

        var dugmeOtkazi = document.createElement("input");
        dugmeOtkazi.setAttribute("type", "button");
        dugmeOtkazi.setAttribute("value", "Otkaži");
        dugmeOtkazi.addEventListener("click", function(ev) {
            document.getElementById("noviKomentar").style.display = "block";
            document.getElementById("komentariPanel").removeChild(noviKomentar);
        });

        dugmadWrapper.append(dugmePotvrdi);
        dugmadWrapper.append(dugmeOtkazi);

        noviKomentar.append(dugmadWrapper);

        document.getElementById("noviKomentar").after(noviKomentar);
        document.getElementById("noviKomentar").style.display = "none";
        tekstKomentara.focus();
    });

    // lajk na slici
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




});