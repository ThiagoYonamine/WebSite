package com.ufscar

class User {

    String nome;
    String senha;
    float natureza =2.1
    float cidade=2.1
    float outros=2.1
    int dinheiro=0
    String estado= "todos"
    static hasMany = [locais: Locais]
    static constraints = {

    }
}
