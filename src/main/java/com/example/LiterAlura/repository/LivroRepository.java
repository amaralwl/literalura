package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    @Query("SELECT l FROM Livro l WHERE l.idioma ILIKE :idiomaEscolha")
    List<Livro> buscarLivrosPorIdioma(String idiomaEscolha);
}
