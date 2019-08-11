<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="languages.jsp"/>
<br>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
    <h2> &nbsp;&nbsp; Create a Showroom </h2>
</head>
<body>
<jsp:include page="admin_menu.jsp"/>
<br>
<span>${message}</span>
<span class="error">${error}</span>
<br>
<jsp:include page="showrooms.jsp" flush="true"/>
<form name="create_a_showroom" action="BookingServlet" method="POST">
    <fieldset>
        <table>
            <tr>
                <td>Name:</td>
                <td><input class="dataField" id="showroom_name" type="text" name="showroom_name" value=""/><br/>
                </td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input class="dataField" id="showroom_price" input type="number" min="0.01" max="1000000"
                           step="0.01"
                           name="showroom_price" value="0"/><br/></td>
            </tr>
            <tr>
                <td>Exposition:</td>
                <td>
                    <form name="expositions_form" action="BookingServlet" method="POST">
                        <select name=showrooms.exposition_selected>
                            <c:forEach var="exposition" items="${showroom_expositionslist}">
                                <option>${exposition.presentation}</option>
                            </c:forEach>
                        </select>
                    </form>
                    <br/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="hidden" name="action" value="ADD_NEW_SHOWROOM_BY_ADMIN">
                    <input type="submit" class="myButton" value="Create a showroom"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>