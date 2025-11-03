package com.exemplo.crudmongo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;


@Entity  
@Table(name = "pessoas") 
public class Pessoa {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id ;
    private String nome;
    private int idade;

    public Pessoa() {
    } 

    // Getters 
    public Long getId() { return id; }
    public int getIdade() { return idade; }
    public String getNome() { return nome; }

    // Setters 
    public void setId(Long id) {
        this.id = id;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
}
