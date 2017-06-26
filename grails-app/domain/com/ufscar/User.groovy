package com.ufscar

class User {

    String nome;
    String senha;
    int parques =0;
    int museus=0;
    int dinheiro=0;
    static hasMany = [locais: Locais]
    static constraints = {

    }
}
