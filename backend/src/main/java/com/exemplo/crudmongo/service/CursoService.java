package com.exemplo.crudmongo.service;
import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CursoService {
    
    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Curso salvar(Curso novoCurso) {
        return repository.save(novoCurso);
    }

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Curso buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Curso não encontrado com ID: " + id));
    }

    @Transactional
    public Curso atualizar(Long id, Curso novoCurso) {
        return repository.findById(id).map(c -> {
            c.setNome(novoCurso.getNome());
            c.setCargaHoraria(novoCurso.getCargaHoraria());
            c.setAtivo(novoCurso.isAtivo());
            return repository.save(c);
        }).orElseThrow(() -> new RuntimeException("Curso não encontrado com id: " + id));
    }
 
    @Transactional
    public void excluir(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Curso não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

}
