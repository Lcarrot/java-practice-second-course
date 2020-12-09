<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title"> Sign In </jsp:attribute>
    <jsp:attribute name="header">
        <t:header />
    </jsp:attribute>
    <jsp:attribute name="main">
        <t:form postAction="signIn">
            <jsp:attribute name="title">
                Sign In
            </jsp:attribute>
            <jsp:attribute name="feilds">
                <t:inputField>
    <jsp:attribute name="label">
         <label for="login" class="col-sm-2 col-form-label">Login:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
         <input name="login" id="login" class="form-control" placeholder="write your name here">
    </jsp:attribute>
</t:inputField>
                <t:inputField>
    <jsp:attribute name="label">
        <label for="password" class="col-sm-2 col-form-label">password:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="password" type="password" id="password" class="form-control"
               placeholder="write your password here">
    </jsp:attribute>
</t:inputField>
                <div class="container">
                    <div class="form-group row">
                <c:if test="${sessionScope.get('authorized') == 'false'}">
                    <p>
                            ${requestScope.get("errorCode")}
                    </p>
                </c:if>
                    </div>
                </div>
            </jsp:attribute>
        </t:form>
    </jsp:attribute>
    <jsp:attribute name="footer">
        <t:footer />
    </jsp:attribute>
</t:layout>
