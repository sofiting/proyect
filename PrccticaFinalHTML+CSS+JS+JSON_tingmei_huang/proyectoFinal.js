function createReceipe() {
    fetch('https://raw.githubusercontent.com/sofihuang/apijson/main/receipe.json')
        .then(response => response.text())
        .then(text => mostrarReceipe(text));
}

function mostrarReceipe(StringRecipe) {
    var receipe = JSON.parse(StringRecipe);

    var caja = document.querySelector("#caja");
    var caja2 = document.createElement("div");
    caja2.id = "caja2";
    caja.after(caja2);

    var titulo = document.createElement("h2");
    titulo.textContent = receipe.title;
    caja2.appendChild(titulo);

    var imagenDish = document.createElement("img");
    imagenDish.classList.add("dish");
    imagenDish.src = receipe.image;

    var enlaceDish = document.createElement("a");
    enlaceDish.target = "_blank";
    enlaceDish.href = "https://www.recipetineats.com/quick-broccoli-pasta/";
    enlaceDish.appendChild(imagenDish);
    caja2.appendChild(enlaceDish);

    var minuto = document.createElement("p");
    minuto.textContent = "Ready in minutes: " + receipe.readyInMinutes;
    caja2.appendChild(minuto);

    var dishType = document.createElement("ul");
    dishType.style.listStyle = "circle";
    dishType.textContent = "Dish Type: ";
    receipe.dishTypes.forEach(function (item) {
        var li = document.createElement("li");
        li.textContent = item;
        dishType.appendChild(li);
    });
    caja2.appendChild(dishType);

    var boton2 = document.createElement("button");
    boton2.onclick = function () {
        mostrarIngredientes(receipe, caja2);
    }
    boton2.className = "boton2";
    boton2.textContent = "SHOW ALL INGREDIENTS";
    caja2.appendChild(boton2);
}


function mostrarIngredientes(receipe, caja2) {
    var caja3 = document.createElement("div");
    caja3.id = "caja3";
    caja2.after(caja3);

    var extendedIngredients = receipe.extendedIngredients;

    extendedIngredients.forEach(function (item) {

        var cajaInterna = document.createElement("div");
        cajaInterna.classList.add("cajaInterna");
        caja3.appendChild(cajaInterna);

        var imagenIng = document.createElement("img");
        imagenIng.classList.add("imagenIng");
        imagenIng.src = item.image;
        cajaInterna.appendChild(imagenIng);

        var nombre = document.createElement("span");
        nombre.textContent = item.name;
        nombre.classList.add("nombre");
        cajaInterna.appendChild(nombre);

        var cantidad = document.createElement("span");
        cantidad.textContent = item.original;
        cantidad.classList.add("cantidad");
        cajaInterna.appendChild(cantidad)
    });

    var boton3 = document.createElement("button");
    boton3.textContent = "SEE MORE";
    boton3.onclick = function () {
        instruccion(receipe, caja3);
    }
    boton3.className = "boton3";
    caja3.appendChild(boton3);

}

