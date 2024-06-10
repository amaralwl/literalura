package com.example.LiterAlura.Principal;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.DadosLivro;
import com.example.LiterAlura.model.Livro;
import com.example.LiterAlura.repository.AutorRepository;
import com.example.LiterAlura.repository.LivroRepository;
import com.example.LiterAlura.service.ConsumoApiGutendex;
import com.example.LiterAlura.service.ConverteDados;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoApiGutendex consumo = new ConsumoApiGutendex();
    private ConverteDados conversor = new ConverteDados();

    private LivroRepository livroRepositorio;
    private AutorRepository autorRepositorio;

    public Principal(LivroRepository livroRepositorio, AutorRepository autorRepositorio) {
        this.livroRepositorio = livroRepositorio;
        this.autorRepositorio = autorRepositorio;
    }

    public void exibeMenu() {
        var menu = """
                Escolha o número de sua opção:
                1- buscar livro pelo título
                2- listar livros registrados
                3- listar autores registrados
                4- listar autores vivos em um determinado ano
                5- listar livros em um determinado idioma
                                
                0- sair
                """;
        var opcao = -1;
        while (opcao != 0) {
            System.out.println(menu);
            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivroPeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosPorAno();
                    break;
                case 5:
                    listarLivrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void buscarLivroPeloTitulo() {
        System.out.println("Insira o título do livro que você deseja procurar: ");
        var titulo = sc.nextLine();

        String json = consumo.obterDados(titulo);
        DadosLivro dados = conversor.obterDados(json, DadosLivro.class);
        if (dados == null) {
            System.out.println("\nTítulo não encontrado!\n");
            return;
        }
        Livro livro = new Livro(dados);

        Autor autorExistente = autorRepositorio.findByNome(dados.autores().get(0).nome());

        if (autorExistente != null) {
            livro.setAutor(autorExistente);
        } else {
            Autor autor = new Autor(dados.autores().get(0).nome(), dados.autores().get(0).anoDeNascimento(), dados.autores().get(0).anoDeFalecimento());
            autorRepositorio.save(autor);
            livro.setAutor(autor);
        }
        try{
            livroRepositorio.save(livro);
            System.out.println(livro);
        }catch(DataIntegrityViolationException exception) {
            System.out.println("\nLivro já cadastrado, por favor insira um outro livro.\n");
        }


    }

    private void listarLivrosRegistrados() {
        livroRepositorio.findAll().forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        autorRepositorio.findAll().forEach(System.out::println);
    }

    private void listarAutoresVivosPorAno() {
        System.out.println("Insira o ano que deseja pesquisar: ");
        var anoPesquisa = sc.nextInt();
        sc.nextLine();
        List<Autor> autoresVivos = autorRepositorio.buscarAutoresVivosEmAno(anoPesquisa);
        if (autoresVivos.isEmpty()) {
            System.out.println("\nNenhum autor vivo encontrado!\n");
        } else {
            autoresVivos.forEach(System.out::println);
        }
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                es - espanhol
                en - inglês
                fr - francês
                pt - português
                """);
        var idiomaPesquisa = sc.nextLine();
        List<Livro> livrosPorIdioma = livroRepositorio.buscarLivrosPorIdioma(idiomaPesquisa);
        if (livrosPorIdioma.isEmpty()) {
            System.out.println("\nNão foram encontrados livros no idioma informado.\n");
        } else {
            livrosPorIdioma.forEach(System.out::println);
        }
    }


}
