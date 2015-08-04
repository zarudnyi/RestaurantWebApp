<%@include file="templates/header.jsp" %>
<br/>
<br/>
<br/>
<br/>
<br/>
<article>
    <div class="menu">
        <c:forEach var="entry" items="${menu}">
            <div class="border2"></div>

            <h2>${entry.key.name}</h2>
            <c:forEach var="menuItem" items="${entry.value}">
            <div class="menu-item" id="${menuItem.id}">
                <div class="left">
                    <h4>${menuItem.name}: ${menuItem.dollarPrice}</h4>
                </div>
                <div class="right menu-order"><a style="cursor: pointer" class="button" onclick="addItem(${menuItem.id})">Order Now</a></div>
                <img src="resources/images/${menuItem.picture}" class="left clear item" width="150" alt=""/>

                <p class="left">${menuItem.description}</p>
            </div>

            <div class="border3"></div>
            </c:forEach>
        </c:forEach>
</article>

<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>

