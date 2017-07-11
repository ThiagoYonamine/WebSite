package com.ufscar

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class LocaisController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    /*Adaptação Lista geral*/
    def attLike(){
        //Análise
        def au = session.getAttribute("usr")
        def u = User.get(au.id)

        def categoria = params.categoria
        if(categoria=="natureza"){
            //Decisão
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

        //Ação
        def newLocal = Locais.get(params.id)
        u.addToLocais(newLocal)
        u.save(flush: true)

        redirect(uri: "/index#section1")
    }

    /*Adaptação Lista Favoritos*/
    def attDislike(){

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


    //Função criar() para facilitar a insersão de locais no BD pelo ADM, movido para o init
    def criar() {


        def p2 = new Locais(valor: 0,
                cidade: "Lindóia", estado: "sp",
                nome: "Balneário - Águas de Lindóia",url: "img2.jpg",
                descricao: "Oferece mais de 30 serviços entre banhos de imersão, massagens, piscinas de água mineral, ducha escocesa, banho de argila.",
                categoria: "outros")
        p2.save(flush: true)

        def p3 = new Locais(valor: 0,
                cidade: "Águas de São Pedro", estado: "sp",
                nome: "Thermas Water Park",url: "img3.jpg",
                descricao: "Um dos maiores complexos turísticos de lazer da América Latina.Oferece 13 piscinas com total infraestrutura e decorações temáticas, exposições permanentes, diversos pontos de alimentação e  estacionamento.",
                categoria: "outros")
        p3.save(flush: true)

        def p4 = new Locais(valor: 0,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Parque da Floresta Encantada",url: "img4.jpg",
                descricao: "Parque da Floresta Encantada é uma opção segura e divertida para seu filhos.Aberto diariamente das 9:30h às 17:00h,  o parque conta com crianças caracterizadas proporcionando maior brilho e beleza ao local.",
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
                descricao: "O Castelo Park Aquático tem uma infra-estrutura completa e  diversas atrações para a diversão com a família e os amigos!",
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

        def p9 = new Locais(valor: 0,
                cidade: "Vinhedo", estado: "sp",
                nome: "Hopi Hari",url: "img9.jpg",
                descricao: "Eleito nove vezes o melhor parque do Brasil, Hopi Hari está localizado a 15 minutos de Campinas e meia hora de São Paulo. Contato: ",
                categoria: "outros")
        p9.save(flush: true)


        def p10 = new Locais(valor: 69,
                cidade: "São Paulo", estado: "sp",
                nome: "KidZania",url: "img10.jpg",
                descricao: "A KidZania oferece às crianças e seus pais um ambiente seguro, único, realista e educacional, que permite aos meninos e meninas, com idades entre 4 e 14 anos, fazer o que é da natureza deles: brincar. ",
                categoria: "outros")
        p10.save(flush: true)


        def p11 = new Locais(valor: 0,
                cidade: "Praia Grande", estado: "sp",
                nome: "Praia Grande",url: "img11.jpg",
                descricao: "A cidade de Praia Grande tem uma das mais movimentadas e belas praias do Brasil.",
                categoria: "outros")
        p11.save(flush: true)


        def p12 = new Locais(valor: 0,
                cidade: "Guarujá", estado: "sp",
                nome: "Aulas de Surfe na Praia de Pernanbuco",url: "img12.jpg",
                descricao: "A escola Surfistas para sempre tem como objetivo e compromisso ensinar aos seus alunos os fundamentos básicos do Surf, Long board, Fun board, Short board, Body board, Stand up Paddle ou Stand up surf, e Body surf ou Jacaré.",
                categoria: "outros")
        p12.save(flush: true)


        def p13 = new Locais(valor: 0,
                cidade: "São Sebastião", estado: "sp",
                nome: "Praia de Camburi",url: "img13.jpg",
                descricao: "São mais de 30 praias que fazem de São Sebastião uma das regiões mais encantadoras da costa brasileira. O perfeito encontro da Mata Atlântica com as areias brancas e o azul do mar. ",
                categoria: "outros")
        p13.save(flush: true)


        def p14 = new Locais(valor: 0,
                cidade: "Bertioga", estado: "sp",
                nome: "Praia Boracéia",url: "img14.jpg",
                descricao: "Boracéia é um cartão de visitas, para os amantes da natureza, local excelente para pratica de esportes náuticos.  ",
                categoria: "outros")
        p14.save(flush: true)


        def p15 = new Locais(valor: 0,
                cidade: "Ubatuba", estado: "sp",
                nome: "Praia do Lázaro",url: "img15.jpg",
                descricao: "A Praia do Lázaro é muito procurada por famílias, tem areias finas e firmes, mar calmo e cristalino com vista para o famoso Pico do Corcovado. ",
                categoria: "outros")
        p15.save(flush: true)

        def p16 = new Locais(valor: 0,
                cidade: "Caraguatatuba", estado: "sp",
                nome: "Praia Massaguacu",url: "img16.jpg",
                descricao: "Esta praia possui uma grande extensão e um mar bem agitado, sendo recomendada para a prática do surf e para a pesca de arremesso. ",
                categoria: "outros")
        p16.save(flush: true)

        def p17 = new Locais(valor: 0,
                cidade: "Peruíbe", estado: "sp",
                nome: "Praia Barra do Una",url: "img17.jpg",
                descricao: "A Praia Barra do Una tem águas claras e limpas, uma larga faixa de areia escura e dura, vegetação de restinga e cercada por morros ao fundo. ",
                categoria: "outros")
        p17.save(flush: true)

        def p18 = new Locais(valor: 0,
                cidade: "Santos", estado: "sp",
                nome: "Praia do Gonzaga",url: "img18.jpg",
                descricao: "Talvez a praia mais badalada da cidade por estar em frente a uma das principais avenidas da cidade, próxima aos hotéis,shopping e atrações do bairro.",
                categoria: "outros")
        p18.save(flush: true)

        def p19 = new Locais(valor: 0,
                cidade: "São Vicente", estado: "sp",
                nome: "Praia de Itararé",url: "img19.jpg",
                descricao: "Talvez a praia mais badalada da cidade por estar em frente a uma das principais avenidas da cidade, próxima aos hotéis,shopping e atrações do bairro.",
                categoria: "outros")
        p19.save(flush: true)

        def p20 = new Locais(valor: 0,
                cidade: "Ubatuba", estado: "sp",
                nome: "Praia da enseada",url: "img20.jpg",
                descricao: "Ubatuba possui mais de 100 praias distribuídas pelo seu litoral. Dentre ela, as mais conhecidas são: Maranduba, Itamambuca, Lázaro, Vermelha, Grande, Enseada, Perequê e Saco da Ribeira.",
                categoria: "outros")
        p20.save(flush: true)



        def p21 = new Locais(valor: 149,
                cidade: "Brotas", estado: "sp",
                nome: "Canionismo Jacaré",url: "img21.jpg",
                descricao: "O Canionismo Jacaré traz a oportunidade de desbravar um Canion. A atividade basicamente consiste na prática do rapel em três cachoeiras interligadas por trilhas. ",
                categoria: "natureza")
        p21.save(flush: true)


        def p22 = new Locais(valor: 0,
                cidade: "Brotas", estado: "sp",
                nome: "Recanto das Cachoeiras",url: "img22.jpg",
                descricao: "Um local privilegiado de natureza deslumbrante e preservada. Oferece atividades como o Cascading, Rapel, Arvorismo, Trilha, Banhos de Cachoeira e Cavalgada.",
                categoria: "natureza")
        p22.save(flush: true)

        def p23 = new Locais(valor: 60,
                cidade: "Brotas", estado: "sp",
                nome: "Eco Parque Cassorova",url: "img23.jpg",
                descricao: "As cachoeiras Cassorova e dos Quatis são um dos cartões postais de Brotas escondidas depois de uma trilha no meio da mata preservada e na grandeza da paisagem do vale Cassorova. ",
                categoria: "natureza")
        p23.save(flush: true)


        def p24 = new Locais(valor: 0,
                cidade: "Brotas", estado: "sp",
                nome: "Cachoeira Três Quedas",url: "img24.jpg",
                descricao: "O parque tem três cachoeiras: da Nascente, Figueira e Andorinha. É permitido tomar banho de cachoeira. ",
                categoria: "natureza")
        p24.save(flush: true)


        def p25 = new Locais(valor: 0,
                cidade: "Cunha", estado: "sp",
                nome: "Parque Estadual da Serra do Mar - Núcleo Cunha",url: "img25.jpg",
                descricao: "A visita as cachoeiras é realizada com acompanhamento de um  guia turístico do local que apresenta uma série de curiosidades sobre a região, as cachoeiras e a Mata Atlântica.",
                categoria: "natureza")
        p25.save(flush: true)


        def p26 = new Locais(valor: 0,
                cidade: "Ilhabela", estado: "sp",
                nome: "Cachoeira do Gato",url: "img26.jpg",
                descricao: "Com 40 metros de altura, a Cachoeira do Gato é a estrela da trilha que leva o seu nome. ",
                categoria: "natureza")
        p26.save(flush: true)



        def p27 = new Locais(valor: 0,
                cidade: "Ilhabela", estado: "sp",
                nome: "Cachoeira da Toca",url: "img27.jpg",
                descricao: "Um dos pontos turísticos mais tradicionais de Ilhabela.  ",
                categoria: "natureza")
        p27.save(flush: true)


        def p28 = new Locais(valor: 20,
                cidade: "Itirapina", estado: "sp",
                nome: "Cachoeiras Saltão,  Monjolinho e Ferradura",url: "img28.jpg",
                descricao: "Os amantes da natureza encontram na Cachoeira Saltão um dos cenários mais belos que a natureza local oferece. ",
                categoria: "natureza")
        p28.save(flush: true)


        def p29 = new Locais(valor: 0,
                cidade: "Eldorado", estado: "sp",
                nome: "Cachoeira Queda do Meu Deus",url: "img29.jpg",
                descricao: "A Cachoeira tem uma imensa queda de 53 metros e sua origem está numa nascente em área fechada,  que passa por piscinas naturais com água cristalina e três quedas menores. ",
                categoria: "natureza")
        p29.save(flush: true)

        def p30 = new Locais(valor: 5,
                cidade: "Santo Antônio do Pinhal", estado: "sp",
                nome: "Cachoeira do Lageado",url: "img30.jpg",
                descricao: "Cercada por muito verde, ar puro e a tranqüilidade do interior, a Cachoeira do Lageado é a mais visitada pelos turistas, por oferecer aos visitantes infra estrutura básica para passar bem o dia.",
                categoria: "natureza")
        p30.save(flush: true)


        def p31 = new Locais(valor: 0,
                cidade: "Itararé", estado: "sp",
                nome: "Cachoeira do Corisco",url: "img31.jpg",
                descricao: "A Cachoeira do Corisco é uma das mais belas dessa lista. ",
                categoria: "natureza")
        p31.save(flush: true)


        def p32 = new Locais(valor: 110,
                cidade: "Juquitiba", estado: "sp",
                nome: "Rafting em Juquitiba",url: "img32.jpg",
                descricao: "Rafting no Juquiá é reconhecido como o mais tradicional percurso para rafting no Brasil e é indicado para iniciantes no esporte. . ",
                categoria: "natureza")
        p32.save(flush: true)


        def p33 = new Locais(valor: 70,
                cidade: "Ilhabela", estado: "sp",
                nome: "Passeio de escuna para a praia de Jabaquara",url: "img33.jpg",
                descricao: "Conheça praias incríveis de Ilhabela navegando com conforto e segurança a bordo de uma escuna. É um passeio inesquecível!",
                categoria: "natureza")
        p33.save(flush: true)


        def p34 = new Locais(valor: 72,
                cidade: "Socorro", estado: "sp",
                nome: "Rafting em Socorro",url: "img34.jpg",
                descricao: "Os participantes são conduzidos por um condutor especializado, responsável pela orientação do grupo. ",
                categoria: "natureza")
        p34.save(flush: true)


        def p35 = new Locais(valor: 179,
                cidade: "São Luiz do Paraitinga", estado: "sp",
                nome: "RAFTING BRAZADÃO",url: "img35.jpg",
                descricao: "Descer as corredeiras é uma prática divertida de manter a forma, já que o rafting trabalha todos os músculos do corpo. ",
                categoria: "natureza")
        p35.save(flush: true)

        def p36 = new Locais(valor: 95,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Passeio de Bike + Tirolesa",url: "img36.jpg",
                descricao: "Este combo da  Zoom Aventura incluí 1 hora de aluguel de bicicleta Scott, para curtir a trilha da cachoeira e na seqüência, caminhada para as Tirolesas do Sapucaí – 450 e 150 metros sobre o rio e a mata de araucárias. ",
                categoria: "natureza")
        p36.save(flush: true)


        def p37 = new Locais(valor: 130,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Passeio de bike + tirolesa + arborismo",url: "img37.jpg",
                descricao: "Este combo reune as 3 atividades: Tirolesas do Sapucaí , o percurso de arborismo  e 1 hora de aluguel de bicicleta. para o passeio até a cachoeira.",
                categoria: "natureza")
        p37.save(flush: true)


        def p38 = new Locais(valor: 0,
                cidade: "São Bernardo do Campo", estado: "sp",
                nome: "Parque Estoril",url: "img38.jpg",
                descricao: "Primeira unidade de conservação une Mata Atlântica, Represa Billings, fauna silvestre, e abriga o Zoológico Municipal e a Escola Livre de Sustentabilidade. ",
                categoria: "natureza")
        p38.save(flush: true)


        def p39 = new Locais(valor: 0,
                cidade: "Atibaia", estado: "sp",
                nome: "Atibaia 4x4",url: "img39.jpg",
                descricao: "Excursões pela natureza e vida selvagem, Excursões de off-road, quadriciclo , excursões e atividades ao ar livre. ",
                categoria: "natureza")
        p39.save(flush: true)

        def p40 = new Locais(valor: 0,
                cidade: "Eldorado", estado: "sp",
                nome: "Caverna do Diabo",url: "img40.jpg",
                descricao: "Maior caverna do estado de SP está preparada para receber visitas. ",
                categoria: "natureza")
        p40.save(flush: true)

        def p41 = new Locais(valor: 6,
                cidade: " São Paulo", estado: "sp",
                nome: "Pinacoteca do Estado de São Paulo", url: "img41.jpg",
                descricao: "A Pinacoteca de São Paulo é um museu de artes visuais com ênfase na produção brasileira do século XIX até a contemporaneidade. Fundada em 1905 é o museu de arte mais antigo da cidade.",
                categoria: "cidade")
        p41.save(flush: true)

        def p42 = new Locais(valor: 10,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu Do Futebol",url: "img42.jpg",
                descricao: "O Museu do Futebol tem a missão de investigar, preservar e comunicar o futebol como expressão cultural no Brasil, para instigar e inspirar ideias e experiências a partir do futebol. ",
                categoria: "cidade")
        p42.save(flush: true)

        def p43 = new Locais(valor: 30,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu de Arte de São Paulo Assis Chateaubriand - MASP",url: "img43.jpg",
                descricao: "O MASP é um museu privado sem fins lucrativos, fundado pelo empre­sário brasileiro Assis Chateaubriand, em 1947, tornando-se o primeiro museu moderno no país. ",
                categoria: "cidade")
        p43.save(flush: true)


        def p44 = new Locais(valor: 10,
                cidade: "São Paulo", estado: "sp",
                nome: "Jardim Botânico de São Paulo",url: "img44.jpg",
                descricao: "Os Jardins Botânicos têm um papel fundamental neste processo conservacionista e educacional, cujo objetivo é ensinar a importância da vegetação, da conservação da biodiversidade, da pesquisa científica e do desenvolvimento sustentável.  ",
                categoria: "cidade")
        p44.save(flush: true)


        def p45 = new Locais(valor: 30,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu da Imigração",url: "img45.jpg",
                descricao: "OMuseu da imigração retrata a situação dos imigrantes ao chegarem em SP.  ",
                categoria: "cidade")
        p45.save(flush: true)


        def p46= new Locais(valor: 0,
                cidade: "Marília", estado: "sp",
                nome: "Museu de Paleontologia de Marília",url: "img46.jpg",
                descricao: "O Museu de Paleontologia de Marília surgiu da necessidade de expor  fósseis de dinossauros e crocodilos que vem sendo coletados desde 1993 pelo paleontólogo William Nava.",
                categoria: "cidade")
        p46.save(flush: true)

        def p47= new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu Afro Brasil",url: "img47.jpg",
                descricao: "O Museu conta com um grande acervo permanente sobre a cultura afrobrasileira! Há também exposições temporárias excelentes! ",
                categoria: "cidade")
        p47.save(flush: true)

        def p48= new Locais(valor: 0,
                cidade: "Brodowski", estado: "sp",
                nome: "Museu Casa de Portinari",url: "img48.jpg",
                descricao: "A antiga casa onde Portinari nasceu traz as lembranças, mostra as várias fases do pintor, suas mudanças e melhorias da casa, que conta com uma pequena capela erguida para a avó de Portinari.",
                categoria: "cidade")
        p48.save(flush: true)

        def p49 = new Locais(valor: 11,
                cidade: "Taubaté", estado: "sp",
                nome: "Museu Mazzaropi",url: "img49.jpg",
                descricao: "O Museu resgata a história deste artista incrível e pouco lembrado nos dias de hoje e pelas novas gerações. Horário: Ter - Dom 08:30 - 12:30.",
                categoria: "cidade")
        p49.save(flush: true)


        def p50 = new Locais(valor: 10,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Museu Felícia Leirner",url: "img50.jpg",
                descricao: "Parque com as belas esculturas da artista Felícia Leirner dispostas em ordem cronológica, espalhadas em meio às araucárias. Horário: Ter - Dom 09:00 - 17:00",
                categoria: "cidade")
        p50.save(flush: true)


        def p51 = new Locais(valor: 10,
                cidade: "Santos", estado: "sp",
                nome: "Museu do Café",url: "img51.jpg",
                descricao: "Apresenta o patrimônio de homens e mulheres que, de alguma forma, se relacionaram com esse universo. ",
                categoria: "cidade")
        p51.save(flush: true)


        def p52 = new Locais(valor: 6,
                cidade: "São Paulo", estado: "sp",
                nome: "Catavento Cultural e Educacional",url: "img52.jpg",
                descricao: "Museu Catavento tem sido um grande fenômeno de público, tendo atingido a marca de dois milhões e meio de visitantes em apenas seis anos de operação.",
                categoria: "cidade")
        p52.save(flush: true)

        def p53 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu da Imagem e do Som",url: "img53.jpg",
                descricao: "Conta com um acervo de mais de 200 mil itens como fotografias, filmes, vídeos e cartazes e uma produção cultural diversificada.",
                categoria: "cidade")
        p53.save(flush: true)

        def p54 = new Locais(valor: 64,
                cidade: "Brotas", estado: "sp",
                nome: "CEU - Centro de Estudos do Universo",url: "img54.jpg",
                descricao: "As propostas de atividades abordam todos os ciclos do ensino adaptadas para suprir todas as necessidades do currículo escolar, atingindo assim todas as faixas etárias. ",
                categoria: "cidade")
        p54.save(flush: true)

        def p55 = new Locais(valor: 0,
                cidade: "Santos", estado: "sp",
                nome: "Museu Pelé",url: "img55.jpg",
                descricao: "Museu Pelé é um museu na cidade de Santos, dedicado à carreira do ex-jogador Pelé.",
                categoria: "cidade")
        p55.save(flush: true)

        def p56 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu Paulista",url: "img56.jpg",
                descricao: "É museu público mais antigo da cidade de São Paulo, cuja sede é um monumento-edifício que faz parte do conjunto arquitetônico do Parque da Independência. ",
                categoria: "cidade")
        p56.save(flush: true)

        def p57 = new Locais(valor: 0,
                cidade: "São Roque", estado: "sp",
                nome: "Museu de Cera",url: "img57.jpg",
                descricao: "O Museu de Cera Alpino encontra-se nas dependências do Hotel Alpino e conta com diversos personagens que marcaram a história do Brasil.",
                categoria: "cidade")
        p57.save(flush: true)

        def p58 = new Locais(valor: 0,
                cidade: "Taubaté", estado: "sp",
                nome: "Museu Monteiro Lobato",url: "img58.jpg",
                descricao: "O Museu Monteiro Lobato é uma instituição cultural pública quefunciona na casa onde o escritor Monteiro Lobato nasceu e viveu até os 12 anos.",
                categoria: "cidade")
        p58.save(flush: true)

        def p59 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu de Arte Moderna",url: "img59.jpg",
                descricao: "O MAM é uma das mais importantes instituições culturais do Brasil.",
                categoria: "cidade")
        p59.save(flush: true)

        def p60 = new Locais(valor: 0,
                cidade: "Araraquara", estado: "sp",
                nome: "Museu de Arqueologia e Paleontologia",url: "img60.jpg",
                descricao: "O MAPA abre suas portas ao público durante a semana das 12h15 às 17h45, e aos sábados, das 9 às 12 horas.",
                categoria: "cidade")
        p60.save(flush: true)

        def p61 = new Locais(valor: 15,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Jardim Botânico",url: "img61.jpg",
                descricao: "O J JBRJ – foi fundado com a intenção de se instalar  um jardim para aclimatação de espécies vegetais originárias de outras partes do mundo. ",
                categoria: "cidade")
        p61.save(flush: true)

        def p62 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu do Índio",url: "img62.jpg",
                descricao: "O Museu do Índio, tem como objetivo contribuir para uma maior conscientização sobre a contemporaneidade e a importância das culturas indígenas. ",
                categoria: "cidade")
        p62.save(flush: true)

        def p63 = new Locais(valor: 80,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Pão de Açúcar",url: "img63.jpg",
                descricao: "O Pão de Açúcar é um complexo de morros localizado no bairro da Urca, na cidade do Rio de Janeiro, no Brasil. ",
                categoria: "cidade")
        p63.save(flush: true)

        def p64 = new Locais(valor: 30,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Corcovado - Cristo Redentor",url: "img64.jpg",
                descricao: "Cristo Redentor é uma estátua  que retrata Jesus Cristo, localizada no topo do morro do Corcovado, a 709 metros acima do nível do mar.",
                categoria: "cidade")
        p64.save(flush: true)

        def p65 = new Locais(valor: 30,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Morro da Urca",url: "img65.jpg",
                descricao: "De cima do Morro da Urca tem-se uma vista espetacular de vários pontos da cidade, como as praias do Leme, Copacabana e Ipanema",
                categoria: "cidade")
        p65.save(flush: true)

        def p66 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Pedra Bonita",url: "img66.jpg",
                descricao: "A Pedra Bonita é um ponto turístico localizado no Parque Nacional da Tijuca, na cidade do Rio de Janeiro. ",
                categoria: "cidade")
        p66.save(flush: true)

        def p67 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Morro Dois Irmãos",url: "img67.jpg",
                descricao: "Morro Dois Irmãos é uma formação rochosa no bairro do Vidigal, no Rio de Janeiro. ",
                categoria: "cidade")
        p67.save(flush: true)


        def p68 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Mosteiro De São Bento",url: "img68.jpg",
                descricao: "Fundado em 1590 por monges vindos da Bahia, o Mosteiro foi construído a pedido dos próprios habitantes da recém fundada cidade de São Sebastião.  ",
                categoria: "cidade")
        p68.save(flush: true)

        def p69 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Parque Lage",url: "img69.jpg",
                descricao: "Originário de um antigo engenho de açúcar, o parque faz parte da memória histórica da cidade.",
                categoria: "cidade")
        p69.save(flush: true)

        def p71 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Mirante do Leblon",url: "img71.jpg",
                descricao: "Um grande deck de madeira, apoiado sobre rochas, que permite uma bela vista da orla oceânica do Bairro do Leblon e de Ipanema.",
                categoria: "cidade")
        p71.save(flush: true)

        def p72 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Arcos da Lapa",url: "img72.jpg",
                descricao: "Considerada como a obra arquitetônica de maior porte empreendida no Brasil durante o período colonial, é hoje um dos cartões postais da cidade.",
                categoria: "cidade")
        p72.save(flush: true)

        def p73 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Centro Cultural Banco do Brasil",url: "img73.jpg",
                descricao: "O CCBB é uma rede de espaços culturais geridas e mantidas pelo Banco do Brasil, com o objetivo de disseminar a cultura pela população.",
                categoria: "cidade")
        p73.save(flush: true)

        def p74 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Academia Brasileira de Letras",url: "img74.jpg",
                descricao: "Academia Brasileira de Letras é uma instituição literária brasileira fundada na cidade do Rio de Janeiro em 20 de julho de 1897. ",
                categoria: "cidade")
        p74.save(flush: true)

        def p75 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Palácio Guanabara",url: "img75.jpg",
                descricao: "O Palácio Guanabara localiza-se na Rua Pinheiro Machado (antiga Rua Guanabara), no bairro de Laranjeiras, na zona sul da cidade do Rio de Janeiro.  ",
                categoria: "cidade")
        p75.save(flush: true)


        def p76 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu Casa de Rui Barbosa",url: "img76.jpg",
                descricao: "Tem como finalidade o desenvolvimento da cultura, da pesquisa e do ensino pela a divulgação e o culto da obra e da vida de Rui Barbosa.  ",
                categoria: "cidade")
        p76.save(flush: true)

        def p77 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu do Amanhã",url: "img77.jpg",
                descricao: "A proposta é ser um museu de artes e ciências, além de contar com mostras que alertam sobre os perigos das mudanças climáticas, da degradação ambiental e do colapso social. ",
                categoria: "cidade")
        p77.save(flush: true)

        def p78 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu de Arte Contemporânea",url: "img78.jpg",
                descricao: "O MAC é um museu de arte contemporânea brasileira localizado na cidade de Niterói, no Rio de Janeiro, no Brasil. ",
                categoria: "cidade")
        p78.save(flush: true)

        def p79 = new Locais(valor: 40,
                cidade: "Petrópolis", estado: "rj",
                nome: "Museu de Cera de Petrópolis",url: "img79.jpg",
                descricao: "O museu é o primeiro no Brasil com padrões artísticos internacionais de hiper-realismo, no qual personalidades nacionais e internacionais são retratadas com perfeição. ",
                categoria: "cidade")
        p79.save(flush: true)

        def p80 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu Histórico do Exército / Forte de Copacabana",url: "img80.jpg",
                descricao: "O Forte de Copacabana localiza-se na ponta de Copacabana, ao final da praia e bairro de mesmo nome, na cidade do Rio de Janeiro, no Brasil ",
                categoria: "cidade")
        p80.save(flush: true)

        def p81 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia de Ipanema",url: "img81.jpg",
                descricao: "Ipanema figura entre os principais pontos turísticos da cidade do Rio de Janeiro, tanto para turistas brasileiros quanto internacionais. ",
                categoria: "outros")
        p81.save(flush: true)

        def p82 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia de Copacabana",url: "img82.jpg",
                descricao: "Uma das praias mais conhecida e frequentada do Brasil no mundo. ",
                categoria: "outros")
        p82.save(flush: true)

        def p83 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Leblon",url: "img83.jpg",
                descricao: "A frequência maior é de moradores que procuram se refrescar nas águas do mar. ",
                categoria: "outros")
        p83.save(flush: true)

        def p84 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia da Barra da Tijuca",url: "img84.jpg",
                descricao: "Os principais esportes praticados da praia são a pesca de beira, kitesurf, windsurf e flysurf, além de ser palco de muitos campeonatos.",
                categoria: "outros")
        p84.save(flush: true)

        def p85 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Leme",url: "img85.jpg",
                descricao: "Uma delícia de praia, apesar de ser continuação da praia de Copacabana, estando no Bairro do Leme leva o nome de Praia do Leme.  ",
                categoria: "outros")
        p85.save(flush: true)

        def p86 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Vidigal",url: "img86.jpg",
                descricao: "Praia que fica na encosta do morro Dois Irmãos, próxima a comunidade do Vidigal e do Hotel Sheraton. ",
                categoria: "outros")
        p86.save(flush: true)

        def p87 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Feira Hippie de Ipanema",url: "img87.jpg",
                descricao: "Grande número de artesãos, artistas e comerciantes, teom tdo tipo de artigo e vestuário. ",
                categoria: "outros")
        p87.save(flush: true)

        def p88  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Parque Nacional da Tijuca",url: "img88.jpg",
                descricao: "Localizado no coração do Rio de Janeiro, recebe três milhões de visitantes por ano, entre brasileiros e estrangeiros de todas as idades. ",
                categoria: "outros")
        p88.save(flush: true)

        def p89  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Lagoa Rodrigo de Freitas",url: "img89.jpg",
                descricao: "Os passeios de pedalinhos ou pequenos botes movidos à pedal, estão entre as atrações de lazer encontrada às margens da Lagoa Rodrigo de Freitas.",
                categoria: "outros")
        p89.save(flush: true)

        def p90  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Paço Imperial  ",url: "img90.jpg",
                descricao: "O Paço Imperial é um edifício colonial localizado na atual Praça XV de Novembro, no centro histórico da cidade do Rio de Janeiro, Brasil. ",
                categoria: "outros")
        p90.save(flush: true)


        def p91  = new Locais(valor: 0,
                cidade: "Búzios", estado: "rj",
                nome: "Praia da Ferradura  ",url: "img91.jpg",
                descricao: "A Praia da Ferradura é tranquila e suas águas são frias. ",
                categoria: "outros")
        p91.save(flush: true)

        def p92  = new Locais(valor: 0,
                cidade: "Paraty", estado: "rj",
                nome: "Praia do Cachadaço - Trindade  ",url: "img92.jpg",
                descricao: "Nesta praia, o mar é propício a prática do surfe e pontos onde os turistas podem se banhar calmamente e praticar mergulho.",
                categoria: "outros")
        p92.save(flush: true)

        def p93  = new Locais(valor: 0,
                cidade: "Itacuruça", estado: "rj",
                nome: "Praia Grande ",url: "img93.jpg",
                descricao: " Com uma boa faixa de areia clara, possui mar tranquilo, ótimo para o banho.",
                categoria: "outros")
        p93.save(flush: true)

        def p94  = new Locais(valor: 0,
                cidade: "Arraial do Cabo", estado: "rj",
                nome: "PRAIA DO FORNO ",url: "img94.jpg",
                descricao: "Conta com um trecho de mata preservada, águas claras e corais.",
                categoria: "outros")
        p94.save(flush: true)

        def p95  = new Locais(valor: 0,
                cidade: "Búzios", estado: "rj",
                nome: "Praia de Geribá ",url: "img95.jpg",
                descricao: "Talvez a praia mais conhecida de Búzios, com certeza a mais frequentada por famosos. ",
                categoria: "outros")
        p95.save(flush: true)

        def p96  = new Locais(valor: 0,
                cidade: "Cabo Frio", estado: "rj",
                nome: "Praia do Forte",url: "img96.jpg",
                descricao: "A Praia do Forte é uma das mais conhecidas e mais agitadas praias de Cabo Frio. ",
                categoria: "outros")
        p96.save(flush: true)

        def p97  = new Locais(valor: 0,
                cidade: "Cabo Frio", estado: "rj",
                nome: "Praia das Conchas",url: "img97.jpg",
                descricao: " Frequentada pelos aficionados pela pesca de arremesso. Os peixes mais capturados nesta praia são o badejo, a anchova e a tainha. ",
                categoria: "outros")
        p97.save(flush: true)

        def p98  = new Locais(valor: 0,
                cidade: "Búzios", estado: "rj",
                nome: "Praia de João Fernandes",url: "img98.jpg",
                descricao: "O espetáculo começa ainda no topo do mirante que mostra o belo mar da Praia de João Fernandes. Difícil não se encantar e descer até a areia. ",
                categoria: "outros")
        p98.save(flush: true)

        def p99  = new Locais(valor: 0,
                cidade: "Niterói", estado: "rj",
                nome: "Praia de Camboinhas",url: "img99.jpg",
                descricao: "Se destaca por uma orla belíssima, com quiosques muito bem estruturados, calçadão e até uma aldeia indígena no seu canto esquerdo.",
                categoria: "outros")
        p99.save(flush: true)

        def p100  = new Locais(valor: 0,
                cidade: "Arraial do Cabo", estado: "rj",
                nome: "Praia dos Anjos",url: "img100.jpg",
                descricao: "O visual da Praia dos Anjos atrai pelo bucólico conjunto de barcos, onde durante todo o dia pescadores voltam do mar carregados de peixes para o mercado.",
                categoria: "outros")
        p100.save(flush: true)

        def p101  = new Locais(valor: 280,
                cidade: "Angra dos Reis", estado: "rj",
                nome: "Batismo de Mergulho",url: "img101.jpg",
                descricao: "No batismo você terá a oportunidade de descobrir o fundo do mar acompanhado de um profissional qualificado.",
                categoria: "natureza")
        p101.save(flush: true)


        def  p102  = new Locais(valor: 200,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Batismo de Escalada",url: "img102.jpg",
                descricao: "Ideal para iniciantes, apresenta uma escalada de até 30 metros com bela visão dos arredores da Urca, enseada da Praia Vermelha e da Praia de Botafogo. ",
                categoria: "natureza")
        p102.save(flush: true)

        def p103  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome: "Parque Nacional de Itatiaia",url: "img103.jpg",
                descricao: "Unidade de conservação brasileira de proteção integral da natureza localizada no maciço do Itatiaia.",
                categoria: "natureza")
        p103.save(flush: true)

        def  p104  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome:" Cachoeira Véu de Noiva",url: "img104.jpg",
                descricao: "Cachoeira formada pelo Rio Maromba formando uma queda d'água de 40 m de altura, fica a 1.100 m de altitude..",
                categoria: "natureza")
        p104.save(flush: true)

        def p105  = new Locais(valor:0,
                cidade: "Nova Friburgo", estado: "rj",
                nome:" Cachoeira Poço Belo ",url: "img105.jpg",
                descricao: " A tranquilidade das vilas da região de colonização suíça e alemã, o clima agradável, as matas e as cachoeiras são ingredientes muito atrativos. ",
                categoria: "natureza")
        p105.save(flush: true)

        def p106  = new Locais(valor:0,
                cidade: "Visconde de Mauá", estado: "rj",
                nome:" Vale do Alcantilado ",url: "img106.jpg",
                descricao: " Uma sequência de nove cachoeiras terminando com a mais alta de todas, para dar o toque final. ",
                categoria: "natureza")
        p106.save(flush: true)

        def p107  = new Locais(valor:0,
                cidade: "Macabu", estado: "rj",
                nome:" Cachoeira da Amorosa ",url: "img107.jpg",
                descricao: "A Cachoeira da Amorosa é a mais famosa cachoeira do município de Conceição de Macabu. ",
                categoria: "natureza")
        p107.save(flush: true)

        def p108  = new Locais(valor:0,
                cidade: "Paraty", estado: "rj",
                nome:" Cachoeira do Tobogã",url: "img108.jpg",
                descricao: "Enquanto a água corre pela pedra lisa da Cachoeira do Tobogã, moradores da região se preparam, em fila, para descer em alta velocidade. ",
                categoria: "natureza")
        p108.save(flush: true)

        def p109  = new Locais(valor:0,
                cidade: "Penedo", estado: "rj",
                nome:" Três Cachoeiras",url: "img109.jpg",
                descricao: "Muito frequentada nos finais de semana e feriados pela proximidade do Centro, é formada por três quedas que formam piscinas naturais boas para banho. ",
                categoria: "natureza")
        p109.save(flush: true)


        def p110  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome:" Cachoeira de Deus",url: "img110.jpg",
                descricao: "Muito frequentada nos finais de semana e feriados é uma das praias mais conhecidas de Itatiaia. ",
                categoria: "natureza")
        p110.save(flush: true)

        def p111  = new Locais(valor:0,
                cidade: "Angra dos Reis", estado: "rj",
                nome:" Cachoeira da Feiticeira",url: "img111.jpg",
                descricao: "A Cachoeira da Feiticeira é de beleza espetacular e exalta a admiração de quem a visita, certamente é uma das melhores caminhadas em toda a Ilha Grande.",
                categoria: "natureza")
        p111.save(flush: true)

        def p112  = new Locais(valor:0,
                cidade: "Nova Friburgo", estado: "rj",
                nome:" Cachoeira Indiana Jones",url: "img112.jpg",
                descricao: "A cachoeira da Boa Vista (Indiana Jones), possui um canyon estreito com pedras formando um escorrega natural.",
                categoria: "natureza")
        p112.save(flush: true)

        def p113  = new Locais(valor:0,
                cidade: "Visconde de Mauá", estado: "rj",
                nome:" Cachoeira do Escorrega",url: "img113.jpg",
                descricao: "A cachoeira é um tobogã natural , que foi esculpido pela natureza após uma forte tempestade em 1966.",
                categoria: "natureza")
        p113.save(flush: true)

        def p114  = new Locais(valor:0,
                cidade: "Cachoeiras de Macacu", estado: "rj",
                nome:" Cachoeira Poço Tenebroso",url: "img114.jpg",
                descricao: "Localizada em Cachoeiras de Macacu (RJ), é a mais bela queda d'água do município.",
                categoria: "natureza")
        p114.save(flush: true)

        def p115  = new Locais(valor:0,
                cidade: "Cachoeiras de Macacu", estado: "rj",
                nome:" Cachoeira Poço Tenebroso",url: "img115.jpg",
                descricao: "A Cachoeira da Pedra Branca está entre as mais movimentadas de Paraty.",
                categoria: "natureza")
        p115.save(flush: true)

        def p116  = new Locais(valor:0,
                cidade: "Conservatória", estado: "rj",
                nome:" Cachoeira da Índia",url: "img116.jpg",
                descricao: "A Cachoeira da Índia está situada no Balneário Municipal João Raposo.",
                categoria: "natureza")
        p116.save(flush: true)

        def p117  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome:"CACHOEIRA DAS ANTAS",url: "img117.jpg",
                descricao: "A melhor maneira de conhecê-la é através de uma cavalgada. A cachoeira fica dentro do Hotel Fazenda da Serra, sendo pouco movimentada",
                categoria: "natureza")
        p117.save(flush: true)

        def p118  = new Locais(valor:0,
                cidade: "Rio do Ouro", estado: "rj",
                nome:"Cachoeira das Andorinhas",url: "img118.jpg",
                descricao: "Rio do Ouro é um município bastante humilde e muito pouco explorado turisticamente. ",
                categoria: "natureza")
        p118.save(flush: true)


        def p119  = new Locais(valor:0,
                cidade: "Nova Friburgo", estado: "rj",
                nome:"Cachoeira Toca da Onça",url: "img119.jpg",
                descricao: "Toca da Onça, lugar com ambiente florestal intocado , natureza exuberante como as corredeiras do Rio Macaé. ",
                categoria: "natureza")
        p119.save(flush: true)

        def p120  = new Locais(valor:0,
                cidade: "Nova Friburgo", estado: "rj",
                nome:"Cachoeira do Iriri",url: "img120.jpg",
                descricao: "A Cachoeira do Iriri trata-se de uma bela queda d'água que termina numa refrescante piscina. ",
                categoria: "natureza")
        p120.save(flush: true)

        redirect (uri: "/adm")

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
