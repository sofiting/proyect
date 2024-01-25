function validarFormulario() {
  var nombre = document.getElementById("nombre").value;
  var email = document.getElementById("email").value;
  var password = document.getElementById("password").value;
  var direccion = document.getElementById("direccion").value;
  var suscribirse = document.querySelector('input[name="suscribirse"]').checked;

  // Reiniciar mensajes de error
  document.getElementById("nombreError").innerHTML = "";
  document.getElementById("emailError").innerHTML = "";
  document.getElementById("passwordError").innerHTML = "";
  document.getElementById("direccionError").innerHTML = "";
  document.getElementById("suscribirseError").innerHTML = "";

  // Validar cada campo
  if (!nombre) {
    document.getElementById("nombreError").innerHTML = "Ingrese su nombre";
    return false;
  }

  if (!email) {
    document.getElementById("emailError").innerHTML =
      "Ingrese su correo electrónico";
    return false;
  }

  if (!password) {
    document.getElementById("passwordError").innerHTML =
      "Ingrese su contraseña";
    return false;
  }

  if (!direccion) {
    document.getElementById("direccionError").innerHTML =
      "Ingrese su dirección";
    return false;
  }

  if (!suscribirse) {
    document.getElementById("suscribirseError").innerHTML =
      "Debe aceptar los términos y condiciones";
    return false;
  }

  // Si todos los campos son válidos, redirige a la página de login
  window.location.href = "../Login/Login.html";
  alert("¡Bienvenido al Retro Market, " + nombre + "!");
  return true;
}

function mostrarTerminosCondiciones() {
  // Muestra el modal
  document.getElementById("overlay").style.display = "block";
  document.getElementById("modal").style.display = "block";
}

function cerrarModal() {
  // Cierra el modal
  document.getElementById("overlay").style.display = "none";
  document.getElementById("modal").style.display = "none";
}
