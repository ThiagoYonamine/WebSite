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
    <img class="img-responsive" src="http://crisscrosstvl.com/wp-content/uploads/2016/05/sunset-plane.png" alt="Chania">
    <div id ="headerText">

       Olá, ${session.getAttribute("usr").nome}.Vamos pra onde?

    </div>


    <g:form  controller="user" action="attInit" >
    <div id="primeiraPergunta">
            <input type="number" placeholder="Orçamento para lazer" min=0 id="dinheiro" name="dinheiro">
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
            <h1>Viajaí</h1>

            <p>
                Curta os locais onde você gostaria de ir e o site se adapata em tempo real oferencendo as melhores sugestões
                para seu perfil!
            </p>


            <h1 id="section1"></h1>
            <g:include controller="locais" action="listar" />


        </section>
    </div>


<asset:javascript src="application.js"/>
<asset:stylesheet src="list.css"/>
</body>
</html>
