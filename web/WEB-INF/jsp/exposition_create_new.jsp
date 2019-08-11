<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="languages.jsp"/>
<br>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
    <h2> &nbsp;&nbsp; Exposition Create</h2>
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
                <td>Name:</td>
                <td><input class="dataField" id="name" type="text" name="name" value=""/><br/></td>
            </tr>
            <tr>
                <td>Event start date:</td>
                <td><input class="dataField" id="event_start_date" type="date" name="event_start_date" value=""/><br/>
                </td>
            </tr>
            <tr>
                <td>Event end date:</td>
                <td><input class="dataField" id="event_end_date" type="date" name="event_end_date" value=""/><br/></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="hidden" name="action" value="ADD_NEW_EXPOSITION_BY_ADMIN"> <input type="submit"
                                                                                                   class="myButton"
                                                                                                   value="Create an exposition"/>
                </td>
            </tr>
        </table>
    </fieldset>
</form>
</body>
</html>