package com.example.LiterAlura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Autor autor;
    private String idioma;
    private Integer numeroDownloads;

    public Livro() {
    }

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        this.idioma = dados.idiomas().get(0);
        this.numeroDownloads = dados.numeroDownloads();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDownloads() {
        return numeroDownloads;
    }

    public void setNumeroDownloads(Integer numeroDownloads) {
        this.numeroDownloads = numeroDownloads;
    }

    @Override
    public String toString() {
        return "\n-------- LIVRO --------\n" +
                "Título: " + titulo +
                "\nAutor: " + autor.getNome() +
                "\nIdioma: " + idioma +
                "\nNúmero de downloads: " + numeroDownloads +
                "\n-----------------------\n";
    }
}
