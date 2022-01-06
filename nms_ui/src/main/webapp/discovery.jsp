<%@ page import="java.sql.Statement" %>
<%@ page import="action.dao.UserDAO" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: smit
  Date: 3/1/22
  Time: 7:01 PM
  To change this template use File | Settings | File Templates.
--%>
<jsp:include page="index.jsp"></jsp:include>

<hr/>

<%@ taglib uri="/struts-tags" prefix="s" %>

    <link rel="stylesheet" href="css/style.css">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.7.2/font/bootstrap-icons.css">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

    <script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>

    <script src="js/index.js"></script>

    <h3 class="middle">Create IP</h3>

    <div class="demo">

        <nav>

            <a href="" class="disc__new middle" onclick="openForm('myForm')">new</a>

            <a href="" class="disc__new middle" onclick="openForm('myTable')">discoverd_IP</a> <br><br>

        </nav>

    </div>

    <hr/>

    <div class="demo">

        <table class="disc__table" style="width: 95%" id="tb_data" id="myTable">

            <thead>

            <tr class="disc__heading">

                <th scope="col">Name</th>

                <th scope="col">IP/Host</th>

                <th scope="col">Options</th>

            </tr>

            </thead>

            <hr/>

            <tbody>

            <%
                try
                {
                    ResultSet resultSet = UserDAO.getResultSet();

                    while (resultSet.next())
                    {

            %>
                            <tr class="disc__data">

                                <td><%=resultSet.getString(1) %></td>

                                <td><%=resultSet.getString(2) %></td>

                                <td><i class="bi bi-play disc__discovery"></i> &nbsp; <i class="bi bi-tv disc__monitor"></i> &nbsp; <i class="bi bi-pencil-square"></i> &nbsp; <i class="bi bi-trash"></i></td>

                            </tr>
            <%
                    }

                    resultSet.close();
                }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }

            %>

            </tbody>

        </table>

    </div>

    <div class="form-popUp">

        <form action="discoveryProcess" method="POST" class="form-container" id="myForm">

            <div class="demo">

                <h3>Discover IP</h3> <br>

            </div>

            <div id="type" class="sample">

                <p class="name">Name</p>

                <p class="ip">IP/Host</p><br>

            </div>

            <div class="sample">

                <input type="text" name="name" placeholder="Enter Name" id="name" class="disc" required>

                <input type="text" name="ip" placeholder="Enter IP/Host" id="ip" class="address" required> <br>

            </div>

            <div class="demo">

                <p class="name">Device Type</p>

                <select name="deviceType" id="deviceType" class="list" style="width: 10%;height: 25px" data-drop-down="true" data-required="true" onchange="displayLinuxProfile('profile', this)" required>

                    <option value="0" class="ping">Ping</option>

                    <option value="1" class="linux">Linux</option>

                </select>

                <br><br>

            </div>

            <div class="event" id="profile">

                <div class="demo">

                    Username : <input type="text" name="discoveryUsername" id="discoveryUsername" class="linux_user" placeholder="Linux Username" required> &nbsp;&nbsp;

                    Password : <input type="password" id="discoveryPassword" class="linux_pass" placeholder="Linux Password" required> <br><br>

                </div>

            </div>

            <div class="demo">

                <button type="button" class="btn" onclick="discoverData()">Create</button>

                <button type="button" class="btn cancel" onclick="closeForm('myForm')">Cancel</button>

            </div>

        </form>

    </div>

    <div id="loader"></div>
