<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title"> Job </jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
       <jsp:useBean id="ad" scope="request" type="ru.itis.Tyshenko.dto.AdDTO"/>
       <c:out value="${ad}"/>
        <div class="container">
            <h2>${requestScope.get("ad").header}</h2>
            <a href="${pageContext.request.contextPath}/profile?id=${requestScope.get("ad").id}">${requestScope.get("ad").user_login}</a>
            <p>${requestScope.get("ad").description}</p>
            <p>${requestScope.get("ad").contact}</p>
            <p>${requestScope.get("ad").price}</p>
        </div>
    </jsp:attribute>
    <jsp:attribute name="footer"><t:footer/></jsp:attribute>
</t:layout>
