package com.exemplo.crudmongo.controller;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @PostMapping
    public Curso criarCurso(@RequestBody Curso curso) {
        return cursoService.criar(curso);
    }

    @GetMapping
    public List<Curso> listarCursos() {
        return cursoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCursoPorId(@PathVariable Long id) {
        return cursoService.buscarPorId(id)
                .map(curso -> ResponseEntity.ok(curso))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> atualizarCurso(@PathVariable Long id, @RequestBody Curso detalhesCurso) {
        
        return cursoService.atualizar(id, detalhesCurso)
                .map(cursoAtualizado -> ResponseEntity.ok(cursoAtualizado))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCurso(@PathVariable Long id) {
        
        if (cursoService.deletar(id)) {
            return ResponseEntity.ok().<Void>build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}