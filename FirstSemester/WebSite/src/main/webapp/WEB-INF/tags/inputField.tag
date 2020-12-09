<%@tag description="form for registration new user" pageEncoding="UTF-8" %>
<%@attribute name="label" fragment="true" %>
<%@attribute name="input" fragment="true" %>

<div class="container">
    <div class="form-group row">
        <jsp:invoke fragment="label" />
        <div class="col-sm-10">
            <jsp:invoke fragment="input" />
        </div>
    </div>
</div>