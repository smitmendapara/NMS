<%--
  Created by IntelliJ IDEA.
  User: smit
  Date: 31/12/21
  Time: 3:19 PM
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="index.jsp"></jsp:include>

<hr/>

<%@ taglib uri="/struts-tags" prefix="s" %>

    <link rel="stylesheet" href="css/style.css">

    <s:form action="loginprocess" method="POST">

        <div class="demo">

            <h3>Login Motadata</h3>

            <s:textfield name="username" label="Name" class="user"></s:textfield>

            <s:password name="password" label="Password" class="pass"></s:password>

            <s:submit value="login" class="log"></s:submit>

        </div>

    </s:form>