<%@tag description="form for registration new user" pageEncoding="UTF-8" %>
<%@attribute name="title" fragment="true" %>
<%@attribute name="feilds" fragment="true" %>
<%@attribute name="postAction" required="true"%>

<form action="${postAction}" method="post">
    <h2 style="text-align: center">
        <jsp:invoke fragment="title"/>
    </h2>
    <div>
        <jsp:invoke fragment="feilds" />
    </div>
    <div class="container">
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary" value="submit"> Send </button>
            </div>
        </div>
    </div>
</form>
