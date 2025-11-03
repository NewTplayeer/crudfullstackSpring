package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.service.PessoaService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid; 

import java.util.List;

@RestController 
@RequestMapping("/api/pessoas") 
@CrossOrigin(origins = "*") 
public class PessoaController {

    private final PessoaService service; 

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping
    public List<Pessoa> listarPessoas() {
        return service.listarTodos();
    }
    
    @GetMapping("/{id}")
    public Pessoa listarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/buscarNome")
    public List<Pessoa> listarPorNome(@RequestParam String nome) {
        return service.buscarPorNome(nome);
    }

    @GetMapping("/buscarIdade")
    public List<Pessoa> listarPorIdade(@RequestParam int idade) {
        return service.buscarPorIdade(idade);
    }

    @GetMapping("/buscarNomeIdade")
    public List<Pessoa> listarPorNomeIdade(@RequestParam String nome, @RequestParam int idade) {
        return service.buscarPorNomeIdade(nome, idade);
    }


    @GetMapping("/pagina")
    public Page<Pessoa> listarPaginado(Pageable pageable) {
        return service.listarTodosPaginado(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa criarPessoa(@RequestBody @Valid Pessoa pessoa) {
        return service.salvar(pessoa);
    }

    @PutMapping("/{id}")
    public Pessoa atualizarPessoa(@PathVariable Long id, @RequestBody @Valid Pessoa pessoa) {
        return service.atualizar(id, pessoa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPessoa(@PathVariable Long id) {
        service.excluir(id);
    }
}
