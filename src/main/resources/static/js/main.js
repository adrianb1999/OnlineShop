document.addEventListener("DOMContentLoaded", function () {

    cart = localStorage.cart ? JSON.parse(localStorage.cart) : []

    if (document.body.classList.contains("category")){
        getProductsByCategory(getCategoryFromURL(window.location.href))

    }

    if(document.body.classList.contains("product-page")){

    }

    if(document.body.classList.contains("cart-page")){
        loadCartItems()
        loadAddresses()


        document.querySelector("#orderForm").onsubmit = function () {
            placeOrder()
            return false
        }
    }

    document.querySelector("#login-form").onsubmit = function () {
        login()
        return false
    }

    account = document.getElementById("account-value").innerHTML

//    if(account === "true"){
//        getUserInfo()
//    }

    var loginModal = document.getElementById("loginModal")
    var cartModal = document.getElementById("cartModal")
    var loggedModal = document.getElementById("loggedModal")

    var myAccount = document.getElementById("myAccount")
    var myCart = document.getElementById("myCart")

    myAccount.onclick = function (){
        if(account === "false")
            openLoginModal()
        else
            openLoggedModal()
    }

    myCart.onclick = function (){
        openCartModal()
    }

    window.onclick = function (event) {
        if (event.target === loginModal || event.target === cartModal || event.target === loggedModal) {
            closeLoginModal()
            closeCartModal()
            closeLoggedModal()
        }
    }

    updateCartModal()
})

function placeOrder(){
    let shippingAddress = {"id": document.querySelector("#shipping-address").value}
    let billingAddress = {"id":document.querySelector("#billing-address").value}
    let email = document.querySelector("#email").value
    let products = []

    for(let cartItem of cart){
        products.push({
            "productId" : cartItem.itemId,
            "amount" : cartItem.amount
        })
    }

    fetch('/api/order',
        {
              method: 'POST',
              headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
              },
              body: JSON.stringify({
                 'shippingAddress': shippingAddress,
                 'billingAddress': billingAddress,
                 'email': email,
                 'products': products
                }),
            })
            .then(response => {
                if (response.status === 200) {
                    cleanCart()
                } else if (response.status === 400) {
                    response.json().then(data =>
                        console.log(data.message))
                }
            })
            .catch((error) => {
                console.error('Error:', error);
    });

}

