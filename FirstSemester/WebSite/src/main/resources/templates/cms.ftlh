<!doctype html>
<html lang="en">
<#import "spring.ftl" as spring/>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://cdn.tiny.cloud/1/extof7ii67lyp7eaaicvst9utopg9dqmy9lpsqkairw74n6m/tinymce/5/tinymce.min.js"
            referrerpolicy="origin"></script>
    <script>
        tinymce.init({
            selector: '#mytextarea',
            plugins: 'media paste image help emoticons directionality',
            statusbar: true,
            emoticons_database: 'emojis',
            image_advtab: true,
            height: 600
        });
        tinymce.activeEditor.execCommand('mceMedia');
        tinymce.activeEditor.execCommand('mceTogglePlainTextPaste');
        tinymce.activeEditor.execCommand('mceImage');
    </script>

    <title>Create your own page</title>
</head>
<body>
<form method="post">
    <div class="name">
        <label for="name">Enter a name of page</label>
        <input type="text" placeholder="Name of page" name="name" id="name">
    </div>
    <textarea id="mytextarea" name="content"></textarea>
    <button type="submit">Сохранить страницу</button>
</form>
</body>
</html>