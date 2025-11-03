package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Pessoa;
import com.exemplo.crudmongo.repository.PessoaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service 
public class PessoaService {

    private final PessoaRepository repository; 
    
    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listarTodos() {
        return repository.findAll();
    }
    
    public Pessoa buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    public List<Pessoa> buscarPorNome(String nome) {
        return repository.findByNome(nome);
    }
    
    public List<Pessoa> buscarPorIdade(int idade) {
        return repository.findByIdade(idade);
    }
    
    public Page<Pessoa> listarTodosPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }
    
    public List<Pessoa> buscarPorNomeIdade(String nome, int idade) {
        return repository.findByNomeContainingIgnoreCaseAndIdade(nome, idade);
    }
    
    @Transactional
    public Pessoa salvar(Pessoa pessoa) {
        return repository.save(pessoa);
    }
    
    @Transactional
    public Pessoa atualizar(Long id,  Pessoa novaPessoa) {
        return repository.findById(id).map(p -> {
            p.setNome(novaPessoa.getNome());
            p.setIdade(novaPessoa.getIdade());
            return repository.save(p);
        }).orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
    }

    @Transactional
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
