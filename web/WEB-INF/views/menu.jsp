<%@include file="templates/header.jsp" %>
<br/>
<br/>
<br/>
<br/>
<br/>
<div class="border2"></div>

<article>
    <div class="menu">
        <c:forEach var="entry" items="${menu}">

            <h2>${entry.key.name}</h2>
            <c:forEach var="menuItem" items="${entry.value}">
            <div class="menu-item" id="${menuItem.id}">
                <div class="left">
                    <h4>${menuItem.name}: ${menuItem.dollarPrice}</h4>
                </div>
                <sec:authorize access="isAuthenticated()" var="authenticated"/>

                <div class="right menu-order"><a style="cursor: pointer" class="button" onclick=" if (${authenticated}) { addItem(${menuItem.id}); refreshOrderPanel(); } else document.location ='login'">Order Now</a></div>
                <img src="resources/images/${menuItem.picture}" class="left clear item" width="150" alt=""/>

                <p class="left">${menuItem.description}</p>
            </div>

            <div class="border3"></div>
            </c:forEach>
            <div class="border2"></div>

        </c:forEach>
</article>

<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>

