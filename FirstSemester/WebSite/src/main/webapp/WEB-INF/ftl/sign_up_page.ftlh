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
<@spring.bind "userDTO"/>
<form method="post">
    <label for="login">login:</label>
    <br>
    <input type="text" id="login" name="login">
    <br>
    <label>Email</label>
    <@spring.formInput "userDTO.email"/>
    <@spring.showErrors "error"/>
    <br>
    <label for="password">Пароль</label>
    <@spring.formPasswordInput "userDTO.password"/>
    <@spring.showErrors "error"/>
    <br>
    <@spring.formInput "userDTO.country"/>
    <@spring.showErrors "error"/>
    <br>
    <@spring.formInput "userDTO.gender"/>
    <@spring.showErrors "error"/>
    <br>
    <input type="hidden" value="${_csrf_token}" name="_csrf_token">
    <button type="submit"> Отправить</button>
</form>
<#if repeatableFields??>
    <p style=".error">${repeatableFields}</p>
</#if>
<br>
</body>
</html>