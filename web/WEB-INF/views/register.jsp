<%@include file="templates/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="border2"></div>
<article>

    <form:form method="POST" action="register" commandName="user">

        <label>
            Login<br>
            <form:input path="login"/><br>
            <form:errors path="login" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
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
            Password<br>
            <form:password path="password"/><br>
            <form:errors path="password" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <button class="button" type="submit">Register</button>
    </form:form>


</article>
<%@include file="templates/footer.jsp" %>
