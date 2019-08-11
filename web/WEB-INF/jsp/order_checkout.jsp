<%@ page session="true" import="java.util.*, ua.andrii.project_7.entity.Ticket" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<jsp:include page="languages.jsp"/>
<br>

<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/menu.css"/>
    <h2> &nbsp;&nbsp; Showrooms</h2>
</head>
<body>
<jsp:include page="client_menu.jsp"/>
<br>
<span>${message}</span>
<span class="error">${error}</span>
<br>

<div align="left">
    <fieldset>
        <table border="0"
               cellpadding="0"
               width="100%" bgcolor="#FFFFFF">
            <tr>
                <td><b>Showroom_Code</b></td>
                <td><b>Showroom</b></td>
                <td><b>Exposition</b></td>
                <td><b>Price</b></td>
                <td><b>Quantity</b></td>
                <td></td>
            </tr>
            <c:forEach var="tickets" items="${sessionScope.shoppingcart}">
                <tr>
                    <td><b>${tickets.showroom.id}</b></td>
                    <td><b>${tickets.showroom.name}</b></td>
                    <td><b>${tickets.showroom.exposition.name}</b></td>
                    <td><b>${tickets.showroom.price}</b></td>
                    <td><b>${tickets.ticketQuantity }</b></td>
                </tr>
            </c:forEach>
            <tr>
                <td></td>
                <td></td>
                <td></td>
                <td><b><br><br></b></td>
                <td></td>
            </tr>

            <tr>
                <td></td>
                <td></td>
                <td><b>TOTAL</b></td>
                <td><b>${amount}</b></td>
                <td>
                    <form name="send_order" action="BookingServlet" method="POST">
                        <input type="hidden" name="action" value="SEND_ORDER">
                        <input type="submit" name="Submit" value="Send Order">
                    </form>
                </td>

            </tr>
        </table>
    </fieldset>
</div>
</body>
</html>