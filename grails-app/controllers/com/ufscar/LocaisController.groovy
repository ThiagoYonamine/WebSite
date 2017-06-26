package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LocaisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def attLike(){
        def au = session.getAttribute("usr")
        def u = User.get(au.id)
        def categoria = params.categoria
        if(categoria=="parques"){
            u.parques = u.parques+1
        }
        if(categoria=="museus"){
            u.museus = u.museus+1
        }

        def newLocal = Locais.get(params.id)
        u.addToLocais(newLocal)
        u.save(flush: true)
    }

    def listar() {
        def hobbies = Locais.list() //Todos os pontos turísticos
        def au = session.getAttribute("id") // u = usuário logado (objeto)
        def u = User.get(au)
        def pontosTuristicos = []
        def cats = [parques: u.parques, museus: u.museus]
        for (categorias in cats) {
                def results = Locais.findAllByCategoria(categorias.key)
                def cont = 0
                for (item in results) {
                    if (cont >= categorias.value)
                        break

                    if (!(u.locais.contains(item))) {
                        pontosTuristicos.add(item)
                        cont++
                    }
                }
        }
        render(view: "modeloLocal", model: [pontosTuristicos: pontosTuristicos])
    }

    def listarFavoritos() {
        def au = session.getAttribute("id") // u = usuário logado (objeto)
        def u = User.get(au)
        def pontosTuristicos = []
        for(item2 in u.locais){
            pontosTuristicos.add(item2)
        }

        render(view: "modeloLocalFavorito", model: [pontosTuristicos: pontosTuristicos])
    }

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
