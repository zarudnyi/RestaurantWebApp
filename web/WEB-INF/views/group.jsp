<%--@elvariable id="isOwner" type="java.lang.Boolean"--%>
<%--@elvariable id="isMember" type="java.lang.Boolean"--%>
<%--@elvariable id="groupOrderExists" type="java.lang.Boolean"--%>

<jsp:useBean id="group" scope="request" type="zarudnyi.trials.restaurant.model.entity.Group"/>
<%@include file="templates/header.jsp" %>
<div class="border2"></div>
<article>
    <h4 contenteditable="${isOwner}">${group.name}</h4>
    <c:choose>
        <c:when test="${isOwner}">
            <c:if test="${!groupOrderExists}">
                <a href="groupOrder?group_id=${group.id}">[Place Order]</a> <br>

            </c:if>
        </c:when>
        <c:otherwise><%--@elvariable id="owner" type="zarudnyi.trials.restaurant.model.entity.User"--%>
            ${owner.login} (${owner.fname} ${owner.lname})
        </c:otherwise>
    </c:choose>

    <c:if test="${isMember && !isOwner}">
        <a href="leaveGroup?group_id=${group.id}&user_id=${currUser.id}">[Leave]</a> <br>
    </c:if>
    <c:if test="${!isMember}">
        <c:choose>
            <c:when test="${isPendingApprove}">
                [Pending approve from group owner]
            </c:when>
            <c:otherwise>
                <a href="inviteToGroup?group_id=${group.id}&user_id=${currUser.id}">[Asc Invitation]</a> <br>
            </c:otherwise>
        </c:choose>
    </c:if>
    <br>
    <h5>Members:</h5>
    <jsp:useBean id="members" scope="request" type="java.util.List<zarudnyi.trials.restaurant.model.entity.User>"/>
    <p>
        <c:forEach items="${members}" var="user">
            ${user.login} (${user.fname} ${user.lname} )
            <c:if test="${isOwner}">
                <a href="removeUserFromGroup?group_id=${group.id}&user_id=${user.id}">[Remove]</a> <br>
            </c:if> <br>
        </c:forEach>
    </p>
    <c:if test="${isOwner}">

        <jsp:useBean id="candidates" scope="request"
                     type="java.util.List<zarudnyi.trials.restaurant.model.entity.User>"/>
        <c:if test="${candidates.size()>0}">

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
