<jsp:useBean id="user" scope="request" type="zarudnyi.trials.restaurant.model.entity.User"/>
<%@include file="templates/header.jsp" %>
<div class="border2"></div>
<article>
    <h4>Hello ${user.fname==null?user.login:user.fname}</h4>

    <form name="updateUser" action="updateUser" method="post">
        <label>
            Login<br>
            <input name="login" type="text" value="${user.login}" disabled><br>
        </label>
        <label>
            Name<br>
            <input name="fname" type="text" value="${user.fname}"><br>
        </label>
        <label>
            Last Name<br>
            <input name="lname" type="text" value="${user.lname}"><br>
        </label>
        <label>
            New Password<br>
            <input name="password" type="password" value=""><br>
        </label>
        <button class="button" type="submit">Update</button>
    </form>
    <h5><a href="/logout">Logout </a></h5>


</article>
<%@include file="order.jsp" %>
<%@include file="templates/footer.jsp" %>
