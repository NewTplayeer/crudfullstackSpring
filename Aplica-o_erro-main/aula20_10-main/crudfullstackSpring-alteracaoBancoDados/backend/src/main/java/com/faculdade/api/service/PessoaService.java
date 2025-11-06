package com.faculdade.api.service;

import com.faculdade.api.model.Pessoa;
import com.faculdade.api.repository.PessoaRepository;
import com.faculdade.api.repository.PessoaSpecification;
import com.faculdade.api.dto.RelatorioResumoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> buscarPorCurso(String curso) {
        if (curso == null || curso.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do curso não pode ser vazio.");
        }
        return repository.findByCursoNomeContaining(curso);
    }

    public Page<Pessoa> pesquisar(String nome, String curso, Integer idadeMin, Integer idadeMax, int page, int size) {
        Specification<Pessoa> spec = Specification.where(PessoaSpecification.comNome(nome))
            .and(PessoaSpecification.matriculadaEmCurso(curso))
            .and(PessoaSpecification.comIdadeEntre(idadeMin, idadeMax));

        PageRequest pageable = PageRequest.of(page, size, Sort.by("nome").ascending());

        return repository.findAll(spec, pageable);
    }

    public RelatorioResumoDTO gerarRelatorioResumo() {
        RelatorioResumoDTO relatorio = new RelatorioResumoDTO();
        relatorio.setTotalPessoasCadastradas(repository.count());

        Map<String, Long> totalPorCurso = new HashMap<>();
        repository.countPessoasByCurso().forEach(obj -> totalPorCurso.put((String) obj[0], (Long) obj[1]));
        relatorio.setTotalPorCurso(totalPorCurso);

        Map<String, Double> mediaIdadePorCurso = new HashMap<>();
        repository.avgIdadeByCurso().forEach(obj -> mediaIdadePorCurso.put((String) obj[0], (Double) obj[1]));
        relatorio.setMediaIdadePorCurso(mediaIdadePorCurso);

        return relatorio;
    }
}