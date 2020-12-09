<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title"> Job List </jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
        <jsp:useBean id="resumes" type="java.util.List"/>
        <div class="row"></div>
        <c:forEach items="${resumes}" var="resume">
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${resume.header}</h5>
                    <p class="card-text">${resume.description}</p>
                    <p class="card-text">${resume.contact}</p>
                    <a href="${pageContext.request.contextPath}/profile?id=${resume.user_id}">user</a>
                </div>
            </div>
        </c:forEach>
    </jsp:attribute>
    <jsp:attribute name="footer"><t:footer/></jsp:attribute>
</t:layout>

