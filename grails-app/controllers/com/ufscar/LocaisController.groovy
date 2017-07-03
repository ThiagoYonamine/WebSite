package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LocaisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*Adaptação Lista geral*/
    def attLike(){
        def au = session.getAttribute("usr")
        def u = User.get(au.id)

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

        def au = session.getAttribute("usr")
        def u = User.get(au.id)

        def categoria = params.categoria
        if(categoria=="natureza"){
            u.natureza = u.natureza/1.4
            u.cidade = u.cidade*1.1
            u.outros = u.outros*1.05
        }
        if(categoria=="cidade"){
            u.cidade = u.cidade/1.4
            u.natureza = u.natureza*1.1
            u.outros = u.outros*1.05
        }
        if(categoria=="outros"){
            u.outros = u.outros/1.4
            u.natureza = u.natureza*1.05
            u.cidade = u.cidade*1.05
        }

        def newLocal = Locais.get(params.id)
        u.removeFromLocais(newLocal)
        u.save(flush:true)
        redirect(uri:"/usrPro#section1")
    }

    /*Lista radomicamente de acordo com os dados do forms*/
    def listar() {
        def au = session.getAttribute("usr")
        def u = User.get(au.id)
        def pontosTuristicos = []
        def cats = [natureza: u.natureza, cidade: u.cidade, outros: u.outros]
        for (categorias in cats) {
                def results = Locais.findAllByCategoria(categorias.key)
                Collections.shuffle(results);
                def cont = 0.9 //Exibe 2 de cada categoria
                for (item in results) {
                    if (cont >= categorias.value)
                        break
                    /*"Filtra" por orçamento e estado*/
                    if (!(u.locais.contains(item)) && ((u.estado==item.estado)||(u.estado=="todos")) && (u.dinheiro >= item.valor)) {
                        pontosTuristicos.add(item)
                        cont++
                    }
                }
        }
        Collections.shuffle(pontosTuristicos)
        render(view: "modeloLocal", model: [pontosTuristicos: pontosTuristicos])
    }

    def listarFavoritos() {
        def au = session.getAttribute("usr")
        def u = User.get(au.id)
        def pontosTuristicos = []
        for(item2 in u.locais){
            pontosTuristicos.add(item2)
        }
        render(view: "modeloLocalFavorito", model: [pontosTuristicos: pontosTuristicos])
    }

    def criar() {
        //ja criado
       /* def p1 = new Locais(valor: 100,
                cidade: "Olimpia", estado: "sp",
                nome: "Thermas dos Laranjais",
                url: "img1.jpg", descricao: "Um dos maiores e mais visitados parques aquáticos termais do Brasil, região noroeste Paulista a 400 km de São Paulo e apenas 30 minutos do aeroporto de S.J.Rio Preto.",
                categoria: "outros")
        p1.save(flush: true)*/

        def p2 = new Locais(valor: 0,
                cidade: "Lindóia", estado: "sp",
                nome: "Balneário - Águas de Lindóia",url: "img2.jpg",
                descricao: "Lazer, Termalismo, Crenoterapia, Saúde e Beleza reunidos em só lugar. Oferece mais de 30 serviços entre banhos de imersão, massagens, piscinas de água mineral, ducha escocesa, banho de argila.",
                categoria: "outros")
        p2.save(flush: true)

        def p3 = new Locais(valor: 0,
                cidade: "Águas de São Pedro", estado: "sp",
                nome: "Thermas Water Park",url: "img3.jpg",
                descricao: "Um dos maiores complexos turísticos de lazer da América Latina.Oferece 13 piscinas com total infraestrutura e decorações temáticas, exposições permanentes, diversos pontos de alimentação, estacionamento para até 2 mil veículos e muita segurança.",
                categoria: "outros")
        p3.save(flush: true)

        def p4 = new Locais(valor: 0,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Parque da Floresta Encantada",url: "img4.jpg",
                descricao: "Com seus 12.000 metros quadrados de área o Parque da Floresta Encantada é uma opção segura e divertida para seu filhos.Aberto diariamente das 9:30h às 17:00h, aos sábados, domingos e feriados, o parque conta com crianças caracterizadas proporcionando maior brilho e beleza ao local.",
                categoria: "outros")
        p4.save(flush: true)

        def p5 = new Locais(valor: 0,
                cidade: "Itupeva", estado: "sp",
                nome: "Wet'n Wild",url: "img5.jpg",
                descricao: "O parque de diversão (e também aquático) apresenta diversos brinquedos,profissionais atenciosos ,simpáticos em um local limpo e sustentável.",
                categoria: "outros")
        p5.save(flush: true)

        def p6 = new Locais(valor: 78,
                cidade: "Cesário Lange", estado: "sp",
                nome: "Castelo Park Aquático",url: "img6.jpg",
                descricao: "O Castelo Park Aquático tem uma infra-estrutura completa: equipe de monitores e guarda vidas treinados, praça de alimentação, lanchonetes, quiosques, restaurante self service, sorveteria, vestiários amplos e modernos, fraldário equipado, guarda volumes, ambulatório, estacionamento gratuito e diversas atrações para a diversão com a família e os amigos!",
                categoria: "outros")
        p6.save(flush: true)


        def p7 = new Locais(valor: 0,
                cidade: "Barretos", estado: "sp",
                nome: "Barretos Country Acquapark",url: "img7.jpg",
                descricao: "Parque aquático de águas termais. Ambiente muito agradável,com mini zoo, pista de arvorismo e diversas atrações.",
                categoria: "outros")
        p7.save(flush: true)


        def p8 = new Locais(valor: 120,
                cidade: "Suzano", estado: "sp",
                nome: "Magic City",url: "img8.jpg",
                descricao: "Um bom parque aquático, pertinho pra quem mora em São Paulo, com boas piscinas, incluindo piscinas aquecidas e de ondas que fazem a diversão da galera.",
                categoria: "outros")
        p8.save(flush: true)
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
