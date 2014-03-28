<%@ page import="com.my.bussiness.beans.Book" %>
<%@ page import="com.my.dao.BooksDao" %>
<%@ page import="org.apache.log4j.Logger" %>
<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 09.03.14
  Time: 13:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Book by id</title>
    <%@ include file="/jsp/includes/head.jsp" %>
</head>
<body>
    <%@ include file="/jsp/includes/header.jsp" %>
    <%--<jsp:include page="includes/header.jsp" flush="true" />--%>

    <div class="col-md-1 col-md-offset-11">
        <a href="/library/books/add">
            <button class="btn custom-button">Add new book</button>
        </a>
    </div>

    <div class="col-md-12" style="padding: 0 20px; margin: 0;">
        <%
            long id = (Long) request.getAttribute("bookToDisplayId");
            Book book = new BooksDao().selectById(id);
            config.getServletContext().setAttribute("bookToDisplay", book);
        %>
        <div class="row book-item-block" style="background-color: #427278;">
            <jsp:include page="/jsp/includes/book.jsp" flush="true" />
        </div>
    </div>

</body>
</html>