<%@include file="templates/header.jsp" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="border2"></div>
<article>

    <form:form method="POST" action="createItem" commandName="item">

        <label>
            Name<br>
            <form:input path="name"/><br>
            <form:errors path="name" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <label>
            Price<br>
            <form:input  path="floatPrice" cssStyle="type: number;"/><br>
            <form:errors path="floatPrice" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <label>
            Category<br>
            <form:select path="categoryId">
                <form:options items="${categories}" />
            </form:select><br>
        </label>

        <label>
            Description<br>
            <form:textarea path="description"/><br>
            <form:errors path="description" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>
        <label>
            Picture<br>
            <form:input path="picture"/><br>
            <form:errors path="picture" cssStyle="color: #ff0000; font-size: small; font-family: serif"/><br>
        </label>

        <button class="button" type="submit" >Create</button>
    </form:form>


</article>
<%@include file="templates/footer.jsp" %>
