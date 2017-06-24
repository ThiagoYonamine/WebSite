<!doctype html>
<html>
<head>

    <title>Voadora</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

</head>
<body>

<div class="image">

    <div id="primeiraPergunta">
    <g:form controller="user" action="login">
        <input type="number" min="0" id="dinheiro" name="dinheiro">
        <input type="text" id="nome" name="nome">
        <a href="index.gsp" ><g:actionSubmit value="Login" /> </a>
    </g:form>
    </div>



<h1 id="section1">Section 1</h1>
<asset:javascript src="application.js"/>
</body>
</html>
