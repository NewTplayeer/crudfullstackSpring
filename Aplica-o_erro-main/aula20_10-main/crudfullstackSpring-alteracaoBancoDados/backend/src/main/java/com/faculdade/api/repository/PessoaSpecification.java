package com.faculdade.api.repository;

import com.faculdade.api.model.Pessoa;
import com.faculdade.api.model.Curso;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class PessoaSpecification {

    public static Specification<Pessoa> comNome(String nome) {
        if (!StringUtils.hasText(nome)) return null;
        return (root, query, cb) -> cb.like(cb.upper(root.get("nome")), "%" + nome.toUpperCase() + "%");
    }

    public static Specification<Pessoa> matriculadaEmCurso(String cursoNome) {
        if (!StringUtils.hasText(cursoNome)) return null;
        return (root, query, cb) -> {
            Join<Pessoa, Curso> cursoJoin = root.join("cursos");
            return cb.like(cb.upper(cursoJoin.get("nome")), "%" + cursoNome.toUpperCase() + "%");
        };
    }

    public static Specification<Pessoa> comIdadeEntre(Integer idadeMin, Integer idadeMax) {
        return (root, query, cb) -> {
            if (idadeMin != null && idadeMax != null) {
                return cb.between(root.get("idade"), idadeMin, idadeMax);
            } else if (idadeMin != null) {
                return cb.greaterThanOrEqualTo(root.get("idade"), idadeMin);
            } else if (idadeMax != null) {
                return cb.lessThanOrEqualTo(root.get("idade"), idadeMax);
            }
            return null;
        };
    }
}