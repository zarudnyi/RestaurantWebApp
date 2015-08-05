function updateItem (itemId){

    var itemDiv = $("div.menu-item#"+itemId);
    var item = new Item();
    item.id=itemId;
    item.name = itemDiv.find("span#name").html();
    item.description = itemDiv.find("p#description").html();
    item.price = itemDiv.find("span#price").html();

    item.price =  Math.floor (parseFloat(item.price) *100);
    console.log(item);

    $.ajax({
            url: "/menu/update",
            data: item,
            type: "POST",
            beforeSend: setHeaders,
            complete: function (response) {
                console.log(response.responseText);
            }
        });

}

function removeItem(itemId){
    $.ajax({
        url: "/menu/delete",
        data: {id : itemId},
        type: "POST",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(response.responseText);
        },
        async:false
    });
}

function updateCategory(catId){
    var name = $("h2#"+catId).html();
    $.ajax({
        url: "/menu/update/category",
        data: {id:catId, name: name},
        type: "POST",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(response.responseText);
        },
        async:false

    });
}

function removeCategory(catId){
    $.ajax({
        url: "/menu/delete/category",
        data: {id : catId},
        type: "POST",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(response.responseText);
        }
    });
}

function Item (){
    this.id = -1;
    this.name="";
    this.price = 0;
    this.description="";
}