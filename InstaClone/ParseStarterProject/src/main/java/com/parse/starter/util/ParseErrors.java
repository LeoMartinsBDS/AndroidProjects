package com.parse.starter.util;

import java.util.HashMap;

public class ParseErrors {

    private HashMap<Integer, String> erros;

    public ParseErrors() {
        this.erros = new HashMap<>();
        this.erros.put(201, "Senha não foi preenchida!");
        this.erros.put(202, "Usuário já existe, utilize outro nome de usuário!");
    }

    public String getErro(int codErro){
        return this.erros.get(codErro);
    }

}
