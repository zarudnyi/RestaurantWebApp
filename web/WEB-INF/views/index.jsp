<%@include file="templates/header.jsp" %>
<br/>
<br/>
<br/>
<br/>
<br/>

<div class="pikachoose">
    <ul id="pikame">
        <c:forEach var="item" items="${gallery}">
            <li><a href=""><img src="resources/images/${item}" alt="" /></a></li>
        </c:forEach>
    </ul>
</div>
<div class="clear"></div>
<div class="border"></div>

<div class="widgets">
<c:forEach var="item" items="${firstDishes}">
    <div class="home-widget">
        <h3>${item.name}</h3>
        <img src="resources/images/${item.picture}" width="300" alt="" />
        <p>${item.description}</p>
    </div>
</c:forEach>
</div>
<div class="border2"></div>
<br />
<c:forEach var="item" items="${secondDishes}">
    <div class="home-widget">
        <h3>${item.name}</h3>
        <img src="resources/images/${item.picture}" width="300" alt="" />
        <p>${item.description}</p>
    </div>
</c:forEach>



    <%@include file="templates/footer.jsp" %>

