<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Awesome Restaurant</title>
    <link href="resources/styles/style.css" rel="stylesheet" type="text/css" media="screen"/>
    <link href="resources/styles/base.css" rel="stylesheet" type="text/css" media="screen"/>

    <link href="resources/styles/jumbotron-narrow.css" rel="stylesheet" type="text/css" media="screen"/>


    <script type="text/javascript" src=" https://ajax.googleapis.com/ajax/libs/jquery/1.6/jquery.js"></script>
    <script type="text/javascript" src="resources/scripts/jquery.pikachoose.js"></script>
    <script type="text/javascript" src="resources/scripts/jquery.cookie.js"></script>

    <script type="text/javascript" src="resources/scripts/restaurant.js"></script>

    <script type="text/javascript">
        $(document).ready(function () {
            $("#pikame").PikaChoose();


            if (window.location.pathname == "/") {
                $("a[href='/']").addClass("current");
            }
            if (window.location.pathname == "/menu") {
                $("a[href='menu']").addClass("current");
            }
            if (window.location.pathname == "/contact") {
                $("a[href='contact']").addClass("current");
            }
            if (window.location.pathname == "/gallery") {
                $("a[href='gallery']").addClass("current");
            }
            if (window.location.pathname == "/profile" || window.location.pathname == "/login" || window.location.pathname == "/updateUser") {
                $("a[href='profile']").addClass("current");
            }
            if (window.location.pathname == "/group" || window.location.pathname == "/groups") {
                $("a[href='groups']").addClass("current");
            }
            if (window.location.pathname == "/contact" ) {
                $("a[href='contact']").addClass("current");
            }
            if (window.location.pathname == "/admin" ) {
                $("a[href='admin']").addClass("current");
            }

        })
        ;
    </script>
</head>
<body>
<div id="container">
    <header>
        <nav>
            <ul id="nav">

                <li><a href="/">Home</a></li>
                <li><a href="menu">Menu</a></li>
                <li><a href="gallery">Gallery</a></li>
                <li><a href="profile">
                    <sec:authorize access="isAuthenticated()" var="authenticated"/>
                    <c:choose>
                        <c:when test="${authenticated}">
                            Profile
                        </c:when>
                        <c:otherwise>
                            Login
                        </c:otherwise>
                    </c:choose>
                </a></li>

                <sec:authorize access="isAuthenticated()" var="authenticated"/>
                <c:choose>
                    <c:when test="${authenticated}">
                        <li><a href="groups">Groups</a></li>
                    </c:when>
                </c:choose>
                <sec:authorize access="hasRole('ROLE_ADMIN')" var="isAdmin" />
                <c:choose>
                    <c:when test="${isAdmin}">
                        <li><a href="admin">Administration</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="contact">Contact</a></li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </nav>
        <hgroup class="intro">
            <h1 class="title">Awesome Restaurant</h1>
        </hgroup>
        <div class="reservations"><br/>
            <span class="reservations-title">Call Us:</span>
            <hr class="hr-solid"/>
            <span class="phone-number">+012 345 6789</span>

        </div>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
    </header>
    <div class="wrapper">