function instruccion(receipe, caja3) {

    var caja4 = document.createElement("div");
    caja4.id = "caja4";
    caja3.after(caja4);

    var tituloIns = document.createElement("h3");
    tituloIns.textContent = "COOKING INSTRUCTION";
    tituloIns.className = "tituloIns";
    caja4.appendChild(tituloIns);

    var instru = receipe.instructionsDish;
    var ins = document.createElement("ul");
    ins.id = "ins";

    instru.forEach(function (line) {
        var li = document.createElement("li");
        li.textContent = line;
        li.style.listStyleType = "square";
        ins.appendChild(li);
    });

    caja4.appendChild(ins);

    var tituloWine = document.createElement("h3");
    tituloWine.textContent = "Wine Pairing";
    tituloWine.className = "tituloWine";
    caja4.appendChild(tituloWine);

    var cajaAux = document.createElement("div");
    cajaAux.id = "cajaAux";
    caja4.appendChild(cajaAux);

    var imagenWine = document.createElement("img");
    imagenWine.src = receipe.winePairing.productMatches[0].imageUrl;
    imagenWine.className = "imageWinee";

    var enlaceWine = document.createElement("a");
    enlaceWine.target = "_blank";
    enlaceWine.href = "https://www.wine.com/#closePromoModal";
    enlaceWine.id = "wine";
    enlaceWine.appendChild(imagenWine);

    cajaAux.appendChild(enlaceWine);


    var nombreWine = document.createElement("h3");
    nombreWine.textContent = receipe.winePairing.productMatches[0].title;
    nombreWine.className = "nombreWinee";
    cajaAux.appendChild(nombreWine);

    var descripcion = document.createElement("p");
    descripcion.textContent = receipe.winePairing.productMatches[0].description;
    descripcion.className = "descripcionWinee";
    nombreWine.appendChild(descripcion);

    var showDessert = document.createElement("button");
    showDessert.textContent = "SHOW DESSERT";
    showDessert.classList.add("showDessert");
    showDessert.onclick = function () {
        postre(receipe, caja4);
    }
    descripcion.appendChild(showDessert);
}

function postre(receipe, caja4) {
    var caja5 = document.createElement("div");
    caja4.after(caja5);

    var postre = receipe.dessertParing;
    var tituloPostre = document.createElement("h3");
    tituloPostre.textContent = " IF YOU'D LIKE SOME DESSERT ";
    tituloPostre.className = "tituloPostre";
    caja5.appendChild(tituloPostre);

    var element1 = document.createElement("p");
    element1.textContent = postre.name;
    element1.className = "element";
    caja5.appendChild(element1);

    var element2 = document.createElement("p");
    element2.textContent = "Servings: " + postre.servings;
    element2.className = "element";
    caja5.appendChild(element2);

    var element3 = document.createElement("ul");
    element3.className = "element";

    element3.textContent = "Ingredients : ";
    var ingredients = receipe.dessertParing.ingredients;
    ingredients.forEach(function (ing) {
        var name = document.createElement("li");
        name.textContent = ing.name + " : " + ing.amount;
        name.classList.add("elementSon");
        element3.appendChild(name);
    });
    caja5.appendChild(element3);

    var insPostre = postre.instructions;

    insPostre.forEach(function (paso) {
        var line = document.createElement("p");
        line.textContent += paso;
        line.classList.add("pasoDessert");
        caja5.appendChild(line);
    });

    var botonmeal = document.createElement("button");
    botonmeal.classList.add("botonmeal");
    botonmeal.textContent = "get daily meal";
    caja5.appendChild(botonmeal);
    botonmeal.onclick = function () {
        getmeal(caja5);
    }

}
function getmeal(caja5) {
    fetch('https://raw.githubusercontent.com/sofihuang/apijson/main/meal_plan.json')
        .then(response => response.text())
        .then(text => mealplan(caja5, text));
}
function mealplan(caja5, plan) {
    var meal = JSON.parse(plan);

    var caja6 = document.createElement("div");
    caja5.after(caja6);

    var divAux = document.createElement("div");
    divAux.classList.add("divAux");

    var meal2 = meal.meals;
    meal2.forEach(function (plan) {
        var divIn = document.createElement("div");
        divIn.classList.add("divIn");
        divAux.appendChild(divIn);

        var titulo = document.createElement("h4");
        titulo.classList.add("titulo6");
        titulo.textContent = plan.title;
        divIn.appendChild(titulo);

        var imagen = document.createElement("img");
        imagen.classList.add("imgen6");
        imagen.src = plan.imageType;

        var url = document.createElement("a");
        url.href = plan.sourceUrl;
        url.target = "_blank";
        url.appendChild(imagen);
        divIn.appendChild(url);
    });
    caja6.appendChild(divAux);

    var fin = document.createElement("p");
    fin.textContent = "Hope this can help you and your family have a nice meal !";
    fin.className = "fin";
    caja6.appendChild(fin);
}