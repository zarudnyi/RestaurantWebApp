<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:useBean id="user" scope="request" type="zarudnyi.trials.restaurant.model.entity.User"/>
<%@include file="templates/header.jsp" %>
<div class="border2"></div>
<article>
    <h4>Hello ${user.fname==null?user.login:user.fname}</h4>
    <p><a href="/logout">[Logout]</a></p>


    <form:form method="POST" action="updateUser" commandName="user">

        <label>
            Login<br>
            <form:input path="login" disabled="true" /><br>
            <br>
        </label>
        <label>
            Name<br>
            <form:input path="fname"/><br>
            <form:errors path="fname" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <label>
            Last Name<br>
            <form:input path="lname"/><br>
            <form:errors path="lname" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <label>
            New Password<br>
            <form:password path="password"/><br>
            <form:errors path="password" cssStyle="color: #ff0000; font-size: small; font-family: serif"/> <br>
        </label>
        <button class="button" type="submit">Update</button>

    </form:form>
    <br>
    <div class="border2"></div>

    <h4>My Groups</h4>
    <br>
    <%--@elvariable id="myGroups" type="java.util.List<Group>"--%>
    <c:choose>
        <c:when test="${myGroups.size()==0}">
            You dont have any groups  <br>
        </c:when>
        <c:otherwise>
          <c:forEach items="${myGroups}" var="group">
              ${group.name} <a href="removeGroup?group_id=${group.id}">[Remove]</a>  <br>
          </c:forEach>
        </c:otherwise>
    </c:choose>
    <br>
    <div class="border2"></div>
    <br>
    <c:if test="${myGroups.size()>0}">
        <h4>I am member of groups</h4>
        <br>
            <%--@elvariable id="memberOfGroups" type="java.util.List<Group>"--%>
            <c:forEach items="${memberOfGroups}" var="group">
                ${group.name} <a href="leaveGroup?group_id=${group.id}">[Leave]</a>  <br>
            </c:forEach>
    </c:if>



</article>
<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>
