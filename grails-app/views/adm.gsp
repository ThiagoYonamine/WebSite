<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Admin</title>

</head>
<body>
<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div id="page-body" role="main">
    <br><br><br><br><br><br>
    <h1>Banco de Dados</h1>
    <p>LocaisController: Inserir os locais no Banco de Dados
    <br>UserController: Inserir manualmente usuários no Banco de Dados</p>

    <div id="controller-list" role="navigation">
        <h1>Controllers:</h1>
       <h3> <ul>
            <g:each var="c" in="${grailsApplication.controllerClasses.sort { it.fullName } }">
                <li class="controller"><g:link controller="${c.logicalPropertyName}">${c.fullName}</g:link></li>
            </g:each>
        </ul></h3>

        <g:form  controller="locais" action="criar" >

                <input href="#section1" class="btn-success " type="submit" value="Criar Locais" />

        </g:form>
    </div>
</div>
</body>
</html>
