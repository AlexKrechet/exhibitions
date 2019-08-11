<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="languages.jsp"/>
<br>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
    <h2> &nbsp;&nbsp; Showroom edit</h2>
</head>
<body>
<jsp:include page="admin_menu.jsp"/>
<br>
<span>${message}</span>
<span class="error">${error}</span>
<br>
<jsp:include page="showrooms.jsp" flush="true"/>
<form name="edit_a_showroom" action="BookingServlet" method="POST">
    <fieldset>
        <table>
            <tr>
                <td>Code:</td>
                <td><input class="dataField" id="showroom_id" type="text" readonly name="showroom_id"
                           value="${showroom_id}"/><br/></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input class="dataField" id="showroom_name" type="text" name="showroom_name"
                           value="${showroom_name}"/><br/></td>
            </tr>
            <tr>
                <td>Price:</td>
                <td><input class="dataField" id="showroom_price" input type="number" min="0.01" max="1000000"
                           step="0.01"
                           name="showroom_price" value="${showroom_price}"/><br/></td>
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
                <td><input type="hidden" name="action" value="UPDATE_SHOWROOM_INFO"> <input type="submit"
                                                                                            class="myButton"
                                                                                            value="Update a showroom"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>