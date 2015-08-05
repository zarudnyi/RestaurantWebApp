<%@include file="templates/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="border2"></div>
<article>

    <form:form method="POST" action="createGroup" commandName="group">

        <label>
            Login<br>
            <form:input path="name"/><br>
            <form:errors path="name" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>

        <button class="button" type="submit">Create</button>
    </form:form>


</article>
<%@include file="templates/footer.jsp" %>
