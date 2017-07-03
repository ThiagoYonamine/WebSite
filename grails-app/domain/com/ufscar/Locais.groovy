package com.ufscar

class Locais {
    Integer valor
    String cidade
    String estado
    String nome
    String url
    String descricao
    String categoria
    static mapping = {

        descricao sqlType: 'longText'
    }
    static constraints = {
    }
}
