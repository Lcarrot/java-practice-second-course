<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title">Your profile</jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
        <jsp:useBean id="user" scope="session" type="ru.itis.Tyshenko.dto.UserDTO"/>
        <c:out value="${user}"/>
        <div class="container">
            <p> Login : ${sessionScope.get("user").login}</p>
            <p> Email : ${sessionScope.get("user").email}</p>
            <p> Country : ${sessionScope.get("user").country}</p>
            <p> Gender : ${sessionScope.get("user").gender}</p>
            <a href="${pageContext.request.contextPath}/service/profile/change"> Change your data</a>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer"> <t:footer/></jsp:attribute>
</t:layout>
