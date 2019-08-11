<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="languages.jsp"/>
<br>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
    <h2> &nbsp;&nbsp; Exposition edit</h2>
</head>
<body>
<jsp:include page="admin_menu.jsp"/>
<br>
<span>${message}</span>
<span class="error">${error}</span>
<br>
<jsp:include page="expositions.jsp" flush="true"/>
<form name="create_an_exposition" action="BookingServlet" method="POST">
    <fieldset>
        <table>
            <tr>
                <td>Code:</td>
                <td><input class="dataField" id="exposition_id" type="text" readonly name="exposition_id"
                           value="${exposition_id}"/><br/></td>
            </tr>
            <tr>
                <td>Name:</td>
                <td><input class="dataField" id="exposition_name" type="text" name="exposition_name"
                           value="${exposition_name}"/><br/></td>
            </tr>
            <tr>
                <td>Start:</td>
                <td><input class="dataField" id="exposition_start_date" type="date" name="exposition_start_date"
                           value="${exposition_start_date}"/><br/></td>
            </tr>
            <tr>
                <td>End:</td>
                <td><input class="dataField" id="exposition_end_date" type="date" name="exposition_end_date"
                           value="${exposition_end_date}"/><br/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="hidden" name="action" value="UPDATE_EXPOSITION_INFO"> <input type="submit"
                                                                                              class="myButton"
                                                                                              value="Update an exposition"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>