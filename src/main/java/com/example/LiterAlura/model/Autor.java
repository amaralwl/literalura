package com.example.LiterAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Integer anoDeNascimento;
    private Integer anoDeFalecimento;
    @OneToMany(mappedBy = "autor", fetch = FetchType.EAGER)
    private List<Livro> livros = new ArrayList<>();

    public Autor() {
    }

    public Autor(String nome, Integer anoDeNascimento, Integer anoDeFalecimento) {
        this.nome = nome;
        this.anoDeNascimento = anoDeNascimento;
        this.anoDeFalecimento = anoDeFalecimento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoDeNascimento() {
        return anoDeNascimento;
    }

    public void setAnoDeNascimento(Integer anoDeNascimento) {
        this.anoDeNascimento = anoDeNascimento;
    }

    public Integer getAnoDeFalecimento() {
        return anoDeFalecimento;
    }

    public void setAnoDeFalecimento(Integer anoDeFalecimento) {
        this.anoDeFalecimento = anoDeFalecimento;
    }

    @Override
    public String toString() {
        return "\nAutor: " + nome +
                "\nAno de nascimento: " + anoDeNascimento +
                "\nAno de falecimento: " + anoDeFalecimento +
                "\nLivros: " + String.join(", ", livros.stream().map(Livro::getTitulo).toList()) +
                "\n";
    }

    public void adicionarLivro(Livro livro) {
        this.livros.add(livro);
    }
}
