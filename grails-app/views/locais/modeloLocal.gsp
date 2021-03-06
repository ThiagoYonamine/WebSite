
<!DOCTYPE html>

<html>
    <head>
        <meta name="layout" content="estrutura" />
        <g:set var="entityName" value="${message(code: 'locais.label', default: 'Locais')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
        <asset:stylesheet src="list.css"/>
    </head>
    <body>
        <div class="row">
            <g:each in="${pontosTuristicos}" var="pt">
                <div class="col-md-4">
                    <h2 align = center>${pt.nome}</h2>
                    <div class="polaroid">
                    <div class="container2">
                        <a href="/assets/pontos/${pt.url}" target="_blank">
                            <img src="/assets/pontos/${pt.url}" alt="Norway" style="width:100%" class="image1">
                                <div class="overlay">
                                    <div class="textList">${pt.descricao}</div>
                                </div>
                        </a>
                    </div>
                        <div class="container1">
                            <p>${"Local: "+pt.cidade}
                            <br>${"Valor: "+pt.valor}</p>
                        </div>
                    </div>
                    <div id="vote">
                        <!-- Monitoramento do Like-->
                        <g:form  action="attLike" params="[categoria: pt.categoria, id: pt.id]">
                            <input class="btn btn-success " type="submit" value="Like" />
                        </g:form>
                    </div>
                </div>
            </g:each>
        </div>
    </body>
</html>
