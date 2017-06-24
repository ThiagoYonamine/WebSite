<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="estrutura" />
        <g:set var="entityName" value="${message(code: 'locais.label', default: 'Locais')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>
    My name is ${name} and my hobbies are:

    <div class="row">
        <g:each in="${hobbies}" var="hobby">

              <div class="col-md-4">
              <h2 align = center>${hobby.nome}</h2>
                <div class="thumbnail" >
                  <a href="/assets/img${hobby.url}.jpg" target="_blank">
                      <div class="img" style="background-image:url('/assets/img${hobby.url}.jpg');" ></div>
                    <div class="caption">
                    <g:form controller="user" action="like">

                        <g:actionSubmit value="Like" />
                    </g:form>
                      <p>${hobby.descricao}</p>
                    </div>
                  </a>
                </div>
            </div>
        </g:each>
    </div>


    </body>
</html>
