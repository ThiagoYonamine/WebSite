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
                descricao: "Com seus 12.000 metros quadrados de área o Parque da Floresta Encantada é uma opção segura e divertida para seu filhos.Aberto diariamente das 9:30h às 17:00h, aos sábados, domingos e feriados.",
                categoria: "outros")
        p4.save(flush: true)

        def p5 = new Locais(valor: 0,
                cidade: "Itupeva", estado: "sp",
                nome: "Wet'n Wild",url: "img5.jpg",
                descricao: "O parque de diversão apresenta diversos brinquedos,profissionais atenciosos ,simpáticos em um local limpo e sustentável.",
                categoria: "outros")
        p5.save(flush: true)

        def p6 = new Locais(valor: 78,
                cidade: "Cesário Lange", estado: "sp",
                nome: "Castelo Park Aquático",url: "img6.jpg",
                descricao: "O Castelo Park Aquático com diversas atrações para a diversão com a família e os amigos!",
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
                descricao: "A cidade de Praia Grande tem uma das mais movimentadas praias do Brasil.",
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
                descricao: "BORACÉIA é um cartão de visitas, para os amantes da natureza, local excelente para pratica de esportes náuticos. O canto direito é um convite às boas ondas. Os cantos da praia são preferidos para mergulho, e daí saem trilhas que dão acesso às prainhas, ótimas para banho. No canto direito sai a trilha para piscinas naturais e a praia de Itaguá. ",
                categoria: "outros")
        p14.save(flush: true)


        def p15 = new Locais(valor: 0,
                cidade: "Ubatuba", estado: "sp",
                nome: "Praia do Lázaro",url: "img15.jpg",
                descricao: "A Praia do Lázaro é muito procurada por famílias, tem areias finas e firmes, mar calmo e cristalino com vista para o famoso Pico do Corcovado. É bastante sombreada pela presença de muitas árvores onde começa a areia. ",
                categoria: "outros")
        p15.save(flush: true)

        def p16 = new Locais(valor: 0,
                cidade: "Caraguatatuba", estado: "sp",
                nome: "Praia Massaguacu",url: "img16.jpg",
                descricao: "Esta praia possui uma grande extensão e um mar bem agitado, sendo recomendada para a prática do surf e para a pesca de arremesso. Mais próxima a praia da Coconha possui uma boa infraestrutura e é costeada por muitos prédios e casas, mais ao norte temos espalhados ao longo da orla alguns quiosques. Por ser uma praia de tombo recomenda-se muito cuidado por apresentar fortes correntes marinhas. ",
                categoria: "outros")
        p16.save(flush: true)

        def p17 = new Locais(valor: 0,
                cidade: "Peruíbe", estado: "sp",
                nome: "Praia Barra do Una",url: "img17.jpg",
                descricao: "A Praia Barra do Una em Peruíbe, com 2 km de águas claras e limpas, larga faixa de areia escura e dura, vegetação de restinga e cercada por morros ao fundo e no canto esquerdo, é um paraíso ecológico, com uma pequena vila de pescadores, localizada na Estação Ecológica Juréia-Itatins. ",
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
                nome: "CANIONISMO JACARÉ EM BROTAS",url: "img21.jpg",
                descricao: "O Canionismo Jacaré traz a oportunidade de desbravar um Canion. A atividade basicamente consiste na prática do rapel em três cachoeiras interligadas por trilhas. Cachoeira da Usina 6 metros (para treinamento), Cachoeira São Sebastião 25 metros e Cachoeira Jacaré 38 metros.",
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
                descricao: "As cachoeiras Cassorova e dos Quatis são um dos “cartões postais” de Brotas escondidas depois de uma trilha no meio da mata preservada e na grandeza da paisagem do vale Cassorova. Horário de funcionamento: Aberto à visitação todos os dias das 09h00 às 17h00.",
                categoria: "natureza")
        p23.save(flush: true)


        def p24 = new Locais(valor: 0,
                cidade: "Brotas", estado: "sp",
                nome: "Cachoeira Três Quedas",url: "img24.jpg",
                descricao: "O parque tem três cachoeiras: da Nascente, Figueira e Andorinha. É permitido tomar banho de cachoeira. O local possui hospedagem e um restaurante. ",
                categoria: "natureza")
        p24.save(flush: true)


        def p25 = new Locais(valor: 0,
                cidade: "Cunha", estado: "sp",
                nome: "Parque Estadual da Serra do Mar - Núcleo Cunha",url: "img25.jpg",
                descricao: "A visita as cachoeiras é realizada com acompanhamento de um  Guia turístico do local que apresenta uma série de curiosidades sobre a região, as cachoeiras e a Mata Atlântica.",
                categoria: "natureza")
        p25.save(flush: true)


        def p26 = new Locais(valor: 0,
                cidade: "Ilhabela", estado: "sp",
                nome: "Cachoeira do Gato",url: "img26.jpg",
                descricao: "Com 40 metros de altura, a Cachoeira do Gato é a estrela da trilha que leva o seu nome. O percurso da trilha é de quatro quilômetros e leva aproximadamente uma hora e 30 minutos para ser todo percorrido. ",
                categoria: "natureza")
        p26.save(flush: true)



        def p27 = new Locais(valor: 0,
                cidade: "Ilhabela", estado: "sp",
                nome: "Cachoeira da Toca",url: "img27.jpg",
                descricao: "Um dos pontos turísticos mais tradicionais de Ilhabela. Horário de funcionamento: Seg à Seg -  09:00 às 17:00. ",
                categoria: "natureza")
        p27.save(flush: true)


        def p28 = new Locais(valor: 20,
                cidade: "Itirapina", estado: "sp",
                nome: "Cachoeiras Saltão,  Monjolinho e Ferradura",url: "img28.jpg",
                descricao: "Os amantes da natureza encontram na Cachoeira Saltão um dos cenários mais belos que a natureza local oferece. São 75 metros de altura de uma deslumbrante queda d’água.Visitação inclusa as cachoeiras Cachoeira Saltão, Cachoeira Monjolinho e Cachoeira Ferradura. Um dia repleto de muita diversão em meio à natureza. ",
                categoria: "natureza")
        p28.save(flush: true)


        def p29 = new Locais(valor: 0,
                cidade: "Eldorado", estado: "sp",
                nome: "Cachoeira Queda do Meu Deus",url: "img29.jpg",
                descricao: "A Cachoeira tem uma imensa queda de 53 metros e sua origem está numa nascente em área fechada, que atravessa quatro quilômetros no interior da Caverna do Diabo. O acesso mais curto para a bela cachoeira é uma caminhada de dificuldade média (uma hora), que passa por piscinas naturais com água cristalina e três quedas menores. Há outro caminho pela trilha completa do Vale das Ostras (cinco horas), incluindo mais 11 quedas. Nesse caso é obrigatório contar com a presença de um monitor. ",
                categoria: "natureza")
        p29.save(flush: true)

        def p30 = new Locais(valor: 5,
                cidade: "Santo Antônio do Pinhal", estado: "sp",
                nome: "Cachoeira do Lageado",url: "img30.jpg",
                descricao: "Cercada por muito verde, ar puro e a tranqüilidade do interior, a Cachoeira do Lageado está localizada no bairro de mesmo nome (Lageado), e fica a 7 km do centro da cidade com acesso em boas condições por uma estrada de terra. É a cachoeira mais visitada pelos turistas, por oferecer aos visitantes infra estrutura básica para passar bem o dia, como banheiros, área piquenique e trilhas em meio a mata.A queda d'agua de sua cachoeira forma uma piscina natural em meios às pedras, com fundo de areia, permitindo o banho.",
                categoria: "natureza")
        p30.save(flush: true)


        def p31 = new Locais(valor: 0,
                cidade: "Itararé", estado: "sp",
                nome: "Cachoeira do Corisco",url: "img31.jpg",
                descricao: "A Cachoeira do Corisco é uma das mais belas dessa lista. Vista do mirante de madeira, sua queda de 106 metros impressiona os visitantes. A cachoeira fica no meio de cânions e forma um verdadeiro cartão postal. A cachoeira fica localizada próxima a divisa de São Paulo com o Paraná, na cidade de Sengés - PR. Como uma das suas princípais vias de acesso é feita através de Itararé, interior de São Paulo, não podiamos deixar de fora da nossa lista. ",
                categoria: "natureza")
        p31.save(flush: true)


        def p32 = new Locais(valor: 110,
                cidade: "Juquitt", estado: "sp",
                nome: "Cachoeira do Corisco",url: "img32.jpg",
                descricao: "A Cachoeira do Corisco é uma das mais belas dessa lista. Vista do mirante de madeira, sua queda de 106 metros impressiona os visitantes. A cachoeira fica no meio de cânions e forma um verdadeiro cartão postal. A cachoeira fica localizada próxima a divisa de São Paulo com o Paraná, na cidade de Sengés - PR. Como uma das suas princípais vias de acesso é feita através de Itararé, interior de São Paulo, não podiamos deixar de fora da nossa lista. ",
                categoria: "natureza")
        p32.save(flush: true)


        def p33 = new Locais(valor: 70,
                cidade: "Ilhabela", estado: "sp",
                nome: "PASSEIO DE ESCUNA PARA PRAIA DO JABAQUARA E DA FOME",url: "img33.jpg",
                descricao: "Conheça praias incríveis de Ilhabela navegando com conforto e segurança a bordo de uma escuna. É um passeio inesquecível!",
                categoria: "natureza")
        p33.save(flush: true)


        def p34 = new Locais(valor: 72,
                cidade: "Socorro", estado: "sp",
                nome: "RAFTING EM SOCORRO",url: "img34.jpg",
                descricao: "Os participantes são conduzidos por um condutor especializado, responsável pela orientação do grupo. Todos remam durante o percurso.Esta modalidade exige trabalho em equipe, agilidade e reflexos apurados, atenção aos comandos, e superação de limites e obstáculos. O percurso total do Rafting é de 4 km pelas águas do rio do Peixe. ",
                categoria: "natureza")
        p34.save(flush: true)


        def p35 = new Locais(valor: 179,
                cidade: "São Luiz do Paraitinga", estado: "sp",
                nome: "RAFTING BRAZADÃO",url: "img35.jpg",
                descricao: "Descer as corredeiras é uma prática divertida de manter a forma, já que o rafting trabalha todos os músculos do corpo, com destaque para os braços, ombros e o peito por conta do ritmo das remadas. Por ser um esporte de equipe, aumenta a socialização e trabalha a flexibilidade, e principalmente, tem a vantagem de se estar em contato direto com a natureza, o que torna o rafting uma escolha ainda mais atraente para quem vive em cidades e longe do ar puro e da paz que a natureza oferece. ",
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
                descricao: "Este combo reune as 3 atividades: Tirolesas do Sapucaí – 450 e 150 metros, o percurso de arborismo de 9 travessias, e 1 hora de aluguel de bicicleta. para o passeio até a cachoeira.",
                categoria: "natureza")
        p37.save(flush: true)


        def p38 = new Locais(valor: 0,
                cidade: "São Bernardo do Campo", estado: "sp",
                nome: "Parque Estoril",url: "img38.jpg",
                descricao: "Primeira unidade de conservação une Mata Atlântica, Represa Billings, fauna silvestre, e abriga o Zoológico Municipal e a Escola Livre de Sustentabilidade. Conta com teleférico, pedalinho, stand up paddle e caiaques, trilhas para caminhada, viveiro escola, jardim sensorial, área de piquenique, área de banho, estacionamento, lanchonetes, museu de arte ao ar livre e são frequentes as atividades de educação ambiental. ",
                categoria: "natureza")
        p38.save(flush: true)


        def p39 = new Locais(valor: 0,
                cidade: "Atibaia", estado: "sp",
                nome: "Atibaia 4x4",url: "img39.jpg",
                descricao: "Excursões pela natureza e vida selvagem, Excursões de off-road, quadriciclo e 4WD, Excursões, Atividades ao ar livre. ",
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
                descricao: "O Museu do Futebol tem a missão de investigar, preservar e comunicar o futebol como expressão cultural no Brasil, em diálogo com todos os públicos, para instigar e inspirar ideias e experiências a partir do futebol. Visa a ser referência constante e global: no tratamento do futebol como patrimônio; em acessibilidade; em sustentabilidade e no respeito à diversidade cultural. Busca ser um museu que dialoga com seus públicos no desenvolvimento de suas ações. ",
                categoria: "cidade")
        p42.save(flush: true)

        def p43 = new Locais(valor: 30,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu de Arte de São Paulo Assis Chateaubriand - MASP",url: "img43.jpg",
                descricao: "O Museu de Arte de São Paulo é um museu privado sem fins lucrativos, fundado pelo empre­sário brasileiro Assis Chateaubriand, em 1947, tornando-se o primeiro museu moderno no país. ",
                categoria: "cidade")
        p43.save(flush: true)


        def p44 = new Locais(valor: 10,
                cidade: "São Paulo", estado: "sp",
                nome: "Jardim Botânico de São Paulo",url: "img44.jpg",
                descricao: "A missão do Jardim Botânico de São Paulo é a preservação e o uso sustentável da biodiversidade paulista e brasileira, por meio da conservação “in-situ” e “ex-situ”, e o conhecimento de todos os grupos de plantas e fungos, bem como de suas relações com o meio ambiente.Os Jardins Botânicos têm um papel fundamental neste processo conservacionista e educacional, cujo objetivo é ensinar a importância da vegetação, da conservação da biodiversidade, da pesquisa científica e do desenvolvimento sustentável.  ",
                categoria: "cidade")
        p44.save(flush: true)


        def p45 = new Locais(valor: 30,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu da Imigração",url: "img45.jpg",
                descricao: "Localizado na Mooca, o Museu da imigração retrata a situação dos imigrantes ao chegarem em SP. Tem uma bela entrada com um jardim onde é possível descansar e também tirar belas fotos. É possível tirar fotos com roupas antigas; procurar sobrenomes dos imigrantes num painel e também interagir durante a visita. ",
                categoria: "cidade")
        p45.save(flush: true)


        def p46= new Locais(valor: 0,
                cidade: "Marília", estado: "sp",
                nome: "Museu de Paleontologia de Marília",url: "img46.jpg",
                descricao: "O Museu de Paleontologia de Marília surgiu da necessidade de expor para a comunidade de Marília e região, fósseis de dinossauros e crocodilos que vem sendo coletados desde 1993 pelo paleontólogo William Nava, em campanhas de campo pela região.",
                categoria: "cidade")
        p46.save(flush: true)

        def p47= new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu Afro Brasil",url: "img47.jpg",
                descricao: "O Museu conta com um grande acervo permanente sobre a cultura afrobrasileira! Há também exposições temporárias excelentes! Horário: Ter - Dom 10:00 - 18:00.",
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
                descricao: "O Museu resgata a história deste artista incrível e pouco lembrado nos dias de hoje e pelas novas gerações. Possui uma lojinha de lembranças, DVDs e outros produtos com a marca do artista.Vale a pena a visita. Horário: Ter - Dom 08:30 - 12:30.",
                categoria: "cidade")
        p49.save(flush: true)


        def p50 = new Locais(valor: 10,
                cidade: "Campos do Jordão", estado: "sp",
                nome: "Museu Felícia Leirner",url: "img50.jpg",
                descricao: "Parque muito bem cuidado, com as belas esculturas da artista Felícia Leirner dispostas em ordem cronológica, espalhadas em meio às araucárias. De lá se avista o Morro do Baú e uma linda paisagem. Horário: Ter - Dom 09:00 - 17:00",
                categoria: "cidade")
        p50.save(flush: true)


        def p51 = new Locais(valor: 10,
                cidade: "Santos", estado: "sp",
                nome: "Museu do Café",url: "img51.jpg",
                descricao: "A missão do Museu. o entanto,é apresentar o patrimônio de homens e mulheres que, de alguma forma, se relacionaram com esse universo e que fizeram e fazem do Brasil historicamente não só seu maior produtor, mas o segundo maior consumidor no mundo. Dom 10:00 - 17:00 Ter - Sáb 09:00 - 17:00 ",
                categoria: "cidade")
        p51.save(flush: true)


        def p52 = new Locais(valor: 6,
                cidade: "São Paulo", estado: "sp",
                nome: "Catavento Cultural e Educacional",url: "img52.jpg",
                descricao: "Criado com a vocação de ser um espaço interativo que apresente a ciência de forma instigante para crianças, jovens e adultos, desde sua inauguração em 2009 o Museu Catavento tem sido um grande fenômeno de público, tendo atingido a marca de dois milhões e meio de visitantes em apenas seis anos de operação, tendo sido o Museu mais visitado do Estado de São Paulo por três anos consecutivos.",
                categoria: "cidade")
        p52.save(flush: true)

        def p53 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu da Imagem e do Som",url: "img53.jpg",
                descricao: "O Museu da Imagem e do Som de São Pauloconta com um acervo de mais de 200 mil itens como fotografias, filmes, vídeos e cartazes. Além de exposições e mostras de cinema regulares, o MIS possui uma programação cultural diversificada voltada para todos os públicos e abre espaço para novos artistas, que, por meio de seleção, exibem seus trabalhos dentro de programas de  fotografia, cinema, dança e música.  ",
                categoria: "cidade")
        p53.save(flush: true)

        def p54 = new Locais(valor: 64,
                cidade: "Brotas", estado: "sp",
                nome: "CEU - Centro de Estudos do Universo",url: "img54.jpg",
                descricao: "As propostas de atividades abordam todos os ciclos do Ensino Fundamental e grande parte do Ensino Médio, principalmente nos eixos temáticos Terra e Universo, Vida e Ambiente e Tecnologia e Sociedade, adaptadas para suprir todas as necessidades do currículo escolar, atingindo assim todas as faixas etárias. ",
                categoria: "cidade")
        p54.save(flush: true)

        def p55 = new Locais(valor: 0,
                cidade: "Santos", estado: "sp",
                nome: "Museu Pelé",url: "img55.jpg",
                descricao: "Museu Pelé é um museu na cidade de Santos, dedicado à carreira do ex-jogador Pelé. Foi inaugurado em 15 de junho de 2014, como parte dos eventos paralelos à Copa do Mundo FIFA de 2014 ",
                categoria: "cidade")
        p55.save(flush: true)

        def p56 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu Paulista",url: "img56.jpg",
                descricao: "Museu Paulista, é o museu público mais antigo da cidade de São Paulo, cuja sede é um monumento-edifício que faz parte do conjunto arquitetônico do Parque da Independência. É o mais importante museu da Universidade de São Paulo e um dos mais visitados da capital paulista. ",
                categoria: "cidade")
        p56.save(flush: true)

        def p57 = new Locais(valor: 0,
                cidade: "São Roque", estado: "sp",
                nome: "Museu de Cera",url: "img57.jpg",
                descricao: "O Museu de Cera Alpino, em São Roque, encontra-se nas dependências do Hotel Alpino e conta com diversos personagens que marcaram a história do Brasil.",
                categoria: "cidade")
        p57.save(flush: true)

        def p58 = new Locais(valor: 0,
                cidade: "Taubaté", estado: "sp",
                nome: "Museu Monteiro Lobato",url: "img58.jpg",
                descricao: "O Museu Monteiro Lobato é uma instituição cultural pública c localizada no Sítio do Picapau Amarelo, na cidade brasileira de Taubaté, no Estado de São Paulo. É mantido pelo poder público estadual e é tombado como patrimônio histórico estadual e nacional.O museu funciona na casa onde o escritor Monteiro Lobato nasceu e viveu até os 12 anos.Abriga uma biblioteca infantil com as obras de Lobato, alguns acervos da família e área verde conhecida como Parque Sítio do Picapau Amarelo.",
                categoria: "cidade")
        p58.save(flush: true)

        def p59 = new Locais(valor: 0,
                cidade: "São Paulo", estado: "sp",
                nome: "Museu de Arte Moderna",url: "img59.jpg",
                descricao: "O Museu de Arte Moderna de São Paulo (MAM) é uma das mais importantes instituições culturais do Brasil. Localiza-se sob a marquise do Parque Ibirapuera, em São Paulo, em um edifício inserido no conjunto arquitetônico projetado por Oscar Niemeyer em 1954 e reformado por Lina Bo Bardi em 1982 para abrigar o museu.",
                categoria: "cidade")
        p59.save(flush: true)

        def p60 = new Locais(valor: 0,
                cidade: "Araraquara", estado: "sp",
                nome: "Museu de Arqueologia e Paleontologia",url: "img60.jpg",
                descricao: "O Mapa – Museu de Arqueologia e Paleontologia de Araraquara abre suas portas ao público durante a semana das 12h15 às 17h45, e aos sábados, das 9 às 12 horas.",
                categoria: "cidade")
        p60.save(flush: true)

        def p61 = new Locais(valor: 15,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Jardim Botânico",url: "img61.jpg",
                descricao: "O Jardim Botânico do Rio de Janeiro – JBRJ – foi fundado em 13 de junho de 1808. Ele surgiu de uma decisão do então príncipe regente português D. João de instalar no local uma fábrica de pólvora e um jardim para aclimatação de espécies vegetais originárias de outras partes do mundo. ",
                categoria: "cidade")
        p61.save(flush: true)

        def p62 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu do Índio",url: "img62.jpg",
                descricao: "O Museu do Índio, da Fundação Nacional do Índio - Funai, tem como objetivo contribuir para uma maior conscientização sobre a contemporaneidade e a importância das culturas indígenas. ",
                categoria: "cidade")
        p62.save(flush: true)

        def p63 = new Locais(valor: 80,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Pão de Açúcar",url: "img63.jpg",
                descricao: "O Pão de Açúcar é um complexo de morros localizado no bairro da Urca, na cidade do Rio de Janeiro, no Brasil.  Possui, como atração complementar, o passeio de teleférico, interligando a Praia Vermelha, o Morro da Urca e o Pão de Açúcar. Conhecido como Bondinho do Pão de Açúcar, o teleférico foi idealizado em 1908 e inaugurado em 1912, tornando-se o primeiro teleférico instalado no país e o terceiro do mundo. ",
                categoria: "cidade")
        p63.save(flush: true)

        def p64 = new Locais(valor: 30,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Corcovado - Cristo Redentor",url: "img64.jpg",
                descricao: "Cristo Redentor é uma estátua art déco que retrata Jesus Cristo, localizada no topo do morro do Corcovado, a 709 metros acima do nível do mar, no Parque Nacional da Tijuca, com vista para a maior parte da cidade do Rio de Janeiro, Brasil. Em 2007 foi eleito informalmente como uma das sete maravilhas do mundo moderno. Em 2012 a UNESCO considerou o Cristo Redentor como parte da paisagem do Rio de Janeiro incluída na lista de Patrimônios da Humanidade. ",
                categoria: "cidade")
        p64.save(flush: true)

        def p65 = new Locais(valor: 30,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Morro da Urca",url: "img65.jpg",
                descricao: "Cristo Redentor é uma estátua art déco que retrata Jesus Cristo, localizada no topo do morro do Corcovado, a 709 metros acima do nível do mar, no Parque Nacional da Tijuca, com vista para a maior parte da cidade do Rio de Janeiro, Brasil. Em 2007 foi eleito informalmente como uma das sete maravilhas do mundo moderno. Em 2012 a UNESCO considerou o Cristo Redentor como parte da paisagem do Rio de Janeiro incluída na lista de Patrimônios da Humanidade. ",
                categoria: "cidade")
        p65.save(flush: true)

        def p66 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Pedra Bonita",url: "img66.jpg",
                descricao: "A Pedra Bonita é um ponto turístico localizado no Parque Nacional da Tijuca, na cidade do Rio de Janeiro, mais precisamente entre a Pedra da Gávea e os bairros de São Conrado e Barra da Tijuca. ",
                categoria: "cidade")
        p66.save(flush: true)

        def p67 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Morro Dois Irmãos",url: "img67.jpg",
                descricao: "Morro Dois Irmãos é uma formação rochosa no bairro do Vidigal, no Rio de Janeiro. Seu topo com 533 metros de altitude acima do nível do mar é mais alto que o Pão de Açúcar, porém inferior ao Corcovado. ",
                categoria: "cidade")
        p67.save(flush: true)


        def p68 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Mosteiro De São Bento",url: "img68.jpg",
                descricao: "Fundado em 1590 por monges vindos da Bahia, o Mosteiro beneditino do Rio de Janeiro foi construído a pedido dos próprios habitantes da recém fundada cidade de São Sebastião.  ",
                categoria: "cidade")
        p68.save(flush: true)

        def p69 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Parque Lage",url: "img69.jpg",
                descricao: "Originário de um antigo engenho de açúcar, o parque faz parte da memória histórica da cidade. Em 1957, foi tombado pelo Instituto do Patrimônio Histórico e Artístico Nacional (IPHAN) como patrimônio histórico e cultural da cidade do Rio de Janeiro.",
                categoria: "cidade")
        p69.save(flush: true)

        def p71 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Mirante do Leblon",url: "img71.jpg",
                descricao: "Um grande deck de madeira, apoiado sobre rochas, que permite uma bela vista da orla oceânica do Bairro do Leblon e de Ipanema, assim como das pedras do caminho que vai para São Conrado. No local existe bar e local de pesca.",
                categoria: "cidade")
        p71.save(flush: true)

        def p72 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Arcos da Lapa",url: "img72.jpg",
                descricao: "Os Arcos da Lapa, localizam-se na região da Lapa, no bairro da Lapa, na Zona Central do município do Rio de Janeiro, no Brasil. Considerada como a obra arquitetônica de maior porte empreendida no Brasil durante o período colonial, é hoje um dos cartões postais da cidade, símbolo mais representativo do Rio de Janeiro Antigo preservado na região boêmia da Lapa.",
                categoria: "cidade")
        p72.save(flush: true)

        def p73 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Centro Cultural Banco do Brasil",url: "img73.jpg",
                descricao: "O Centro Cultural Banco do Brasil (CCBB) é uma rede de espaços culturais geridas e mantidas pelo Banco do Brasil, com o objetivo de disseminar a cultura pela população. Atualmente, encontra-se instalado em quatro capitais brasileiras: Rio de Janeiro, São Paulo, Belo Horizonte e Brasília.",
                categoria: "cidade")
        p73.save(flush: true)

        def p74 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Academia Brasileira de Letras",url: "img74.jpg",
                descricao: "Academia Brasileira de Letras é uma instituição literária brasileira fundada na cidade do Rio de Janeiro em 20 de julho de 1897 por escritores como Machado de Assis, Lúcio de Mendonça, Inglês de Sousa, Olavo Bilac, Afonso Celso, Graça Aranha, Medeiros e Albuquerque, Joaquim Nabuco, Teixeira de Melo, Visconde de Taunay e Ruy Barbosa. É composta por quarenta membros efetivos e perpétuos (por isso alcunhados imortais) e por vinte sócios estrangeiros. ",
                categoria: "cidade")
        p74.save(flush: true)

        def p75 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Palácio Guanabara",url: "img75.jpg",
                descricao: "O Palácio Guanabara localiza-se na Rua Pinheiro Machado (antiga Rua Guanabara), no bairro de Laranjeiras, na zona sul da cidade do Rio de Janeiro, capital do estado homônimo, no Brasil. É de estilo neoclássico. Construção iniciada pelo português José Machado Coelho em 1853, tendo sido utilizado como residência particular até a década de 1860. Reformado, tornou-se a residência da Princesa Isabel e seu esposo, o Conde d'Eu em 1865, sendo conhecido a partir de então como Paço Isabel.  ",
                categoria: "cidade")
        p75.save(flush: true)


        def p76 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu Casa de Rui Barbosa",url: "img76.jpg",
                descricao: "A Fundação terá como finalidade o desenvolvimento da cultura, da pesquisa e do ensino cumprindo-lhe, especialmente, a divulgação e o culto da obra e da vida de Rui Barbosa. A FCRB oferece um espaço reservado ao trabalho intelectual, à consulta de livros e documentos e à preservação da memória nacional. ",
                categoria: "cidade")
        p76.save(flush: true)

        def p77 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu do Amanhã",url: "img77.jpg",
                descricao: "O museu, projeto do arquiteto espanhol Santiago Calatrava, foi erguido ao lado da Praça Mauá, na zona portuária (mais precisamente no Píer Mauá). Sua construção teve o apoio da Fundação Roberto Marinho e teve o custo total de cerca de 230 milhões de reais. A proposta da instituição é ser um museu de artes e ciências, além de contar com mostras que alertam sobre os perigos das mudanças climáticas, da degradação ambiental e do colapso social. ",
                categoria: "cidade")
        p77.save(flush: true)

        def p78 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu de Arte Contemporânea",url: "img78.jpg",
                descricao: "O Museu de Arte Contemporânea de Niterói (MAC) é um museu de arte contemporânea brasileira localizado na cidade de Niterói, no Rio de Janeiro, no Brasil. A obra foi inaugurada no dia 2 de setembro de 1996. Projetado pelo arquiteto Oscar Niemeyer, o MAC tornou-se um dos cartões-postais de Niterói. ",
                categoria: "cidade")
        p78.save(flush: true)

        def p79 = new Locais(valor: 40,
                cidade: "Petrópolis", estado: "rj",
                nome: "Museu de Cera de Petrópolis",url: "img79.jpg",
                descricao: "O Museu de Cera de Petrópolis é o primeiro no Brasil com padrões artísticos internacionais de hiper-realismo, no qual personalidades nacionais e internacionais são retratadas com perfeição. As esculturas exibidas no MC são produzidas por estúdios americanos e ingleses. Elas retratam com perfeição a textura da pele, os fios de cabelo e até mesmo os olhos dos personagens. Todas foram produzidas em tamanho real. ",
                categoria: "cidade")
        p79.save(flush: true)

        def p80 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Museu Histórico do Exército / Forte de Copacabana",url: "img80.jpg",
                descricao: "O Forte de Copacabana localiza-se na ponta de Copacabana, ao final da praia e bairro de mesmo nome, na cidade do Rio de Janeiro, no Brasil. Oficialmente denominado como Museu Histórico do Exército / Forte de Copacabana (MHEx/FC), computa atualmente um fluxo de cerca de dez mil visitantes por mês, constituindo-se em um dos mais belos cartões-postais da cidade. O turista pode escolher entre a visita restrita (apenas às áreas externas) e a completa (incluindo o interior do forte e o Museu histórico-militar).  ",
        categoria: "cidade")
        p80.save(flush: true)

        def p81 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia de Ipanema",url: "img81.jpg",
                descricao: "Ipanema figura entre os principais pontos turísticos da cidade do Rio de Janeiro, tanto para turistas brasileiros quanto internacionais. Todo ano, turistas lotam o tradicional bairro e sua praia. ",
                categoria: "outros")
        p81.save(flush: true)

        def p82 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia de Copacabana",url: "img82.jpg",
                descricao: "Uma das praias mais conhecida e frequentada do Brasil no mundo. Se localiza na zona sul da cidade do Rio de Janeiro, onde tem-se uma grande diversidade de pessoas do Brasil e do mundo inteiro. Em sua orla existe um belíssimo calçadão com quiosques que servem diversas variedades de petiscos e bebidas. A parte da areia da praia é bem larga. ",
                categoria: "outros")
        p82.save(flush: true)

        def p83 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Leblon",url: "img83.jpg",
                descricao: "A frequência maior é de moradores que procuram se refrescar nas águas do mar. Portanto, não será surpresa se houver algumas celebridades em seus bares e calçadão.",
                categoria: "outros")
        p83.save(flush: true)

        def p84 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia da Barra da Tijuca",url: "img84.jpg",
                descricao: "Localizada na Barra da Tijuca, ao longo da Av. Sernambetiba, a Praia da Barra da Tijuca é a principal praia da Barra, e detém o título de maior praia do Rio de Janeiro com quase 18 km de extensão.  Os principais esportes praticados da praia são a pesca de beira, kitesurf, windsurf e flysurf, além de ser palco de muitos campeonatos.",
                categoria: "outros")
        p84.save(flush: true)

        def p85 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Leme",url: "img85.jpg",
                descricao: "Uma delícia de praia, apesar de ser continuação da praia de Copacabana, estando no Bairro do Leme leva o nome de Praia do Leme. E por isso também é uma parte da praia mais tranqüila, menos agitada, pois o bairro do Leme é um bairro mais pacato, menos conhecido.  O seu mar é realmente mais calmo e junto ao morro do Leme no final da praia existem muitos pescadores que aproveitam o recanto para sua pescaria, já que construíram por lá um local propício para eles pescarem, o Caminho dos Pescadores. ",
                categoria: "outros")
        p85.save(flush: true)

        def p86 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Praia do Vidigal",url: "img86.jpg",
                descricao: "Praia que fica na encosta do morro Dois Irmãos, próxima a comunidade do Vidigal e do Hotel Sheraton. A praia é pequena e quando a maré sobe quase não sobra areia para ficar, o mar por lá bate forte, mas existem dias de piscina também. Seus freqüentadores na sua maioria são os turistas que se hospedam no hotel e os moradores da comunidade. O acesso pelo hotel é fácil mais é restrito aos hóspedes, o outro acesso é mais complicado e escondido.",
                categoria: "outros")
        p86.save(flush: true)

        def p87 = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Feira Hippie de Ipanema",url: "img87.jpg",
                descricao: "Grande número de artesãos, artistas e comerciantes. Todo tipo de artigo e vestuário. Preços bem acessíveis. Dom 10:00 - 19:00.",
                categoria: "outros")
        p87.save(flush: true)

        def p88  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Parque Nacional da Tijuca",url: "img88.jpg",
                descricao: "Localizado no coração do Rio de Janeiro, com acesso pelas Zonas Norte, Sul e Oeste, somos o Parque Nacional mais visitado do Brasil, recebendo três milhões de visitantes por ano, entre brasileiros e estrangeiros de todas as idades. ",
                categoria: "outros")
        p88.save(flush: true)

        def p89  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Lagoa Rodrigo de Freitas",url: "img89.jpg",
                descricao: "Os passeios de pedalinhos ou pequenos botes movidos à pedal, estão entre as atrações de lazer encontrada às margens da Lagoa Rodrigo de Freitas. Acima o cais, onde é possivel alugar pedalinhos de 2 lugares ou até de 6 lugares. A vista da lagoa já é bonita vista das margens, mas ao passear e pedalar sobre as águas, é possivel observar novos angulos e interagir mais com a natureza. l.",
                categoria: "outros")
        p89.save(flush: true)

        def p90  = new Locais(valor: 0,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "Paço Imperial  ",url: "img90.jpg",
                descricao: "O Paço Imperial é um edifício colonial localizado na atual Praça XV de Novembro, no centro histórico da cidade do Rio de Janeiro, Brasil.Construído no século XVIII para residência dos governadores da Capitania do Rio de Janeiro, passou a ser a casa de despachos,sucessivamente, do Vice-Rei do Brasil, do Rei de Portugal Dom João VI e dos imperadores do Brasil. Atualmente é um centro cultural. Pela sua importância histórica e estética, o Paço Imperial é considerado o mais importante dos edifícios civis coloniais do Brasil. ",
        categoria: "outros")
        p90.save(flush: true)


        def p91  = new Locais(valor: 0,
                cidade: "Búzios", estado: "rj",
                nome: "Praia da Ferradura  ",url: "img91.jpg",
                descricao: "A Praia da Ferradura localiza-se na cidade de Búzios, no estado brasileiro do Rio de Janeiro. Praia de Enseada Tranquila e águas frias. Com bares no canto esquerdo da praia. ",
                categoria: "outros")
        p91.save(flush: true)

        def p92  = new Locais(valor: 0,
                cidade: "Paraty", estado: "rj",
                nome: "Praia do Cachadaço - Trindade  ",url: "img92.jpg",
                descricao: "Entre as praias mais importantes em Trindade encontram-se a Praia do Cepilho, onde o mar é propício a prática do surfe, a Praia de Fora, Praia do Rancho, onde os turistas podem se banhar calmamente, praticar mergulho e desfrutar ainda de bares e restaurantes, a Praia do Cachadaço (ou Caixa d'Aço), a Praia dos Pelados (praia de nudismo) acessível apenas por trilha ou barco e a praia do Meio mais utilizada para o turismo.",
                categoria: "outros")
        p92.save(flush: true)

        def p93  = new Locais(valor: 0,
                cidade: "Itacuruça", estado: "rj",
                nome: "Praia Grande ",url: "img93.jpg",
                descricao: "Considerada uma das praias mais frequentadas de Itacuruçá, durante a alta temporada o movimento de turistas é grande.  Com uma boa faixa de areia clara, possui mar tranquilo, ótimo para o banho. É cercada por grande área verde, como a maior parte das praias da cidade. Com boa infraestrutura, conta com quiosques e restaurantes, que servem diversos pratos e petiscos. O turista pode contar também com algumas pousadas localizadas no lugar. É um ótimo lugar para a família, que pode aproveitar um belo dia na praia com o conforto de ter tudo próximo",
                categoria: "outros")
        p93.save(flush: true)

        def p94  = new Locais(valor: 0,
                cidade: "Arraial do Cabo", estado: "rj",
                nome: "PRAIA DO FORNO ",url: "img94.jpg",
                descricao: "Localizada em uma enseada, a praia do Forno é acessível somente por barco ou trilha de 30 minutos. Além da mata preservada, das águas claras e dos corais, abriga ainda um restaurante flutuante especializado em ostras e mariscos. Fica a 1,5 quilômetro do Centro.",
                categoria: "outros")
        p94.save(flush: true)

        def p95  = new Locais(valor: 0,
                cidade: "Búzios", estado: "rj",
                nome: "Praia de Geribá ",url: "img95.jpg",
                descricao: "Talvez a praia mais conhecida de Búzios, com certeza a mais frequentada por famosos. Está localizada próxima ao centro de Búzios. O “agito” é frequente neste local, também por isso oferece as seus freqüentadores uma ótima infra-estrutura.  Uma linda praia de areias degrade que vai do alaranjado ao branco, o mar de águas agitadas e claras, sua orla é repleta de belas casas, bares, restaurantes e pousadas. ",
                categoria: "outros")
        p95.save(flush: true)

        def p96  = new Locais(valor: 0,
                cidade: "Cabo Frio", estado: "rj",
                nome: "Praia do Forte",url: "img96.jpg",
                descricao: "A Praia do Forte é uma das mais conhecidas e mais agitadas praias de Cabo Frio.Com um pouco mais de 7 Km de extensão total, a praia tem areias claras e mar tranquilo na maior parte de sua extensão. Porém, existem algumas áreas com mar mais agitado, que atrai muitos surrfistas para a região. ",
                categoria: "outros")
        p96.save(flush: true)

        def p97  = new Locais(valor: 0,
                cidade: "Cabo Frio", estado: "rj",
                nome: "Praia das Conchas",url: "img97.jpg",
                descricao: "Praia das Conchas é uma praia localizada no bairro do Peró na cidade de Cabo Frio, no estado brasileiro do Rio de Janeiro. Frequentada pelos aficionados pela pesca de arremesso. Os peixes mais capturados nesta praia são o badejo, a anchova e a tainha. O lugar oferece também uma bela vista das ilhas de Cabo Frio. Em toda sua orla existem quiosques, restaurantes e música ao vivo.",
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
                descricao: "Situada na Região Oceânica entre as praias de Itaipu e Piratininga, a Praia de Camboinhas se destaca por uma orla belíssima, com quiosques muito bem estruturados, calçadão e até uma aldeia indígena no seu canto esquerdo.",
                categoria: "outros")
        p99.save(flush: true)

        def p100  = new Locais(valor: 0,
                cidade: "Arraial do Cabo", estado: "rj",
                nome: "Praia dos Anjos",url: "img100.jpg",
                descricao: "O visual da Praia dos Anjos atrai pelo bucólico conjunto de barcos, onde durante todo o dia pescadores voltam do mar carregados de peixes para o mercado. A praia, apesar de não ser a mais indicada para banho, certamente está entre as mais importantes e movimentadas da região..",
                categoria: "outros")
        p100.save(flush: true)

        def p101  = new Locais(valor: 280,
                cidade: "ANGRA DOS REIS", estado: "rj",
                nome: "BATISMO DE MERGULHO EM ANGRA DOS REIS",url: "img101.jpg",
                descricao: "No batismo você terá a oportunidade de descobrir o fundo do mar acompanhado de um profissional qualificado  (um instrutor) que mergulhará contigo e vai te dar todas instruções necessárias e com segurança. A nossa saída de mergulho é sempre orientada para os melhores pontos de mergulho de Angra dos Reis e Ilha Grande.",
                categoria: "natureza")
        p101.save(flush: true)


        def p102  = new Locais(valor: 200,
                cidade: "Rio de Janeiro", estado: "rj",
                nome: "BATISMO DE ESCALADA FACE NORTE MORRO DA BABILÔNIA",url: "img102.jpg",
                descricao: "Ideal para iniciantes. Faremos o Batismo de Escalada na Face Norte do Morro da Babilônia na bairro da Urca, Rio de Janeiro. Uma escalada de até 30 metros com bela visão dos arredores da Urca, enseada da Praia Vermelha e da Praia de Botafogo. Todos os equipamentos de escalada e as fotos da atividade já estão inclusos. ",
                categoria: "natureza")
        p102.save(flush: true)

        def p103  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome: "Parque Nacional de Itatiaia",url: "img103.jpg",
                descricao: "O Parque Nacional de Itatiaia é uma unidade de conservação brasileira de proteção integral da natureza localizada no maciço do Itatiaia, na serra da Mantiqueira, entre os estados do Rio de Janeiro e Minas Gerais. Itatiaia é o parque nacional mais antigo do Brasil, tendo sido criado em 14 de junho de 1937,  que antes de ser adquirida pela Fazenda Federal, em 1908, pertenceu ao Visconde de Mauá.",
                categoria: "natureza")
        p103.save(flush: true)

        def p104  = new Locais(valor:0,
                cidade: "Itatiaia", estado: "rj",
                nome:" Cachoeira Véu de Noiva",url: "img104.jpg",
                descricao: "Cachoeira formada pelo Rio Maromba formando uma queda d'água de 40 m de altura, fica a 1 100 m de altitude..",
                categoria: "natureza")
        p104.save(flush: true)

        def p105  = new Locais(valor:0,
                cidade: "Nova Friburgo", estado: "rj",
                nome:" Cachoeira Poço Belo ",url: "img105.jpg",
                descricao: " A tranquilidade das vilas da região de colonização suíça e alemã, o clima agradável, as matas e as cachoeiras são os ingredientes mais que atrativos para nos levar a estes dois lugarejos. ",
                categoria: "natureza")
        p105.save(flush: true)

        def p106  = new Locais(valor:0,
                cidade: "Visconde de Mauá", estado: "rj",
                nome:" Vale do Alcantilado ",url: "img106.jpg",
                descricao: "O Vale do Alcantilado, em Visconde de Mauá é assim. Uma sequência de nove cachoeiras terminando com a mais alta de todas, para dar o toque final. Teremos uma saída de São Paulo no dia 22 de Janeiro para lá, em um roteiro de 4 dias por Visconde de Mauá.  O Vale do Alcantilado é uma propriedade particular localizada dentro do Sítio Cachoeiras do Alcantilado. Em 1992, o local foi adaptado à visitação, lazer e entretenimento, contribuindo para o desenvolvimento social e turístico da região. ",
                categoria: "natureza")
        p106.save(flush: true)

        def p107  = new Locais(valor:0,
                cidade: "Macabu", estado: "rj",
                nome:" Cachoeira da Amorosa ",url: "img107.jpg",
                descricao: "A Cachoeira da Amorosa é a mais famosa cachoeira do município de Conceição de Macabu. Uma boa opção para os fãs de cachoeiras, ela tem fácil acesso para carros, com estacionamento e alguns bares próximos.Amorosa é um desnivelamento do Rio Carogango, e tem dois saltos paralelos, com altura aproximada de 15 metros, que em época de maior incidência de chuvas podem se tornar um só. Os saltos formam uma piscina natural com pouca profundidade. Na proximidade existem diversos poços, que oferecem ótimas opções para banho, com águas limpas e uma bela natureza no entorno. ",
                categoria: "natureza")
        p107.save(flush: true)

        def p108  = new Locais(valor:0,
                cidade: "Paraty", estado: "rj",
                nome:" Cachoeira do Tobogã",url: "img108.jpg",
                descricao: "Enquanto a água corre pela pedra lisa da Cachoeira do Tobogã, moradores da região se preparam, em fila, para descer em alta velocidade. A cena está entre as mais divertidas para os turistas que visitam Paraty. ",
                categoria: "natureza")
        p108.save(flush: true)

        def p109  = new Locais(valor:0,
                cidade: "Penedo", estado: "rj",
                nome:" Três Cachoeiras",url: "img109.jpg",
                descricao: "Bastante frequentada nos finais de semana e feriados pela proximidade do Centro, é formada por três quedas que formam piscinas naturais boas para banho. ",
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
                descricao: "A Cachoeira da Feiticeira é de beleza espetacular e exalta a admiração de quem a visita, certamente é uma das melhores caminhadas em toda a Ilha Grande e roteiro certo de quem quer aproveitar ao máximo tudo o que a ilha tem para oferecer.",
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
                descricao: "A cachoeira é um tobogã natural , que foi esculpido pela natureza após uma forte tempestade (cabeça d´água) em 1966 , fez com que uma única placa de rocha faz o leito do rio , permitindo um deslizamento perfeito que desemboca numa piscina natural profunda de águas limpas e cristalinas .",
                categoria: "natureza")
        p113.save(flush: true)

        def p114  = new Locais(valor:0,
                cidade: "Cachoeiras de Macacu", estado: "rj",
                nome:" Cachoeira Poço Tenebroso",url: "img114.jpg",
                descricao: "Localizada em Cachoeiras de Macacu (RJ), é a mais bela queda d'água do município. Possui um salto com altura aproximada de 12m, seguida de uma ampla piscina natural, de formato retangular, que totaliza uma área de 200m2, de grande profundidade.",
                categoria: "natureza")
        p114.save(flush: true)

        def p115  = new Locais(valor:0,
                cidade: "Cachoeiras de Macacu", estado: "rj",
                nome:" Cachoeira Poço Tenebroso",url: "img115.jpg",
                descricao: "A Cachoeira da Pedra Branca está entre as mais movimentadas de Paraty. Por ser uma das mais tranquilas e belas da região, ela faz parte da rota comum de passeios de jeep.",
                categoria: "natureza")
        p115.save(flush: true)

        def p116  = new Locais(valor:0,
                cidade: "Conservatória", estado: "rj",
                nome:" Cachoeira da Índia",url: "img116.jpg",
                descricao: "A Cachoeira da Índia está situada no Balneário Municipal João Raposo. A cachoeira é uma pequena queda d´água com seu rio manso, chamada de Praia Sertaneja. Na cachoeira podemos encontrar a escultura de uma artista chamada Vilma Noel que resine no sul da França. A 'Índia', como chamamos carinhosamente, na verdade, não se trata de uma índia, e sim de uma entidade marinha que desconhecemos, a escultura é toda trabalhada em bronze e tamanho natural pesando 100 kg.",
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
                descricao: "Apesar de possuir rica bagagem histórica e diversos atrativos naturais,Rio do Ouro hoje é um município bastante humilde e muito pouco explorado turisticamente. ",
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
                descricao: "A Cachoeira do Iriri fica na altura do Km 158 da Rodovia Rio-Santos, sentido Paraty-Angra dos Reis, com entrada à esquerda. Próximo à Praia de São Gonçalo. Na frente há área para estacionamento. É área particular, mas pode-se entrar a pé. Trata-se de uma bela queda d'água que termina numa refrescante piscina. ",
                categoria: "natureza")
        p120.save(flush: true)


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
