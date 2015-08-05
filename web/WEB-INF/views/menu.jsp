<%@include file="templates/header.jsp" %>
<br/>
<br/>
<br/>
<br/>
<br/>

<div class="border2"></div>

<article>
    <div class="menu">
        <c:if test="${isAdmin}">
        <a style="cursor: pointer"
           href="/menu/createCategory" >[Add Category]</a>
        </c:if>
        <c:forEach var="entry" items="${menu}">

        <div contenteditable="${isAdmin}" onblur="updateCategory(${entry.key.id})">
            <h2 id="${entry.key.id}">${entry.key.name}</h2></div>
            <c:if test="${isAdmin}">
            <a style="cursor: pointer"
               href="/menu/createItem" >[Add Item]</a>
            <a style="cursor: pointer"
               onclick="removeCategory(${entry.key.id}); location.reload();">[Remove]</a>
            </c:if>
        <c:forEach var="menuItem" items="${entry.value}">
        <div class="menu-item" id="${menuItem.id}">
            <div class="left">
                <h4><span id="name" contenteditable="${isAdmin}"
                          onblur="updateItem(${menuItem.id})">${menuItem.name}</span>: $<span id="price"
                                                                                              onblur="updateItem(${menuItem.id})"
                                                                                              contenteditable="${isAdmin}">${menuItem.dollarPrice} </span>
                </h4>
                <c:if test="${isAdmin}">
                    <a style="cursor: pointer"
                       onclick="removeItem(${menuItem.id}); location.reload();">Remove</a>
                </c:if>
            </div>
            <sec:authorize access="isAuthenticated()" var="authenticated"/>

            <div class="right menu-order"><a style="cursor: pointer" class="button"
                                             onclick=" if (${authenticated}) { addItem(${menuItem.id}); refreshOrderPanel(); } else document.location ='login'">Order
                Now</a></div>
            <img src="resources/images/${menuItem.picture}" class="left clear item" width="150" alt=""/>

            <p class="left" id="description" onblur="updateItem(${menuItem.id})"
               contenteditable="${isAdmin}">${menuItem.description}</p>
        </div>

        <div class="border3"></div>
        </c:forEach>
        <div class="border2"></div>

        </c:forEach>
</article>

<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>

