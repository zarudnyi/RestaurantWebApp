<%@include file="templates/header.jsp" %>
<br/>
<br/>
<br/>
<br/>
<br/>

<div class="home-widget">

    <form action="/j_spring_security_check" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Login" required autofocus>
        <input type="password" class="form-control" name="j_password" placeholder="Password" required>

        <div class="email-us">
            <button class="button" type="submit">Login</button>
        </div>
    </form>
    <a href="register">Register</a>
    <c:if test="${!(empty param.error)}">
        Invalid login or password
    </c:if>
</div>


<%@include file="templates/footer.jsp" %>

