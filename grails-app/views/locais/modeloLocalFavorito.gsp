<%@ page import="com.ufscar.AdaptativoController; com.ufscar.Adaptativo" %>
<!DOCTYPE html>

<html>
<head>
    <meta name="layout" content="estrutura" />
    <g:set var="entityName" value="${message(code: 'locais.label', default: 'Locais')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>

</head>
<body>

<div class="row">
    <g:each in="${pontosTuristicos}" var="pt">


        <div class="col-md-4">
            <h2 align = center>${pt.nome}</h2>
            <div class="polaroid">
                <div class="container2">
                    <a href="/assets/img${pt.url}.jpg" target="_blank">
                        <img src="/assets/img${pt.url}.jpg" alt="Norway" style="width:100%" class="image1">
                        <div class="overlay">
                            <div class="textList">${pt.descricao}</div>
                        </div>
                    </a>

                </div>
                <div class="container1">
                    <p>${pt.descricao}</p>
                </div>
            </div>
            <div id="vote">
                <g:form  action="attUnlike" params="[categoria: pt.categoria, id: pt.id]">
                    <input class="btn btn-success " type="submit" value="Unlike" />
                </g:form>
            </div>
        </div>
    </g:each>
</div>


</body>
</html>
