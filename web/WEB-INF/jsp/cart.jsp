<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div align="center">
    <table border="0" cellpadding="0" width="100%" bgcolor="#FFFFFF">
        <tr>
            <td><b>SHOWROOM_ID</b></td>
            <td><b>SHOWROOM</b></td>
            <td><b>EXPOSITION</b></td>
            <td><b>PRICE</b></td>
            <td><b>QUANTITY</b></td>
            <td></td>
        </tr>
        <c:forEach var="anOrder" items="${sessionScope.shoppingcart}" varStatus="loop">
            <tr>
                <td><b>${anOrder.showroom.id}</b></td>
                <td><b>${anOrder.showroom.name}</b></td>
                <td><b>${anOrder.showroom.exposition.name}</b></td>
                <td><b>${anOrder.showroom.price }</b></td>
                <td><b>${anOrder.ticketQuantity }</b></td>
                <td>
                    <form name="deleteForm"
                          action="BookingServlet"
                          method="POST">
                        <input type="submit" value="Delete">
                        <input type="hidden" name="delindex" value="${loop.index}">
                        <input type="hidden" name="action" value="DELETE">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <p>
    <form name="checkoutForm"
          action="BookingServlet"
          method="POST">
        <input type="hidden" name="action" value="CHECKOUT">
        <input type="submit" name="Checkout" value="Checkout">
    </form>
</div>