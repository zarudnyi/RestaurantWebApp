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
    <c:if test="${isMember.equals(\"false\")}">
        <a href="inviteToGroup?group_id=${group.id}">[Asc Invitation]</a> <br>
    </c:if>
    <br>
    <h5>Members:</h5>
    <jsp:useBean id="members" scope="request" type="java.util.List<zarudnyi.trials.restaurant.model.entity.User>"/>
    <p>
        <c:forEach items="${members}" var="user">
            ${user.login} (${user.fname} ${user.lname} )
            <c:if test="${isOwner.equals(\"true\")}">
                <a href="removeUserFromGroup?group_id=${group.id}&user_id=${user.id}">[Remove]</a> <br>
            </c:if> <br>
        </c:forEach>
    </p>
    <c:if test="${isOwner.equals(\"true\")}">
        <jsp:useBean id="candidates" scope="request"
                     type="java.util.List<zarudnyi.trials.restaurant.model.entity.User>"/>
        <c:if test="${candidates.size()>0}}">

            <h5>Candidates:</h5>

            <p>
                <c:forEach items="${candidates}" var="user">
                    ${user.login} (${user.fname} ${user.lname} )
                    <a href="approveUserToGroup?group_id=${group.id}&user_id=${user.id}">[Approve]</a>
                    <a href="rejectUserFromGroup?group_id=${group.id}&user_id=${user.id}">[Reject]</a>
                    <br>
                </c:forEach>
            </p>
        </c:if>
    </c:if>

</article>
<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>
