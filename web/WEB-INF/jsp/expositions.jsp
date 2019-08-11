<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div align="left">
    <table>
        <tr>
            <td>
                <form name="expositions_form" action="BookingServlet" method="POST">
                    <b>Choose an exposition :</b>
                    <select name=expositions.exposition_selected>
                        <c:forEach var="exposition" items="${expositionslist}">
                            <option>${exposition.presentation}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="action" value="EDIT_EXPOSITION_LINK">
                    <input type="submit" name="Submit" value="Edit Exposition">
                </form>
            </td>
            <td>
                <form name="exposition_add_new" action="BookingServlet" method="POST">
                    <input type="hidden" name="action" value="EXPOSITION_CREATE_LINK">
                    <input type="submit" name="Submit" value="Add new Exposition">
                </form>
            </td>
        </tr>
    </table>
</div>
