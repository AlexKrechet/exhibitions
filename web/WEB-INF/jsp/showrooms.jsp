<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="left">
    <table>
        <tr>
            <td>
                <form name="showrooms_form" action="BookingServlet" method="POST">
                    <b>Choose a showroom :</b>
                    <select name=showrooms.showroom_selected>
                        <c:forEach var="showroom" items="${showroomslist}">
                            <option>${showroom.presentation}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="action" value="EDIT_SHOWROOM_LINK">
                    <input type="submit" name="Submit" value="Edit Showroom">
                </form>
            </td>
            <td>
                <form name="showroom_add_new" action="BookingServlet" method="POST">
                    <input type="hidden" name="action" value="SHOWROOM_CREATE_LINK">
                    <input type="submit" name="Submit" value="Add new Showroom">
                </form>
            </td>
        </tr>
    </table>
</div>

