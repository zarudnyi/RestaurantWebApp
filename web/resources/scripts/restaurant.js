function addItem(id) {
    $.ajax({
        url: "/api/order/item",
        data: {item_id: id},
        type: "POST",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(response.responseText);
        }
    });
}

function getOrderItems(orderId) {
    var orderItems;
    $.ajax({
        url: "/api/order/item",
        data: {order_id: orderId},
        type: "GET",
        beforeSend: setHeaders,
        complete: function (response) {
            orderItems = JSON.parse(response.responseText);
        },
        async: false
    });
    console.log(orderItems);
    return orderItems;
}

function getCurrentOrders() {
    var orders;
    $.ajax({
        url: "/api/order",
        type: "GET",
        beforeSend: setHeaders,
        complete: function (response) {
            orders = JSON.parse(response.responseText);
        },
        async: false
    });
    console.log(orders);
    return orders;
}

function deleteOrderItem(orderId, itemId) {
    var orders;
    $.ajax({
        url: "/api/order/item?" + $.param({order_id: orderId, item_id: itemId}),
        type: "DELETE",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(JSON.parse(response.responseText));
        },
        async: false
    });

}

function deleteOrder(orderId) {
    var orders;
    $.ajax({
        url: "/api/order",
        type: "DELETE",
        beforeSend: setHeaders,
        data: {order_id: orderId},
        complete: function (response) {
            console.log(JSON.parse(response.responseText));
        },
        async: false
    });

}


function setHeaders(xhr) {
    xhr.setRequestHeader('JSESSIONID', $.cookie("JSESSIONID"));
    xhr.setRequestHeader('Accept', "");
}

function setCurrentOrder(id) {
    $.ajax({
        url: "/api/order",
        data: {order_id: id},
        type: "POST",
        beforeSend: setHeaders,
        complete: function (response) {
            console.log(response.responseText);
        }
    });
}

function renderDetailedOrderTable(order, data) {
    $("#order-panel").append("<table id = 'order-" + order.id + "' style='border-spacing: 7px 11px; border-collapse: separate;'><tbody>");
    var table = $("#order-panel").children("#order-" + order.id);

    if (data.length > 0) {
        for (var i in data) {
            var user = data[i];
            var totalPrice = 0;


            table.append("<tr id='tr-" + i + "'>");

            var row = table.children("tbody:first").children("#tr-" + i);

           // alert(row+ " "+ "#tr-" + i)
            renderCell(row, user.login);
            renderCell(row, "");
            renderCell(row, "");
            renderCell(row, "");


            table.append("</tr>");

            var subTotal = 0;
            for (var j in user.items) {

                var item = user.items[j];
                console.log(item);

                var trId = i + "-" + j;
                table.append("<tr id='tr-" + trId + "'>");

                row = table.children("tbody:first").children("#tr-" + trId);

                renderCell(row, item.itemName);
                renderCell(row, item.qty);
                renderCell(row, price(item.price * item.qty));
                subTotal += item.price * item.qty;
                if (currentUser == user.login)
                    renderCell(row, "<a style='cursor: pointer;' onclick='deleteOrderItem(" + order.id + " , " + item.itemId + "); refreshOrderPanel();'> [X]</a>");
                else
                    renderCell(row, "");


                table.append("</tr>");

            }
            j++;
            trId = i + "-" + j;
            totalPrice+=subTotal;

            table.append("<tr id='tr-" + trId + "'>");
            var row = table.children("tbody:first").children("#tr-" + trId);

            renderCell(row, "SubTotal");
            renderCell(row, "");
            renderCell(row, price(subTotal));
            renderCell(row, "");


            table.append("</tr>");
        }
        i++;
        trId = i;

        table.append("<tr id='tr-" + trId + "'>");
        var row = table.children("tbody:first").children("#tr-" + trId);

        renderCell(row, "Total");
        renderCell(row, "");
        renderCell(row, price(totalPrice));
        renderCell(row, "<a style='cursor: pointer;' onclick='checkOut(" + order.id + ");'> [Checkout] </a>");


        table.append("</tr>");
    }
    else {
        table.append("[empty]");
    }

    $("#order-panel").append("</tbody></table>");

}
function refreshOrderPanel() {

    var orders = getCurrentOrders();

    $("#order-panel").html("");
    for (var i in orders) {
        var order = orders[i];
        var orderPanel = $("#order-panel");

        if (order.isCurrent)
            orderPanel.append("<h3 class='current-order'>" + order.name + "</h3>");
        else
            orderPanel.append("<h3 style='cursor: pointer;' onclick='setCurrentOrder(" + order.id + "); refreshOrderPanel();'>" + order.name + "</h3>");

        if (order.isGroup)
        if (summarySelected) {
            orderPanel.append("<a  style='cursor: pointer;' onclick='switchToDetails(); refreshOrderPanel();'>Details</a>");
        }
        else {
            orderPanel.append("<a  style='cursor: pointer;' onclick='switchToSummary();refreshOrderPanel();'>Summary</a>");
        }

        if (summarySelected || !order.isGroup)
            renderSummaryOrderTable(order, getOrderItems(order.id));
        else
            renderDetailedOrderTable(order, getOrderItems(order.id));

    }


}
function switchToDetails() {
    summarySelected = false;
}
function switchToSummary() {
    summarySelected = true;
}

function renderSummaryOrderTable(order, data) {
    $("#order-panel").append("<table id = 'order-" + order.id + "' style='border-spacing: 7px 11px; border-collapse: separate;'><tbody>");
    var table = $("#order-panel").children("#order-" + order.id);

    if (data.length > 0) {
        var totalPrice = 0;
        for (var i in data) {
            var user = data[i];
            console.log(user);
            for (var j in user.items) {

                var item = user.items[j];
                console.log(item);

                var trId = i + "-" + j;

                table.append("<tr id='tr-" + trId + "'>");

                var row = table.children("tbody:first").children("#tr-" + trId);

                renderCell(row, item.itemName);
                renderCell(row, item.qty);
                renderCell(row, price(item.price * item.qty));
                totalPrice += item.price * item.qty;
                if (!order.isGroup)
                    renderCell(row, "<a style='cursor: pointer;' onclick='deleteOrderItem(" + order.id + " , " + item.itemId + "); refreshOrderPanel();'> [X]</a>");


                table.append("</tr>");

            }

        }
        i++;
        table.append("<tr id='tr-" + i + "'>");
        var row = table.children("tbody:first").children("#tr-" + i);

        renderCell(row, "Total");
        renderCell(row, "");
        renderCell(row, price(totalPrice));
        renderCell(row, "<a style='cursor: pointer;' onclick='checkOut(" + order.id + ");'> [Checkout] </a>");


        table.append("</tr>");
    }
    else {
        table.append("[empty]");
    }

    $("#order-panel").append("</tbody></table>");

}


function renderCell(row, data) {
    row.append("<td> " + data + " </td>");
}

function price(intPrice) {
    if (intPrice > 0)
        return "$" + (intPrice / 100).toFixed(2);
    else
        return "Free!";
}


function checkOut (orderId){
    $("#order-checkout").dialog();
    $('#datepicker').appendDtpicker({
        "autodateOnStart": false
    });
    $('#order-id').val(orderId);


}