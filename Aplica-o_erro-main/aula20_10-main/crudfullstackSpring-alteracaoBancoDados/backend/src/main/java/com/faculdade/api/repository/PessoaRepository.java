package com.faculdade.api.repository;

import com.faculdade.api.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, JpaSpecificationExecutor<Pessoa> {

    @Query("SELECT p FROM Pessoa p JOIN p.cursos c WHERE UPPER(c.nome) LIKE UPPER(CONCAT('%', :cursoNome, '%'))")
    List<Pessoa> findByCursoNomeContaining(@Param("cursoNome") String cursoNome);

    @Query("SELECT c.nome, COUNT(p.id) FROM Pessoa p JOIN p.cursos c GROUP BY c.nome")
    List<Object[]> countPessoasByCurso();

    @Query("SELECT c.nome, AVG(p.idade) FROM Pessoa p JOIN p.cursos c GROUP BY c.nome")
    List<Object[]> avgIdadeByCurso();
}