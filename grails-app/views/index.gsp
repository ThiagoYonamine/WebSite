<!doctype html>
<%@ page import="com.ufscar.Adaptativo" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Viajaí</title>

    <asset:link rel="icon" href="globe-icon.png" type="image/x-png" />

</head>
<body>

<div class="image">
    <img class="img-responsive brightness" src="http://crisscrosstvl.com/wp-content/uploads/2016/05/sunset-plane.png" alt="Chania">
    <div id ="headerText">
       Olá, ${session.getAttribute("usr").nome}. Para onde vamos?

    </div>

    <g:form  controller="user" action="attInit" >
    <div id="primeiraPergunta">
        <h1>${flash.message}</h1>
        <input type="number" value="${session.getAttribute("usr").dinheiro}" id="dinheiro" name="dinheiro">
    </div>

    <div id="segundaPergunta">
        <select id="estado" name="estado" class="second">
            <option value="todos">Todos</option>
            <option value="sp">São Paulo</option>
            <option value="rj">Rio de Janeiro</option>
        </select>
    </div>

        <div id="buscar">
            <input href="#section1" class="btn gigante btn-success " type="submit" value="Buscar" />
        </div>
    </g:form>
</div>

<div id="content" role="main">
        <section class="row colset-2-its">
            <h3>Viajaí</h3>

            <h2><p>
                Curta os pontos turísticos que você gostaria de visitar e nós fazemos
                sugestões em tempo real de acordo com o seu perfil!
            </p></h2>


            <h3 id="section1"></h3>
            <!-- Monitora (aguarda "Like") do usuário e lista os pontos turísticos -->
            <g:include controller="locais" action="listar" />
            <br>
            <br>
        </section>
    </div>


<asset:javascript src="application.js"/>
<asset:stylesheet src="list.css"/>
</body>
</html>
