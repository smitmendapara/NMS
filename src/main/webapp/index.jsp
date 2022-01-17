<%--
  Created by IntelliJ IDEA.
  User: smit
  Date: 31/12/21
  Time: 2:25 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

    <head>

        <title>Home Page</title>

        <link rel="stylesheet" href="css/style.css">

        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

        <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

    </head>

    <body>

        <div class="first" style="text-align: end; margin: 20px 20px 0 0;">

            <h2>Light - NMS</h2>

            <nav class="effect">

                <a href="login" class="in" onclick="addClassByClick()"><i class="bi bi-box-arrow-in-right"></i>&nbsp;login</a>

                <a href="logout" class="out" onclick="addClassByClick()"><i class="bi bi-box-arrow-left"></i>&nbsp;logout</a>

                <a href="signup" class="up"><i class="bi bi-person"></i>&nbsp;signUp</a>

                <a href="profile" class="file"><i class="bi bi-check2-circle"></i>&nbsp;profile</a>

            </nav>

            <br>

        </div>

    </body>

</html>
