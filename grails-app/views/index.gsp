<!doctype html>
<%@ page import="com.ufscar.Adaptativo" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Voadora</title>

    <asset:link rel="icon" href="globe-icon.png" type="image/x-png" />

</head>
<body>

<div class="image">
    <img class="img-responsive" src="http://crisscrosstvl.com/wp-content/uploads/2016/05/sunset-plane.png" alt="Chania">
    <div id ="headerText">
       oi ${session.getAttribute("id")}
    </div>


    <g:form  controller="user" action="attInit" >
    <div id="primeiraPergunta">
            <input type="number" value=0 min=0 id="dinheiro" name="dinheiro">
    </div>

    <div id="segundaPergunta">
        <select id="estado" name="estado">
            <option value="todos">Todos</option>
            <option value="sp">São Paulo</option>
            <option value="rj">Rio de Janeiro</option>
        </select>
    </div>
        <a href="#section1">
            <div id="buscar">
                <input class="btn gigante btn-success " type="submit" value="Buscar" />
            </div>
        </a>
    </g:form>



</div>

<div id="content" role="main">
        <section class="row colset-2-its">
            <h1>Welcome to Grails</h1>

            <p>
                Congratulations, you have successfully started your first Grails application! At the moment
                this is the default page, feel free to modify it to either redirect to a controller or display
                whatever content you may choose. Below is a list of controllers that are currently deployed in
                this application, click on each to execute its default action:
            </p>

            <div id="controllers" role="navigation">
                <h2>Available Controllers:</h2>
                <ul>
                    <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                        <li class="controller">
                            <g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link>
                        </li>
                    </g:each>
                </ul>
            </div>

            <h1 id="section1">Section 1</h1>
            <g:include controller="locais" action="listar" />


        </section>
    </div>


<asset:javascript src="application.js"/>
<asset:stylesheet src="list.css"/>
</body>
</html>
