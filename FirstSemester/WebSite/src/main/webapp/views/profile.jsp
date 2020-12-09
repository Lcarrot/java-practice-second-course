<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title"> Job </jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
        <jsp:useBean id="user" scope="request" type="ru.itis.Tyshenko.dto.UserDTO"/>
        <c:out value="${user}"/>
        <div class="container">
            <p>login : ${requestScope.get("user").login}</p>
            <p>country : ${requestScope.get("user").country}</p>
            <p>gender : ${requestScope.get("user").gender}</p>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer"><t:footer/></jsp:attribute>
</t:layout>
