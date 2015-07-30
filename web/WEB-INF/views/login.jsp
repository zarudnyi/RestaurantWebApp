<%@include file="templates/header.jsp" %>
<br>
<br>

<div class="home-widget">

    <form action="/j_spring_security_check" method="post">
        <h2 class="form-signin-heading">Please sign in</h2>
        <input type="text" class="form-control" name="j_username" placeholder="Login" required autofocus>
        <input type="password" class="form-control" name="j_password" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>
</div>


<%@include file="templates/footer.jsp" %>

