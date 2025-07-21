package com.conectapet.dto;

import com.conectapet.model.PetSize;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class PetRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String nome;
    
    @NotBlank(message = "Tipo de animal é obrigatório")
    private String tipoAnimal;
    
    @NotBlank(message = "Raça é obrigatória")
    private String raca;
    
    @NotNull(message = "Idade é obrigatória")
    @Min(value = 0, message = "Idade deve ser maior ou igual a 0")
    private Integer idade;
    
    @NotNull(message = "Porte é obrigatório")
    private PetSize porte;
    
    @NotBlank(message = "Cor é obrigatória")
    private String cor;
    
    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 20, max = 500, message = "Descrição deve ter entre 20 e 500 caracteres")
    private String descricao;
    
    private List<String> imagens;
    
    // Construtores
    public PetRequest() {}
    
    public PetRequest(String nome, String tipoAnimal, String raca, Integer idade, PetSize porte, String cor, String descricao) {
        this.nome = nome;
        this.tipoAnimal = tipoAnimal;
        this.raca = raca;
        this.idade = idade;
        this.porte = porte;
        this.cor = cor;
        this.descricao = descricao;
    }
    
    // Getters e Setters
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getTipoAnimal() { return tipoAnimal; }
    public void setTipoAnimal(String tipoAnimal) { this.tipoAnimal = tipoAnimal; }
    
    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }
    
    public Integer getIdade() { return idade; }
    public void setIdade(Integer idade) { this.idade = idade; }
    
    public PetSize getPorte() { return porte; }
    public void setPorte(PetSize porte) { this.porte = porte; }
    
    public String getCor() { return cor; }
    public void setCor(String cor) { this.cor = cor; }
    
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    
    public List<String> getImagens() { return imagens; }
    public void setImagens(List<String> imagens) { this.imagens = imagens; }
}