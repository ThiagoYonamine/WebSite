<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Voadora</title>

    <asset:link rel="icon" href="favicon.ico" type="image/x-ico" />

</head>
<body>

<div class="image">
    <img class="img-responsive" src="http://crisscrosstvl.com/wp-content/uploads/2016/05/sunset-plane.png" alt="Chania">
    <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
    <div id ="headerText">
        ${session.getAttribute("id")}
    </div>

    <div id="primeiraPergunta">
        <g:form resource="${session.getAttribute("usr")}" method="PUT">
            <g:hiddenField name="version" value="${session.getAttribute("usr")?.version}" />
            <input type="number" min="0" id="dinheiro" name="dinheiro">
            <fieldset class="buttons">
                <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </fieldset>
        </g:form>


    </div>

    <div id="segundaPergunta">
    <form>
        <select id="country" name="country">
            <option value="au">Estado</option>
            <option value="ca">Dinheiro</option>
            <option value="usa">Mes</option>
        </select>
    </form>
    </div>

    <div id="terceiraPergunta">
        <form>
            <input type="month" id="data" name="fname">
        </form>
    </div>

    <a href="#section1">
    <div id="buscar">
        <input type="submit" class="btn gigante btn-success" value="Buscar" href="#section1">
    </div>
    </a>

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
        </section>
    </div>

<h1 id="section1">Section 1</h1>
<asset:javascript src="application.js"/>
</body>
</html>
