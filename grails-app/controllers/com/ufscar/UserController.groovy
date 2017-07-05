package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class UserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*Se o usuário não existe, cria um novo. User: adm e Pass: adm redireciona para controllers*/
    def login= {
        def b = User.findByNome(params.nome)
            if (b) {
                if (params.senha == b.senha) {
                    session.setAttribute("usr", b)
                    if (b.senha == "adm" && b.nome == "adm"){
                        redirect(uri: "/adm")
                    } else {
                        redirect(uri: "/index")
                    }
                } else {
                    flash.message = "Senha inválida"
                    redirect(uri: "/")

                }
            } else {
                def a = new User()
                a.nome = params.nome
                a.senha = params.senha
                a.save(flush: true)
                def c = User.findByNome(params.nome)
                session.setAttribute("usr", c)

                redirect(uri: "/index")
            }
        }

    /*Coleta dados do forms*/
    def attInit() {
        def au = session.getAttribute("usr")
        def u = User.get(au.id)
        if (params.dinheiro == ""){
            flash.message = "Insira um valor"

            redirect(uri: "/index")
        } else {
            u.dinheiro = params.int('dinheiro')
            u.estado = params.estado
            u.save(flush: true)
            session.setAttribute("usr",u)

            redirect(uri: "/index#section1")
        }
    }

    //Padrão do projeto
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
        session.setAttribute("usr",user)
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
