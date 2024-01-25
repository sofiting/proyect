let lista_juego = [];

// para entrada.html
function initPage(goods_dom) {
  const goods_list_dom = document.querySelector(goods_dom);
  fetch("/data/juegos.json").then((res) => {
    res.json().then((resul) => {
      lista_juego = resul.lista_juego;
      console.log(lista_juego);

      var html = "";
      lista_juego.forEach((item, index) => {
        console.log(item);
        if (index >= 3) return;
        html += `
           <div class="juego" data-id="${item.id}">
            <img src="${item.image}" alt="image 1" />
            <h3>${item.title}</h3>
            <p>
              Precio: ${item.price} €<i class="bi bi-cart4"></i>
            </p>
          </div>
          `;
      });
      console.log(html);
      goods_list_dom.innerHTML = html;

      var list_item = document.querySelector(goods_dom).children;
      for (let i = 0; i < list_item.length; i++) {
        list_item[i]
          .querySelector(".bi-cart4")
          .addEventListener("click", (event) => {
            var item_id = list_item[i].getAttribute("data-id");
            var id = parseInt(item_id);
            let storage = localStorage.getItem("carrito") || "[]";
            console.log("id ", id);
            const good_index = lista_juego.findIndex(
              (goods) => goods.id === id
            );
            console.log(good_index);
            const item_selected = lista_juego[good_index];
            console.log(item_selected);
            item_selected.num = 1;
            //add carrito
            storage = JSON.parse(storage);
            console.log(storage);
            const storage_item_index = storage.findIndex(
              (goods_item) => goods_item.id === item_selected.id
            );
            console.log(storage_item_index);
            // si no existe añade al carrito , si existe cantidad +1
            if (storage_item_index == -1) {
              storage.push(item_selected);
            } else {
              storage[storage_item_index].num += item_selected.num;
            }

            localStorage.setItem("carrito", JSON.stringify(storage));
            alert("Has añadido un juego al carrito");

            console.log(localStorage.getItem("carrito"));

            //localStorage.clear();
          });
      }
      (err) => {
        console.log(err);
      };
    });
  });
}

function onAddCart(item) {
  item.num = 1; // 定义购物车商品数量
  // 获取购物车
  let cart_list = localStorage.getItem("carrito") || "[]";
  cart_list = JSON.parse(cart_list); //json转{}
  const goods_index = cart_list.findIndex((goods) => goods.id === item.id);
  if (goods_index !== -1) cart_list[goods_index].num += item.num;
  else cart_list.push(item);
  console.log(cart_list);
  // 存储到本地
  localStorage.setItem("carrito", JSON.stringify(cart_list));
  alert("Has añadido un juego al carrito！");
}
