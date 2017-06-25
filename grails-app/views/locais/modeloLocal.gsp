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
                <div class="thumbnail">
                    <a href="/assets/img${pt.url}.jpg" target="_blank">
                        <div class="img" style="background-image:url('/assets/img${pt.url}.jpg');" ></div>

                        <div class="caption">
                            <p>${pt.descricao}</p>
                        </div>
                    </a>

                </div>
                  <div id="vote">
                <g:form resource="${session.getAttribute("usr")}" method="PUT" >
                    <g:hiddenField name="version" value="${session.getAttribute("usr")?.version}" />
                        <div id="hide">
                          <input type="number"  id="${pt.categoria}" name="${pt.categoria}" value= <g:like categoria="${pt.categoria}" user="${session.getAttribute("usr")}"/> >
                        </div>
                      <input  id="btn1" class="btn btn-success " type="submit" value="Like" "/>
                    </g:form>
                  </div>

              </div>
        </g:each>
    </div>


    </body>
</html>
