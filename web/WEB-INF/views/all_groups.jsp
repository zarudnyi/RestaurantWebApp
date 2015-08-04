<jsp:useBean id="groups" scope="request" type="java.util.List<zarudnyi.trials.restaurant.model.entity.Group>"/>
<jsp:useBean id="owners" scope="request" type="java.util.Map<zarudnyi.trials.restaurant.model.entity.Group,zarudnyi.trials.restaurant.model.entity.User>"/>

<%@include file="templates/header.jsp" %>
<div class="border2"></div>
<article>
    <c:forEach items="${groups}" var="group">
        <c:set var="owner" scope="session" value="${owners.get(group)}"/>


        <a href="group?id=${group.id}">${group.name}</a> [${owner.login}] <br>

    </c:forEach>

</article>
<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>
