<sec:authorize access="isAuthenticated()" var="authenticated"/>
<sec:authentication var="user" property="principal"/>

<script type="text/javascript">
    summarySelected = true;

    <c:choose>
    <c:when test="${authenticated}">
    currentUser = '${user.username}';
    </c:when>
    <c:otherwise>
    currentUser = '';
    </c:otherwise>
    </c:choose>
    $(document).ready(function () {

        if (${authenticated}==true
        )
        {
            refreshOrderPanel();

            //  setInterval(function(){refreshOrderPanel();}, 5000);

        }
    });

</script>
<aside class="sidebar">
    <div id="order-panel">

    </div>

</aside>
<div id="order-checkout" title="Checkout" hidden>
    <form method="post" action="api/order/checkOut">

        <label for="datepicker">Time</label><input type="text" name="date" id="datepicker" required>
        <input hidden id="order-id" name="order_id">
        <button type="submit">Checkout</button>

    </form>
</div>
