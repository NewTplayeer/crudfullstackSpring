package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Curso;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {
    
}
