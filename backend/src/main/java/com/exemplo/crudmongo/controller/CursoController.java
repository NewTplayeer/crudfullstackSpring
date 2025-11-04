package com.exemplo.crudmongo.controller;

// Importa o modelo "Curso", que representa a entidade (tabela/coleção) de cursos
import com.exemplo.crudmongo.Model.Curso;

// Importa o serviço responsável pela lógica de negócio da aplicação
import com.exemplo.crudmongo.service.CursoService;

// Importações do Spring Framework usadas para criar e configurar o controlador REST
import org.springframework.web.bind.annotation.*;

// Importação para usar controle de acesso baseado em permissões (Spring Security)
import org.springframework.security.access.prepost.PreAuthorize;

// Importação para trabalhar com respostas HTTP padronizadas (status + corpo)
import org.springframework.http.ResponseEntity;

// Importação de lista (coleção de objetos)
import java.util.List;

/**
 * Classe controladora responsável por gerenciar as requisições HTTP
 * relacionadas à entidade Curso.
 *
 * Essa classe atua como intermediária entre o front-end (cliente)
 * e a camada de serviço (lógica de negócio).
 */
@RestController // Indica que esta classe é um controlador REST (retorna JSON por padrão)
@RequestMapping("/api/cursos") // Define o caminho base da API (ex: localhost:8080/api/cursos)
@CrossOrigin(origins = "*") // Permite requisições de qualquer origem (libera o CORS)
public class CursoController {

    // Dependência do serviço de cursos (injeção de dependência do Spring)
    private final CursoService service;

    /**
     * Construtor que injeta a dependência do serviço de cursos.
     * O Spring Boot automaticamente fornece uma instância de CursoService.
     */
    public CursoController(CursoService service) {
        this.service = service;
    }

    /**
     * Método que lista todos os cursos cadastrados.
     * É acessado via requisição HTTP GET em /api/cursos.
     *
     * A anotação @PreAuthorize garante que somente usuários com os papéis
     * "ALUNO" ou "COORDENADOR" podem acessar esse endpoint.
     *
     * @return Uma lista de objetos Curso.
     */
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    @GetMapping
    public List<Curso> listar() {
        // Chama o serviço para buscar todos os cursos no banco de dados
        return service.listarTodos();
    }

    /**
     * Busca um curso específico pelo ID.
     * É acessado via GET em /api/cursos/{id}.
     *
     * @PathVariable indica que o valor do ID virá diretamente da URL.
     * @return ResponseEntity contendo o curso encontrado (status 200)
     *         ou erro 404 se o curso não existir.
     */
    @PreAuthorize("hasAnyRole('ALUNO', 'COORDENADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<Curso> buscarPorId(@PathVariable Long id) {
        // Chama o serviço para buscar o curso e trata se o ID existir ou não
        return service.buscarPorId(id)
                // Se o curso existir, retorna status 200 (OK) com o curso no corpo da resposta
                .map(ResponseEntity::ok)
                // Caso não exista, retorna status 404 (Not Found)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo curso no banco de dados.
     * É acessado via POST em /api/cursos.
     *
     * Somente o usuário com papel "COORDENADOR" pode criar cursos.
     *
     * @param curso Objeto Curso enviado no corpo da requisição (JSON)
     * @return O curso criado e salvo no banco de dados.
     */
    @PreAuthorize("hasRole('COORDENADOR')")
    @PostMapping
    public Curso criar(@RequestBody Curso curso) {
        // Chama o serviço para salvar o novo curso e retorna o objeto salvo
        return service.salvar(curso);
    }

    /**
     * Atualiza os dados de um curso existente.
     * É acessado via PUT em /api/cursos/{id}.
     *
     * @param id    ID do curso que será atualizado.
     * @param curso Objeto com os novos dados do curso.
     * @return Curso atualizado após a persistência no banco.
     */
    @PreAuthorize("hasRole('COORDENADOR')")
    @PutMapping("/{id}")
    public Curso atualizar(@PathVariable Long id, @RequestBody Curso curso) {
        // Chama o serviço que busca o curso pelo ID e aplica as atualizações
        return service.atualizar(id, curso);
    }

    /**
     * Exclui um curso existente do banco de dados.
     * É acessado via DELETE em /api/cursos/{id}.
     *
     * @param id ID do curso a ser excluído.
     * 
     *           Este método não retorna nada (void) — apenas executa a exclusão.
     *           Caso o ID não exista, a exceção é tratada no serviço.
     */
    @PreAuthorize("hasRole('COORDENADOR')")
    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        // Chama o serviço para excluir o curso com o ID informado
        service.excluir(id);
    }
}