package com.conectapet.model;

public enum AdoptionStatus {
    DISPONIVEL("Disponível"),
    EM_PROCESSO("Em Processo"),
    ADOTADO("Adotado"),
    INDISPONIVEL("Indisponível");
    
    private final String displayName;
    
    AdoptionStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}