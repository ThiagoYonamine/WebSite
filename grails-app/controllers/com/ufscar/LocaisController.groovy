package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LocaisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*Adaptação Lista geral*/
    def attLike(){
        def au = session.getAttribute("id")
        def u = User.get(au)

        def categoria = params.categoria
        if(categoria=="natureza"){
            u.natureza = u.natureza*1.4
            u.cidade = u.cidade/1.1
            u.outros = u.outros/1.05
        }
        if(categoria=="cidade"){
            u.cidade = u.cidade*1.4
            u.natureza = u.natureza/1.1
            u.outros = u.outros/1.05
        }
        if(categoria=="outros"){
            u.outros = u.outros*1.4
            u.natureza = u.natureza/1.05
            u.cidade = u.cidade/1.05
        }

        def newLocal = Locais.get(params.id)
        u.addToLocais(newLocal)
        u.save(flush: true)

        redirect(uri: "/index#section1")
    }

    /*Adaptação Lista Favoritos*/
    def attUnlike(){

        def au = session.getAttribute("id")
        def u = User.get(au)

        def categoria = params.categoria
        if(categoria=="natureza"){
            u.natureza = u.natureza/1.4
        }
        if(categoria=="cidade"){
            u.cidade = u.cidade/1.4
        }
        if(categoria=="outros"){
            u.outros = u.outros/1.4
        }

        def newLocal = Locais.get(params.id)
        u.removeFromLocais(newLocal)
        u.save(flush:true)
        redirect(uri:"/usrPro#section1")
    }

    def listar() {
        def au = session.getAttribute("id")
        def u = User.get(au)
        def pontosTuristicos = []
        def cats = [natureza: u.natureza, cidade: u.cidade, outros: u.outros]
        for (categorias in cats) {
                def results = Locais.findAllByCategoria(categorias.key)
                Collections.shuffle(results);
                def cont = 0.9
                for (item in results) {
                    if (cont >= categorias.value)
                        break
                    if (!(u.locais.contains(item))) {
                        pontosTuristicos.add(item)
                        cont++
                    }
                }
        }
        Collections.shuffle(pontosTuristicos)
        render(view: "modeloLocal", model: [pontosTuristicos: pontosTuristicos])
    }

    def listarFavoritos() {
        def au = session.getAttribute("id")
        def u = User.get(au)
        def pontosTuristicos = []
        for(item2 in u.locais){
            pontosTuristicos.add(item2)
        }

        render(view: "modeloLocalFavorito", model: [pontosTuristicos: pontosTuristicos])
    }


    //Padrão do projeto
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Locais.list(params), model:[locaisCount: Locais.count()]
    }

    def show(Locais locais) {
        respond locais
    }

    def create() {
        respond new Locais(params)
    }

    @Transactional
    def save(Locais locais) {
        if (locais == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (locais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond locais.errors, view:'create'
            return
        }

        locais.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'locais.label', default: 'Locais'), locais.id])
                redirect locais
            }
            '*' { respond locais, [status: CREATED] }
        }
    }

    def edit(Locais locais) {
        respond locais
    }

    @Transactional
    def update(Locais locais) {
        if (locais == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (locais.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond locais.errors, view:'edit'
            return
        }

        locais.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'locais.label', default: 'Locais'), locais.id])
                redirect locais
            }
            '*'{ respond locais, [status: OK] }
        }
    }

    @Transactional
    def delete(Locais locais) {

        if (locais == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        locais.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'locais.label', default: 'Locais'), locais.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'locais.label', default: 'Locais'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
