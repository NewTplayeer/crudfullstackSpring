package com.faculdade.api.controller;

import com.faculdade.api.dto.RelatorioResumoDTO;
import com.faculdade.api.model.Pessoa;
import com.faculdade.api.service.PessoaService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaController {

    private final PessoaService service;

    public PessoaController(PessoaService service) {
        this.service = service;
    }

    @GetMapping("/curso")
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public ResponseEntity<List<Pessoa>> buscarPorCurso(@RequestParam("valor") String curso) {
        List<Pessoa> pessoas = service.buscarPorCurso(curso);
        if (pessoas.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping("/pesquisa")
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public Page<Pessoa> pesquisar(
        @RequestParam(required = false) String nome,
        @RequestParam(required = false) String curso,
        @RequestParam(required = false) Integer idadeMin,
        @RequestParam(required = false) Integer idadeMax,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        return service.pesquisar(nome, curso, idadeMin, idadeMax, page, size);
    }

    @GetMapping("/relatorio")
    @PreAuthorize("hasAuthority('ROLE_COORDENADOR')")
    public RelatorioResumoDTO gerarRelatorio() {
        return service.gerarRelatorioResumo();
    }
}