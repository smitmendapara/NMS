<%@ page import="org.apache.struts2.ServletActionContext" %>

<%--
  Created by IntelliJ IDEA.
  User: smit
  Date: 31/12/21
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>

<jsp:include page="index.jsp"></jsp:include>

<hr/>

    <%@ taglib uri="/struts-tags" prefix="s" %>

        <link rel="stylesheet" href="css/style.css">

        <br/>

        <%
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        %>

        <h4 class="access middle">Welcome, <s:property value="username"/></h4>

        <div class="demo">

            <nav>

                <a href="discover" class="lg__cover"><i class="bi bi-disc"></i>&nbsp;Discovery</a>

                <a href="monitor" class="lg__monitor"><i class="bi bi-tv"></i>&nbsp;Monitor</a>

                <a href="dashboard" class="lg__dashboard"><i class="bi bi-grid"></i></i>&nbsp;Dashboard</a>

            </nav>

        </div>