# 📚 ANÁLISE TÉCNICA E DETALHAMENTO DO PROJETO FINAL

**Autor:** Mateus Lima

**Tecnologia:** Spring Boot 3.2.5 (Java 17)

**Foco:** Implementação de regras de negócio complexas e controle de acesso (Role-Based Authorization).

---

## 1. 🎯 OBJETIVOS CUMPRIDOS E ARQUITETURA

O projeto final consolida a API em dois módulos principais (`Pessoa` e `Curso`), cumprindo a separação de responsabilidades (Controller -> Service -> Repository) e as regras de segurança exigidas.

### 1.1. Estrutura e Separação de Responsabilidades

| Camada | Função | Detalhe no Código |
| :--- | :--- | :--- |
| **Controller** | Lida com HTTP e JSON. | Mapeia rotas (`@RequestMapping`), recebe dados (`@RequestBody`) e faz a validação (`@Valid`). |
| **Service** | Contém a LÓGICA DE NEGÓCIO. | Responsável por transações (`@Transactional`) e tratamento de erros (`orElseThrow`). |
| **Repository** | Acesso a Dados. | Utiliza **Spring Data JPA** para gerar o CRUD e consultas personalizadas automaticamente (Ex: `findByNome...`). |
| **SecurityConfig** | Segurança e Permissões. | Define as `ROLEs` e o controle de acesso por endpoint. |

### 1.2. Módulo Pessoa: Funcionalidades Estendidas (10 Requisitos)

O módulo `Pessoa` demonstra 10 funcionalidades diferentes, superando o CRUD básico:

| # | Funcionalidade | Endpoint (URL) | Tipo de Implementação |
| :--- | :--- | :--- | :--- |
| 1-4 | CRUD Básico (C, U, D) | `POST`, `PUT`, `DELETE /api/pessoas` | Implementado no `Service` com `@Transactional`. |
| 5 | Leitura (Todos) | `GET /api/pessoas` | `repository.findAll()`. |
| 6 | Buscar por Nome | `GET /api/pessoas/buscarNome?nome=X` | **Derived Query** (`findByNome`). |
| 7 | Buscar por Idade | `GET /api/pessoas/buscarIdade?idade=X` | **Derived Query** (`findByIdade`). |
| 8 | Busca Combinada | `GET /api/pessoas/buscarNomeIdade?nome=X&idade=Y` | **Derived Query** com operador `AND` (`findByNome...AndIdade`). |
| 9 | Paginação | `GET /api/pessoas/pagina?page=X&size=Y` | Recebe objeto `Pageable` no `Controller` e passa para `repository.findAll()`. |
| 10 | Validação de Entrada | `POST`, `PUT /api/pessoas` | Uso de `@Valid` no Controller e anotações como `@NotBlank` na Entidade. |

---

## 2. 🛡️ DETALHE DA SEGURANÇA (Módulo Curso)

A parte mais crítica do projeto é o controle de acesso do `Curso`, configurado em `SecurityConfig.java`.

### 2.1. Controle de Permissões (Role-Based Authorization)

As regras de acesso foram aplicadas para o endpoint `/api/cursos/**`:

* **Leitura (GET):** Permitida para **`ROLE_ALUNO`** e **`ROLE_COORDENADOR`** (`.hasAnyRole(...)`).
* **Modificação (POST, PUT, DELETE):** Restrita apenas ao **`ROLE_COORDENADOR`** (`.hasRole(...)`).
* **API de Pessoa:** Deixada aberta (`.permitAll()`) para permitir testes fáceis das 10 funcionalidades.

### 2.2. Usuários e Criptografia

* **Usuários:** Definidos em memória (`InMemoryUserDetailsManager`) para o teste: `coordenador` e `aluno`.
* **Segurança:** Utilizada a criptografia **`BCryptPasswordEncoder`** para as senhas, o que é o padrão de segurança de mercado, evitando o uso inseguro de `{noop}`.

---

## 3. ⚙️ DESAFIOS E SOLUÇÕES SUPERADAS

Este projeto exigiu superar vários obstáculos no ambiente de desenvolvimento:

1.  **Limpeza Arquitetural:** Correção de erros críticos de arquitetura (remover anotações `@PathVariable` dos métodos do `Service` e resolver o mapeamento ambíguo de rotas `GET`).
2.  **Ambiente e Git:** Solução de conflitos complexos no Git (repositórios aninhados e sobrescrita de histórico com `git push --force-with-lease`) para garantir o envio do código limpo.

---
