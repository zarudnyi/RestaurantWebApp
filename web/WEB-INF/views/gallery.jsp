<%@include file="templates/header.jsp" %>

<div class="border"></div>
<article class="fullwidth gallery">
    <h3>Gallery</h3>
    <c:forEach var="item" items="${gallery}">
        <a class="fancyimage" href="resources/images/${item}"><img src="resources/images/${item}" width="200" height="200" alt="" /></a>
    </c:forEach>
<div class="border2"></div>
<br>

<%@include file="templates/footer.jsp" %>
