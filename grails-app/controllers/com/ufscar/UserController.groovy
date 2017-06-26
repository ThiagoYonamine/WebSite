package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def login={

        def b = User.findByNome(params.nome)
        if(b) {
            println b.senha
            println params.senha
            if(params.senha == b.senha){
                session.setAttribute("id", b.id)
                session.setAttribute("usr",b)
                flash.message="Sucesso"


            }
            else {
                flash.message="Senha invalida"
                redirect(uri: "/")
            }
        }
        else{
            Locais l1 = new Locais()
            l1.categoria = "natureza"
            l1.url = "1"
            l1.nome = "a"
            l1.descricao ="a"
            Locais l2 = new Locais()
            l2.categoria = "cidade"
            l2.url = "2"
            l2.nome = "b"
            l2.descricao ="b"
            Locais l3 = new Locais()
            l3.categoria = "outros"
            l3.url = "3"
            l3.nome = "c"
            l3.descricao ="c"

            l1.save(flush: true)
            l2.save(flush: true)
            l3.save(flush: true)

            def a = new User()
            a.nome = params.nome
            a.senha = params.senha
            a.save(flush: true)
            def c = User.findByNome(params.nome)
            session.setAttribute("usr",c)
            session.setAttribute("id", c.id)
            redirect(uri: "/index")

        }
    }
    def attInit() {
        def au = session.getAttribute("id")
        def u = User.get(au)
        u.dinheiro = params.int('dinheiro')
        u.estado = params.estado
        u.save(flush: true)
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond User.list(params), model:[userCount: User.count()]
    }

    def show(User user) {
        respond user
    }

    def create() {
        respond new User(params)
    }

    @Transactional
    def save(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'create'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect(uri: "/index")
            }
            '*' { respond user, [status: CREATED] }
        }
    }

    def arrumaLista(){
        redirect (uri: "/index#section1")
    }
    def edit(User user) {
        respond user
    }

    @Transactional
    def update(User user) {
        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (user.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond user.errors, view:'edit'
            return
        }

        user.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect(controller: "user", action: "arrumaLista")
            }
            '*'{ respond user, [status: OK] }
        }
    }

    @Transactional
    def delete(User user) {

        if (user == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        user.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), user.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
