package com.faculdade.api.service;

import com.faculdade.api.model.Curso;
import com.faculdade.api.repository.CursoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    public Curso salvar(Curso curso) {
        return repository.save(curso);
    }

    public Curso atualizar(Long id, Curso dadosAtualizados) {
        return repository.findById(id)
                .map(curso -> {
                    curso.setNome(dadosAtualizados.getNome());
                    curso.setCargaHoraria(dadosAtualizados.getCargaHoraria());
                    curso.setAtivo(dadosAtualizados.getAtivo());
                    return repository.save(curso);
                })
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
