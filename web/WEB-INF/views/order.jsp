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
        refreshOrderPanel();
    });

</script>
<aside class="sidebar">
    <div id="order-panel">

    </div>

</aside>