<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title"> Job List </jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
        <div class="col-12 col-sm-12">
            <p>${requestScope.get("resume").header}</p>
            <p>${requestScope.get("resume").description}</p>
            <p>${requestScope.get("resume").contact}</p>
        </div>
        <c:if test="${sessionScope.get('adsIsEmpty') == 'false'}">
        <jsp:useBean id="ads" type="java.util.List"/>
        <div class="row"></div>
        <c:forEach items="${ads}" var="ads">
            <div class="card" style="width: 18rem;">
                <div class="card-body">
                    <h5 class="card-title">${ads.header}</h5>
                    <p class="card-text">{ads.description}</p>
                    <a href="${pageContext.request.contextPath}/jobs/job?id=${ads.id}" class="btn btn-primary">more details</a>
                </div>
            </div>
        </c:forEach>
        </c:if>
    </jsp:attribute>
    <jsp:attribute name="footer"><t:footer/></jsp:attribute>
</t:layout>
