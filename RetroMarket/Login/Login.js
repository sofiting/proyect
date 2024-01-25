function validarFormulario() {
  var nombre = document.getElementById("nombre").value;
  var password = document.getElementById("password").value;

  // Reiniciar mensajes de error
  document.getElementById("nombreError").innerHTML = "";
  document.getElementById("passwordError").innerHTML = "";

  // Validar nombre
  if (!nombre) {
    document.getElementById("nombreError").innerHTML =
      "Por favor, ingresa tu nombre";
    return false;
  }

  // Validar contraseña
  if (!password) {
    document.getElementById("passwordError").innerHTML =
      "Por favor, ingresa tu contraseña";
    return false;
  }

  // Si ambos campos son válidos, puedes enviar el formulario
  window.location.href = "../MainPage/Entrada.html";
  alert(`¡Bienvenido! ${nombre} Has iniciado sesión exitosamente.`);
  return true;
}
