<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form method="post">
    <h3></h3>
    <label for="login"><@spring.message "email.label"/></label>
    <input type="text" id="login" name="email">
    <input type="hidden" value="${_csrf.token}" name="${_csrf.parameterName}">
    <br>
    <label for="password"><@spring.message "password.label"/></label><br>
    <input type="text" id="password" name="password">
    <button type="submit"><@spring.message "button"/> </button>
</form>
</body>
</html>