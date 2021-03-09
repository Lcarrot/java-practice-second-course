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
<a href="?lang=ru">Ру</a>
<br>
<a href="?lang=en">En</a>
<br>
<h1><@spring.message "sign_up_page.registration.title"/></h1>
<@spring.bind "userForm"/>
<form method="post">
    <label for="login"> <@spring.message "login.label"/> </label>
    <br>
    <input type="text" id="login" name="login">
    <br>
    <label> <@spring.message "email.label"/> </label>
    <@spring.formInput "userForm.email"/>
    <@spring.showErrors "error"/>
    <br>
    <label for="password"> <@spring.message "password.label"/> </label>
    <@spring.formPasswordInput "userForm.password"/>
    <@spring.showErrors "error"/>
    <br>
    <label> <@spring.message "country.label"/> </label>
    <@spring.formInput "userForm.country"/>
    <@spring.showErrors "error"/>
    <br>
    <label> <@spring.message "gender.label"/> </label>
    <@spring.formInput "userForm.gender"/>
    <@spring.showErrors "error"/>
    <br>
    <input type="hidden" value="${_csrf_token}" name="_csrf_token">
    <button type="submit"> <@spring.message "button"/></button>
</form>
<#if repeatableFields??>
    <p>${repeatableFields}</p>
</#if>
</body>
</html>