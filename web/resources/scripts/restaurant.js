function addItem(id) {
    $.ajax({
        url: "/api/order/item",
        data: {item_id: id},
        type: "POST",
        beforeSend:setJsessionCookie,
        complete: function (response) {
            $("#container").text(response.responseText + $("#container").text())
        }
    });
}

function setJsessionCookie (xhr) {
    xhr.setRequestHeader('JSESSIONID', $.cookie("JSESSIONID"));
}

function refreshOrderTab() {

}