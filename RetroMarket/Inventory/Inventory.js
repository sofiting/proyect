let lista_juego = [];

// para entrada.html
function initPage(goods_dom) {
  const goods_list_dom = document.querySelector(goods_dom);
  fetch("/Inventory/juegosAlmacen.json").then((res) => {
    res.json().then((resul) => {
      // Obtener la lista de juegos
      // 判断本地是否有数据，有则操作本地数据没有则存入
      let loca = localStorage.getItem('Inventory')
      if(!loca) localStorage.setItem('Inventory',JSON.stringify(resul.lista_juego))
      lista_juego = JSON.parse(localStorage.getItem('Inventory'))
      // Crear elementos HTML para cada juego
      const html = lista_juego
        .map(
          (item) => `
                  <div class="juego" data-id="${item.id}">
                    <h3>${item.title}</h3>
                    <p>
                      Precio: ${item.price} €
                      <i class="bi bi-pencil-square"></i>
                      <i class="bi bi-trash3-fill"></i>
                    </p>
                  </div>
                `
        )
        .join("");

      // Agregar HTML al contenedor
      goods_list_dom.innerHTML = html;

      // Agregar eventos de clic a los íconos de edición y eliminación
      const editIcons = document.querySelectorAll(".bi-pencil-square");
      const deleteIcons = document.querySelectorAll(".bi-trash3-fill");

      editIcons.forEach((editIcon) => {
        editIcon.addEventListener("click", handleEditClick);
      });

      deleteIcons.forEach((deleteIcon) => {
        deleteIcon.addEventListener("click", handleDeleteClick);
      });
    });
  });
}

// Función para manejar el clic en el ícono de edición
function handleEditClick(event) {
  const gameId = event.target.closest(".juego").dataset.id;
  // Aquí puedes implementar la lógica para abrir un formulario de edición o realizar alguna acción específica.
  console.log(`Editar juego con ID ${gameId}`);
  window.open("../addData/updateLnventory.html?id="+gameId, "_blank");
}

// Función para manejar el clic en el ícono de eliminación
function handleDeleteClick(event) {
  const gameId = event.target.closest(".juego").dataset.id;
  // Aquí puedes implementar la lógica para confirmar la eliminación o realizar alguna acción específica.
  console.log(`Eliminar juego con ID ${gameId}`);
  const index = lista_juego.findIndex(item=>item.id===parseInt(gameId))
  lista_juego.splice(index,1)
  localStorage.setItem('Inventory',JSON.stringify(lista_juego))
  alert('删除成功！')
  initPage("#goodID");
}
