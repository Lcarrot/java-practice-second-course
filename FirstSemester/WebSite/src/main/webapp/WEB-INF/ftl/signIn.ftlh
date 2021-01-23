<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<form method="post" action="/signIn">
    <h3></h3>
    <label for="login">login:</label><br>
    <input type="text" id="login" name="login"><br>
    <label for="password">password:</label><br>
    <input type="text" id="password" name="password">
    <input type="hidden" value="${_csrf_token}">
    <button type="submit">Ok </button>
</form>
</body>
</html>