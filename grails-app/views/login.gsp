<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title>Viajaí</title>


    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css'>
    <asset:link rel="icon" href="globe-icon.png" type="image/x-png" />
    <asset:stylesheet src="style.css"/>


</head>

<body>
<div class="login-form">
    <h1>Viajaí</h1>
    <g:form controller="user" action="login">

    <div class="form-group ">

        <input type="text" class="form-control" placeholder="Username " id="nome" name="nome">
        <i class="fa fa-user"></i>
    </div>
    <div class="form-group log-status">
        <input type="password" class="form-control" placeholder="Password" id="senha" name="senha">
        <i class="fa fa-lock"></i>
    </div>

        <g:actionSubmit type="button" class="log-btn" value="Login" />
    </g:form>
    <h2>${flash.message}</h2>
</div>
<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>


</body>
</html>
