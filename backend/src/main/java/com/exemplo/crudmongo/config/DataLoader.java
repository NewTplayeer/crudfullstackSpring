package com.exemplo.crudmongo.config;

import com.exemplo.crudmongo.Model.Curso;
import com.exemplo.crudmongo.repository.CursoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initDatabase(CursoRepository cursoRepository) {
        return args -> {
            if (cursoRepository.count() == 0) {
                cursoRepository.save(new Curso("Engenharia de Software", 360, true));
                cursoRepository.save(new Curso("Medicina", 400, true));
                cursoRepository.save(new Curso("Direito", 300, false));
            }
        };
    }
}
