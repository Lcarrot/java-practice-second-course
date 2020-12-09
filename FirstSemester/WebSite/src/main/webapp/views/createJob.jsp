<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:attribute name="title"> Create new job </jsp:attribute>
    <jsp:attribute name="header"> <t:header/> </jsp:attribute>
    <jsp:attribute name="main">
        <t:form postAction="">
            <jsp:attribute name="title">
                Create Job
            </jsp:attribute>
            <jsp:attribute name="feilds">
                <t:inputField>
        <jsp:attribute name="label">
        <label for="label" class="col-sm-2 col-form-label">header:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="header" type="text" id="label" class="form-control"
               placeholder="write your header" required>
    </jsp:attribute>
        </t:inputField>
        <t:inputField>
        <jsp:attribute name="label">
        <label for="desc" class="col-sm-2 col-form-label">description:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="description" type="text" id="desc" class="form-control"
               placeholder="write your description" required>
    </jsp:attribute>
        </t:inputField>
        <t:inputField>
        <jsp:attribute name="label">
        <label for="price" class="col-sm-2 col-form-label">price:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="price" type="number" id="price" class="form-control"
               placeholder="write your price" required>
    </jsp:attribute>
                </t:inputField>
    <t:inputField>
        <jsp:attribute name="label">
        <label for="contact" class="col-sm-2 col-form-label">Contact information:</label>
    </jsp:attribute>
    <jsp:attribute name="input">
        <input name="contact" type="text" id="contact" class="form-control"
               placeholder="Write your phone, facebook or else" required>
    </jsp:attribute>
        </t:inputField>
    </jsp:attribute>
        </t:form>
    </jsp:attribute>
    <jsp:attribute name="footer"> <t:footer/></jsp:attribute>
</t:layout>
