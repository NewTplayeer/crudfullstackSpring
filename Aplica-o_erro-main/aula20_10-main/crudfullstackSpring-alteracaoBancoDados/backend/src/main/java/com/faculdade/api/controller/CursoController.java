package com.faculdade.api.controller;

import com.faculdade.api.model.Curso;
import com.faculdade.api.service.CursoService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService service;

    public CursoController(CursoService service) {
        this.service = service;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ROLE_ALUNO', 'ROLE_COORDENADOR')")
    public List<Curso> listar() {
        return service.listarTodos();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public ResponseEntity<Curso> criar(@RequestBody Curso curso) {
        return ResponseEntity.ok(service.salvar(curso));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public ResponseEntity<Curso> atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        return ResponseEntity.ok(service.atualizar(id, curso));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
