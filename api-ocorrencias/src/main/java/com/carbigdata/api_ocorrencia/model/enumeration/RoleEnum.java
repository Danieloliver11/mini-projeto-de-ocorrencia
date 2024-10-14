package com.carbigdata.api_ocorrencia.model.enumeration;

public enum RoleEnum {
    ROLE_USUARIO("ROLE_USUARIO"),
    ROLE_ADM("ROLE_ADM");

    private final String valor;

    RoleEnum(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

  
}
