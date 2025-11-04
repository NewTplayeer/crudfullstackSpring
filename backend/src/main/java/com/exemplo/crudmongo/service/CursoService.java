package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Serviço responsável pela lógica de negócio relacionada à entidade Curso.
 */
@Service
public class CursoService {

    private final CursoRepository repository;

    public CursoService(CursoRepository repository) {
        this.repository = repository;
    }

    /** Retorna todos os cursos cadastrados. */
    public List<Curso> listarTodos() {
        return repository.findAll();
    }

    /** Busca um curso pelo ID. */
    public Optional<Curso> buscarPorId(@NonNull Long id) {
        return repository.findById(id);
    }

    /** Salva um novo curso. */
    public Curso salvar(@NonNull Curso curso) {
        return repository.save(curso);
    }

    /** Atualiza um curso existente pelo ID. */
    public Curso atualizar(@NonNull Long id, @NonNull Curso novoCurso) {
        return repository.findById(id)
                .map(c -> {
                    c.setNome(novoCurso.getNome());
                    c.setCargaHoraria(novoCurso.getCargaHoraria());
                    c.setAtivo(novoCurso.isAtivo());
                    return repository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Curso não encontrado"));
    }

    /** Exclui um curso pelo ID. */
    public void excluir(@NonNull Long id) {
        repository.deleteById(id);
    }
}