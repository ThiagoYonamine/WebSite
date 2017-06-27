<!doctype html>
<%@ page import="com.ufscar.Adaptativo" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Perfil do usuário</title>

    <asset:link rel="icon" href="globe-icon.png" type="image/x-png" />

</head>
<body>

<div class="image">
    <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="" src="/assets/img2.jpg">
        </div>
        <div class="useravatar">
            <img alt="" src="/assets/img2.jpg">
        </div>
        <div class="card-info"> <span class="card-title">${session.getAttribute("usr").nome}</span>
        </div>
    </div>

    <div id="content" role="main">
        <section class="row colset-2-its">

            <h1 id="section1">Pontos Turísticos Favoritos</h1>
            <g:include controller="locais" action="listarFavoritos" />

        </section>
    </div>

</div>


<asset:javascript src="usrPro.js"/>
<asset:javascript src="application.js"/>
<asset:stylesheet src="usrPro.css"/>
</body>
</html>
