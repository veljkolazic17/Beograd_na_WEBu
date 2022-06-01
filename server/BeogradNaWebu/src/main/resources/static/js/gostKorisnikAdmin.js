// Marko Mirković 197/19

document.addEventListener("DOMContentLoaded", function() {
    // podesavanje prikazanih smestaja da prikazu svoju sliku na panelu za konkretni smestaj
    var smestaji = document.getElementsByClassName("smestaji");
    var user = JSON.parse(sessionStorage.getItem("user"));
    var isLiked = JSON.parse(sessionStorage.getItem("isliked"));
    var smestajList = JSON.parse(sessionStorage.getItem("smestajList"));
    let currClicked = null;

    if(smestajList != null) {
        // posebno formiran niz zbog dve kolone kod prikaza smestaja
        let nizPrilagodjenZaPrikaz = [];
        for(let i = 0; i < smestajList.length; i++) {
            if(i % 2 == 0)
                nizPrilagodjenZaPrikaz.push(smestaji[i >> 1]);
            else
                nizPrilagodjenZaPrikaz.push(smestaji[(i >> 1) + Math.ceil(smestajList.length / 2)]);
        }
        smestaji = nizPrilagodjenZaPrikaz;

        for(let i = 0; i < smestajList.length; i++) {
            smestaji[i].addEventListener("click", function(ev) {
                currClicked = i;
                if(user != null) {
                    $.ajax({
                        url:"../isliked/"+user.korisnickoime+"/"+smestajList[i].idsmestaj,
                        type:"GET",
                        success: function(data) {
                            isLiked = data;
                        },
                        async: false
                    });
                }
                document.getElementById("prikazStana").style.display = "block";

                var opis = "";
                if (smestajList[i].cena > 0) {
                    opis+= "Cena: " + smestajList[i].cena + '<br>';
                }
                if(smestajList[i].kvadratura > 0) {
                    opis+= "Kvadratura: " + smestajList[i].kvadratura + '<br>';
                }
                if(smestajList[i].lokacija != "") {
                    opis+= "Lokacija: " + smestajList[i].lokacija + '<br>';
                }
                if(smestajList[i].brojSoba > 0) {
                    opis+= "Broj soba: " + smestajList[i].brojSoba + '<br>';
                }
                if(smestajList[i].spratonost > 0 ) {
                    opis+= "Spratnost: " + smestajList[i].spratonost + '<br>';
                }
                if(smestajList[i].imaLift) {
                    opis+= "Ima lift" + '<br>';
                }
                if(smestajList[i].idtipSmestaja > 0) {
                    opis+= "Tip smestaja: " + smestajList[i].idtipSmestaja + '<br>';
                }

                document.getElementById("opisKonkretnogSmestaja").innerHTML = opis
                document.getElementById("likeCounter").innerHTML = smestajList[i].brojLajkova
                // otvaranje originalnog sajta za konkretni smeštaj
                // document.getElementById("linkNaSlici").addEventListener('click', function(link) {
                //     window.open(smestajList[i].orgPutanja, "_blank");
                // });
                document.getElementById("link").setAttribute("href", smestajList[i].orgPutanja)

                var evTarget = ev.target;
                if(evTarget.getAttribute("class") != "smestaji")
                    evTarget = evTarget.parentElement;

                let slika = document.getElementById("slikaKonkretnogSmestaja");
                // reset u slucaju da je pre bilo greske pa je promenjen stil
                slika.style.width = "100%";
                slika.style.height = "auto";
                slika.style.marginLeft = "0";
                slika.style.marginTop = "0";
                slika.style.marginBottom = "0";
                // greska pri prikazu konkretnog smestaja
                slika.addEventListener("error", function(ev) {
                    slika.setAttribute("src", "../images/logoBeli.svg");
                    slika.style.width = "60%";
                    slika.style.height = "60%";
                    slika.style.marginLeft = "20%";
                    slika.style.marginTop = "5%";
                    slika.style.marginBottom = "5%";
                });
                slika.setAttribute("src", smestajList[i].slika);

                if(isLiked)
                    document.getElementById("lajkNaSlici").children[0].innerHTML = fullHeart;
                else
                    document.getElementById("lajkNaSlici").children[0].innerHTML = emptyHeart;
            });

            // greska pri prikazu u listi
            smestaji[i].children[0].addEventListener("error", function(ev) {
                smestaji[i].children[0].setAttribute("src", "../images/logo.svg");
                smestaji[i].children[0].style.width = "60%";
                smestaji[i].children[0].style.height = "60%";
                smestaji[i].children[0].style.marginLeft = "20%";
                smestaji[i].children[0].style.marginTop = "5%";
                smestaji[i].children[0].style.marginBottom = "5%";
            });
            // forsiramo refresh da bi error imao vremena da se pripremi i okine
            smestaji[i].children[0].setAttribute("src", smestaji[i].children[0].getAttribute("src"));
        }
    }


    // prikaz panela za filtere
    document.getElementById("dugmeFilteri").addEventListener('click', function() {
        document.getElementById("filteriPanelPozadina").style.display = "block";
    });
    document.getElementsByClassName("stranicaLevo")

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
    document.getElementById("dugmeNalog").addEventListener("click", function(ev) {
        if(ev.target !== ev.currentTarget) return;
        document.getElementById("nalogPanelPozadina").style.display = "block";
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
                    sviInputi[i].value = 0;
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
    document.getElementById("lajkNaSlici").addEventListener("click", function(ev) {
        var element = document.getElementById("lajkNaSlici");
        if(user!=null) {
            if (element.children[0].children[0].getAttribute("class") == "Layer_1") {
                element.children[1].innerHTML = parseInt(element.children[1].innerHTML) + 1;
                element.children[0].innerHTML = fullHeart;
                smestajList[currClicked].brojLajkova += 1;
                $.ajax({url: "../like/" + smestajList[currClicked].idsmestaj, type: "POST"})

            } else {
                element.children[1].innerHTML = parseInt(element.children[1].innerHTML) - 1;
                element.children[0].innerHTML = emptyHeart;
                smestajList[currClicked].brojLajkova -= 1;
                $.ajax({url: "../unlike/" + smestajList[currClicked].idsmestaj, type: "POST"})
            }
        }
    });






    /*
    * Postavljanje slektovanih filtera
    * */
    var filterData = JSON.parse(sessionStorage.getItem("filterf"));
    var textToBind = "";
    var first = true;

    Object.entries(filterData).forEach(([key,value])=>{
        if(value != null && value !== 0 && value !== false && value !== "nullLokacija" && value !== "nullSoba" && value !== "nullSmestaj"){
            if(first){
                textToBind+="&times;&nbsp;";
                first=false;
            }
            textToBind += key + ": " + value + "</br>"
        }
    });
    document.getElementById("izabraniFilteri").innerHTML = textToBind;



});
