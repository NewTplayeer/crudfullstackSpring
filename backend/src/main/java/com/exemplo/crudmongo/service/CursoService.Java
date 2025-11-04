package com.exemplo.crudmongo.service;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    public List<Curso> listarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    public Curso criar(Curso curso) {
        return cursoRepository.save(curso);
    }

    public Optional<Curso> atualizar(Long id, Curso detalhesCurso) {
        return cursoRepository.findById(id)
                .map(cursoExistente -> {
                    cursoExistente.setNome(detalhesCurso.getNome());
                    cursoExistente.setCargaHoraria(detalhesCurso.getCargaHoraria());
                    cursoExistente.setAtivo(detalhesCurso.isAtivo());
                    return cursoRepository.save(cursoExistente);
                });
    }

    public boolean deletar(Long id) {
        return cursoRepository.findById(id)
                .map(curso -> {
                    cursoRepository.delete(curso);
                    return true;
                })
                .orElse(false);
    }
}