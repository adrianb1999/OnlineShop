document.addEventListener("DOMContentLoaded", function () {
    if (document.body.classList.contains("category")) {
        getProductsByCategory(
            getCategoryFromURL(window.location.href))
    }
})

function getCategoryFromURL(url){
    let position = url.lastIndexOf('/')

    return url.slice(position + 1, url.length).toUpperCase();
}

function getProductsByCategory(category){

    let contentDiv = document.querySelector(".products")

    fetch(`/api/product?category=${category}`,
        {
            method: 'GET',
        })
        .then(response => response.json())
        .then(data => {
            for(let product of data){
                contentDiv.insertAdjacentHTML("beforeend",
                    `<div class="product">
                            <div class="photo">
                                <img src="${product.imageUrl}" alt="laptop" width="250" height="250">
                            </div>
                            <h3>${product.name}</h3>
                            <div class="stars">
                                <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQWzP0b3rcT8tJuL7bwgLkbgYPQzYqMP5Gdyg&usqp=CAU" alt="Stars" width="250" height="30">
                            </div>
                            <h2>${product.price} lei</h2>
                            <button>Adauga in cos!</button>
                         </div>`)
                console.log(product.id + ' ' + product.name + ' ' + product.price)
            }
        })
        .catch((error) => {
            console.error('Error:', error);
        })
}