<%--
  Created by IntelliJ IDEA.
  User: olga1
  Date: 16.10.2020
  Time: 1:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<form method="post">
    <div class="container">
        <div class="form-group row">
            <label for="name" class="col-sm-2 col-form-label">first name</label>
            <div class="col-sm-10">
                <input name="name" id="name" class="form-control" placeholder="write your name here">
            </div>
        </div>
    </div>
    <input type="hidden" value="${_csrf_token}" name="_csrf_token">
    <div class="container">
        <div class="form-group row">
            <label for="last_name" class="col-sm-2 col-form-label">last name</label>
            <div class="col-sm-10">
                <input name="last_name" id="last_name" class="form-control" placeholder="write your last name here">
            </div>
        </div>
    </div>
    <div class="container">
        <div class="form-group row">
            <label for="age" class="col-sm-2 col-form-label">age</label>
            <div class="col-sm-10">
                <input name="age" id="age" class="form-control" placeholder="write your age here">
            </div>
        </div>
    </div>
    <div class="container">
        <div class="form-group row">
            <label for="password" class="col-sm-2 col-form-label">password:</label>
            <div class="col-sm-10">
                <input name="password" id="password" class="form-control" placeholder="write your password here">
            </div>
        </div>
    </div>
    <div class="container">
        <div class="form-group row">
            <div class="col-sm-10">
                <button type="submit" class="btn btn-primary" value="submit"> Sign up</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>
