<%--@elvariable id="isOwner" type="java.lang.String"--%>
<%--@elvariable id="isMember" type="java.lang.String"--%>

<jsp:useBean id="group" scope="request" type="zarudnyi.trials.restaurant.model.entity.Group"/>
<%@include file="templates/header.jsp" %>
<div class="border2"></div>
<article>
    <h4 contenteditable="${isOwner}">${group.name}</h4>
    <c:if test="${isOwner.equals(\"true\")}">
        <a href="removeGroup?group_id=${group.id}">[Remove]</a> <br>
    </c:if>
    <c:if test="${isMember.equals(\"true\")&& isOwner.equals(\"false\")}">
        <a href="leaveGroup?group_id=${group.id}">[Leave]</a> <br>
    </c:if>
    <br>
    <jsp:useBean id="members" scope="request" type="java.util.List<zarudnyi.trials.restaurant.model.entity.User>"/>
    <c:forEach items="${members}" var="user">
        ${user.login} (${user.fname} ${user.lname} )
        <c:if test="${isOwner.equals(\"true\")}">
            <a href="removeUserFromGroup?group_id=${group.id}&user_id=${user.id}">[Remove]</a> <br>
        </c:if> <br>
    </c:forEach>
</article>
<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>
