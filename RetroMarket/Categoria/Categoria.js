function mostrarDescripcion(elemento) {
  if(elemento==undefined) return
  elemento.querySelector("img").style.display = "none";
  elemento.querySelector(".descrip").style.display = "block";
}

function ocultarDescripcion(elemento) {
  if(elemento==undefined) return
  elemento.querySelector("img").style.display = "block";
  elemento.querySelector(".descrip").style.display = "none";
}
