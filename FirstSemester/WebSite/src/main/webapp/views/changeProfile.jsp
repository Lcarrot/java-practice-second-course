<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:layout>
    <jsp:attribute name="title"> Change data </jsp:attribute>
    <jsp:attribute name="header"> <t:header/></jsp:attribute>
    <jsp:attribute name="main">
        <jsp:useBean id="user" scope="session" type="ru.itis.Tyshenko.dto.UserDTO"/>
        <c:out value="${user}"/>
        <t:form postAction="">
            <jsp:attribute name="title"> Change your data </jsp:attribute>
            <jsp:attribute name="feilds">
                <div class="container">
                    <label> login : ${sessionScope.get("user").login}</label>
                </div>
                <t:inputField>
    <jsp:attribute name="label">
        <label for="email" class="col-sm-2 col-form-label">e-mail:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
<input name="email" id="email" type="email" class="form-control"
       value="${sessionScope.get("user").email}"
       pattern="^[_A-Za-z0-9-+]+(\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,})$">
    </jsp:attribute>
</t:inputField>
<t:inputField>
    <jsp:attribute name="label">
        <label for="password" class="col-sm-2 col-form-label">type your password(required):</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="password" id="password" type="password" class="form-control"
               pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\S+$).{8,}$" required>
    </jsp:attribute>
</t:inputField>
<div class="container">
    <div class="form-group col-md-4">
        <label for="inputCountry">change country</label>
        <select id="inputCountry" class="form-control" name="country">
            <option selected value=""> Choose your country...</option>
            <option value="Canada" name="country"> Canada</option>
            <option value="Russia" name="country"> Russia</option>
            <option value="Ukraine" name="country"> Ukraine</option>
        </select>
    </div>
</div>
                <div class="container">
                    <div class="form-group row">
                    <pre>${requestScope.get("description")}</pre>
                    </div>
                </div>
            </jsp:attribute>
        </t:form>
    </jsp:attribute>
    <jsp:attribute name="footer"> <t:footer/></jsp:attribute>
</t:layout>
