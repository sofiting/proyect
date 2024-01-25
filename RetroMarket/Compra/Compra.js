// 购物车页面获取商品
let goods_list = JSON.parse(localStorage.getItem("carrito")) || [];
goods_list.map((item) => (item.checked = false)); // 这一步主要是实现全选和反选，所以增加一个checked属性
let all_checkbox_dom = document.querySelector("#all");
const goods_list_dom = document.querySelector(".goods_list"); // 商品列表容器dom
// 将商品渲染到列表
updateCatDom(goods_list);

// 更新Dom列表的函数
function updateCatDom(list) {
  if (list.length === 0) {
    // Si el carrito está vacío, muestra un mensaje
    goods_list_dom.innerHTML =
      '<p style="font-size: 28px; font-weight: bold;">El carrito está vacío</p>';
    return;
  }
  let html = "";
  list.forEach((item) => {
    html += `
                <div class="juego_seleccionado" data-id="${item.id}">
                  <div>
                    <input type="checkbox" name="" />
                    <label for="seleccionaUno"></label>
                  </div>
                  <img src="${item.image}" alt="img 1" />
                  <div class="info_juego">
                    <p>${item.title}</p>
                    <p>${item.price} €</p>
                  </div>
                  <label for="cantidad">Cantidad</label>
                  <input type="number" id="numeroInput" value="${item.num}" min="0" />
                </div>
              `;
  });
  goods_list_dom.innerHTML = html;
  // 每个商品dom绑定对应的事件
  for (let i = 0; i < goods_list_dom.children.length; i++) {
    const item_dom = goods_list_dom.children[i];
    // 获取当前的商品ID并且将商品item传入到点击事件
    const current_goods_id = item_dom.getAttribute("data-id");
    // 筛选数据
    const goods_item = list.find(
      (item) => item.id === parseInt(current_goods_id)
    );
    // 绑定 checkbox事件
    item_dom.children[0].children[0].addEventListener("change", () =>
      onSelGoods(goods_item)
    );
    // 绑定商品输入框事件
    item_dom
      .querySelector("#numeroInput")
      .addEventListener("change", (event) =>
        setGoods(current_goods_id, "num", event.target.value)
      );
  }
  // 每次更新dom都重新计算一下商品的价格和选中的数量
  onGoodsCount();
}

// 清空购物车
function clearCart() {
  goods_list = [];
  localStorage.removeItem("carrito");
  updateCatDom(goods_list);
}
//  选择购物车商品
function onSelGoods(item) {
  const index = goods_list.findIndex((items) => items.id === item.id);
  goods_list[index].checked = !goods_list[index].checked;

  // 这一步主要是实现全选反选功能(当所有子元素都被选中时则全选否则反选)
  const all_goods = goods_list.every((goods) => goods.checked);
  all_checkbox_dom.checked = false;
  if (all_goods) all_checkbox_dom.checked = true;

  // 计算商品数量价格
  onGoodsCount();
}
// 全选和反选
function onAll(event) {
  var checkboxe_all = document.querySelectorAll('input[type="checkbox"]');
  checkboxe_all.forEach((item) => (item.checked = event.target.checked));
  goods_list.map((item) => (item.checked = event.target.checked));
  onGoodsCount();
}
// 删除选中的商品
function onDelSelGoods() {
  // 这里我为了方便直接重新赋值就不用splice了，当然你也可以把选中的商品筛选出来在循环一遍逐个删除
  const goods_length = goods_list.length;
  goods_list = goods_list.filter((item) => !item.checked);

  // 更新本地存储
  localStorage.setItem("carrito", JSON.stringify(goods_list));
  updateCatDom(goods_list);

  if (goods_list.length !== goods_length)
    console.log("Has eliminado con éxito！");
  else console.log("No hay juego para eliminar");
}
// 计算商品选中数量和价格
function onGoodsCount() {
  let price = 0;
  let check_goods = goods_list.filter((item) => item.checked);
  check_goods.forEach((item) => (price += item.price * item.num));
  document.querySelector("#goods_prices").innerText =
    "Total: " + price.toFixed(2) + " €";
  document.querySelector("#goods_num").innerText =
    "Juego seleccionado：" + check_goods.length;
}
// 更新商品信息
function setGoods(goodsid, attr, value) {
  const index = goods_list.findIndex((item) => item.id === parseInt(goodsid));
  goods_list[index][attr] = attr == "num" ? parseInt(value) : value;
  // 更新本地存储
  localStorage.setItem("carrito", JSON.stringify(goods_list));
  onGoodsCount();
}