function loadAddresses(){
    let shippingAddress = document.querySelector("#shipping-address")
    let billingAddress = document.querySelector("#billing-address")

    fetch('/api/address',
        {
            method: 'GET',
        })
        .then(response => {

            if (response.status === 200){
                response.json().then(data =>{
                    for(let address of data){
                       shippingAddress.insertAdjacentHTML('beforeend', `
                                           <option value="${address.id}">${address.fullAddress}, ${address.fullName}, ${address.phoneNumber}</option>`
                                       )
                       billingAddress.insertAdjacentHTML('beforeend', `
                                           <option value="${address.id}">${address.fullAddress}, ${address.fullName}, ${address.phoneNumber}</option>`
                                       )
                    }

                })
            }
            else if (response.status === 400) {
                response.json().then(data =>
                    console.log(data.message)
                )

            } else if (response.status === 404) {
                console.log("Cannot connect to the server!")
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}

function logoutUser(){
    deleteAllCookies()
}

function deleteAllCookies() {
//    let cookies = document.cookie.split(";");
//
//    for (let i = 0; i < cookies.length; i++) {
//        let cookie = cookies[i];
//        let eqPos = cookie.indexOf("=");
//        let name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
//
//        let exp = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
//        console.log(exp)
//        document.cookie = exp
//    }
    document.cookie.split(";").forEach(function(c) { document.cookie = c.replace(/^ +/, "").replace(/=.*/, "=;expires=" + new Date().toUTCString() + ";path=/"); });
    location.reload()
}

function getUserInfo(){
    console.log("Getting user info...")
//
//    fetch('/api/user',
//        {
//            method: 'GET',
//        })
//        .then(response => {
//
//            if (response.status === 200)
//                //Inject the HTML
//            else if (response.status === 400) {
//                response.json().then(data =>
//                    console.log(data.message)
//                )
//
//            } else if (response.status === 404) {
//                console.log("Cannot connect to the server!")
//            }
//        })
//        .catch((error) => {
//            console.error('Error:', error);
//        });
}
var products
var cart

function disableScroll() {
    // Get the current page scroll position
    scrollTop = window.pageYOffset || document.documentElement.scrollTop;
    scrollLeft = window.pageXOffset || document.documentElement.scrollLeft,

        // if any scroll is attempted, set this to the previous value
        window.onscroll = function() {
            window.scrollTo(scrollLeft, scrollTop);
        };
}

function enableScroll() {
    window.onscroll = function() {};
}

function openCartModal() {
    disableScroll()
    document.getElementById("cartModal").style.display = "block"
}

function closeCartModal() {
    enableScroll()
    document.getElementById("cartModal").style.display = "none"
}

function openLoginModal() {
    disableScroll()
    document.getElementById("loginModal").style.display = "block"
}

function openLoggedModal(){
    disableScroll()
    document.getElementById("loggedModal").style.display = "block"
}

function closeLoggedModal(){
    enableScroll()
    document.getElementById("loggedModal").style.display = "none"
}

function login() {
    let name = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    fetch('/login',
        {
            method: 'POST',
            body: JSON.stringify({'username': name, 'password': password}),
            credentials: 'include'
        })
        .then(response => {

            if (response.status === 200){
                location.reload()
                console.log("Connected!")
            }
            else if (response.status === 400) {
                response.json().then(data =>
                    showAlert(data.message)
                )

            } else if (response.status === 404) {
                showAlert("Cannot connect to the server!")
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}


function closeLoginModal() {
    enableScroll()
    document.getElementById("loginModal").style.display = "none"
}

function getCategoryFromURL(url){
    let position = url.lastIndexOf('/')
    return url.slice(position + 1, url.length).toUpperCase();
}

function search(){
    console.log("Hai ca fac search!")
}


function updateCartModal(){
    for(let i = 0; i < cart.length; i++){
        addItemInCartDiv(i)
    }
    setTotalAmount()
}

function showAlert(){
    swal({
        title:"Item added in cart!",
        button: false,
        timer: 750,
    });
}

function addItemInCart(itemId, amount){
    event.preventDefault()
    showAlert()
    for(let i = 0; i < cart.length; i++){
        if(cart[i].itemId !== itemId)
            continue
        cart[i].amount += amount
        localStorage.setItem("cart", JSON.stringify(cart))
        document.getElementById(`${cart[i].itemId}-amount`).innerHTML = `x${cart[i].amount}`
        return
    }
    let product
    for(let currentProduct of products)
        if(currentProduct.id === itemId){
            product = currentProduct
            break;
        }
    cart.push({
        'itemId':itemId,
        'amount':amount,
        'imageUrl':product.imageUrl,
        'name':product.name,
        'price':product.price
    })
    localStorage.setItem("cart", JSON.stringify(cart))
    addItemInCartDiv(cart.length - 1)

}

function setTotalAmount(){
    let total = 0

    if(cart.length === 0){
        document.querySelector(".cart-info").style.display = "none"
        document.querySelector(".empty-cart").style.display = "block"
        return
    }

    document.querySelector(".cart-info").style.display = "flex"
    document.querySelector(".empty-cart").style.display = "none"

    for(let i = 0; i < cart.length; i++)
        total += cart[i].amount * cart[i].price
    document.getElementById("total-amount").innerHTML = `TOTAL: ${total} lei`
}

function removeItemInCart(itemId, amount = 0){
   for(let i = 0; i < cart.length; i++){
       if(cart[i].itemId !== itemId)
           continue
       if(amount === 0){
           cart.splice(i, 1);
           break
       }
       if(amount > cart[i].amount){
            cart[i].amount = 0
            break
       }
       cart[i].amount = cart[i].amount - amount
   }

   localStorage.setItem("cart", JSON.stringify(cart))
   setTotalAmount()
}
function cleanCart(){
    cart = []
    localStorage.setItem("cart", JSON.stringify(cart))
}

function deleteItemInCartDiv(itemId){
    removeItemInCart(itemId)
    document.getElementById(itemId).remove()
}

function addItemInCartDiv(cartIndex){
    let currentProduct = cart[cartIndex]

    let cartModal = document.getElementById("cart-modal-items")
    cartModal.insertAdjacentHTML("beforeend",`
        <div class="cart-item" id=${currentProduct.itemId}>
            <div class="item-img">
                <img src="${currentProduct.imageUrl}" alt="" width="50" height="50">
            </div>
            <div class="item-details">
                <a href="img/link" class="item-name">${currentProduct.name}</a>
                <p class="item-price">${currentProduct.price} lei</p>
                <p class="item-amount" id=${currentProduct.itemId}-amount>x${currentProduct.amount}</p>
                <a class="item-delete" onClick="deleteItemInCartDiv(${currentProduct.itemId})">X</a>
            </div>
        </div>
    `)
    setTotalAmount()
}
function openCartPage(){
    window.location = "/cart"
}
function loadCartItems(){
    let cartModal = document.getElementById("cart-items")

    for(let cartIndex = 0; cartIndex < cart.length; cartIndex ++){

        let currentProduct = cart[cartIndex]

        cartModal.insertAdjacentHTML("beforeend",`
            <div class="cart-item" id=${currentProduct.itemId}>
                <div class="item-img">
                    <img src="${currentProduct.imageUrl}" alt="" width="100" height="100">
                </div>
                <div class="item-details">
                    <a href="img/link" class="item-name">${currentProduct.name}</a>
                    <p class="item-price">${currentProduct.price} lei</p>

                    <a class="increase-amount">
                        -
                    </a>

                    <p class="item-amount" id=${currentProduct.itemId}-amount>${currentProduct.amount}</p>

                    <a class="decrease-amount">
                        +
                    </a>

                    <a class="item-delete" onClick="deleteItemInCartDiv(${currentProduct.itemId})">
                         <span class="material-icons md-30">delete</span>
                    </a>
                </div>
            </div>
        `)
    }
}

function getProductsByCategory(category){

    let contentDiv = document.querySelector(".products")

    fetch(`/api/product?category=${category}`,
        {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            products = data
            for(let product of data){
                contentDiv.insertAdjacentHTML("beforeend",
                    `
                    <a href="/product/${product.id}">
                    <div class="product">
                            <div class="photo">
                                <img src="${product.imageUrl}" alt="laptop" width="230" height="230">
                            </div>
                            <h3>${product.name}</h3>
                            <div class="stars">
                                <span class="material-icons md-30">star</span>
                                <span class="material-icons md-30">star</span>
                                <span class="material-icons md-30">star</span>
                                <span class="material-icons md-30">star</span>
                                <span class="material-icons md-30">star</span>
                            </div>
                            <h2>${product.price} lei</h2>
                            <button onClick="addItemInCart(${product.id}, 1)" class="add-cart-button">Adauga in cos!</button>
                         </div></a>`)

            }
        })
        .catch((error) => {
            console.error('Error:', error);
        })
}