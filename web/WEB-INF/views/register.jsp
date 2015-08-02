<%@include file="templates/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="border2"></div>
<article>

<form:form method="POST"  action="register">
        <label>
            Login<br>
            <form:input path="login"  />
            <form:errors path="login" cssStyle="color: #ff0000;"/>
        </label>
        <label>
            Name<br>
            <form:input path="fname"  />
            <form:errors path="fname" cssStyle="color: #ff0000;"/>

        </label>
        <label>
            Last Name<br>
            <form:input path="lname"  />
            <form:errors path="lname" cssStyle="color: #ff0000;"/>        </label>
        <label>
            New Password<br>
            <form:password path="password"  />
            <form:errors path="password" cssStyle="color: #ff0000;"/>        </label>
    </label>
        <button class="button" type="submit">Register</button>
</form:form>

    <c:if test="${errors!=null}">

        ${errors.toString()}
    </c:if>


</article>
<%@include file="templates/footer.jsp" %>
