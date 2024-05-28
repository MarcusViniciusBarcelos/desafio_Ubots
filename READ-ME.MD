# Projeto de Distribuição de Solicitações da Invext

projeto de software para desafio técnico Ubots, que distribui solicitações de clientes para os times apropriados (Cartões, Empréstimos e Outros Assuntos) com base no tipo de solicitação. O software garante que cada atendente atenda no máximo 3 solicitações simultâneas e gerencia uma fila de espera quando necessário.

## Funcionalidades

- Distribuição de solicitações para os times corretos com base no tipo.
- Limite de 3 solicitações simultâneas por atendente.
- Gerenciamento de fila de espera para solicitações excedentes.
- Autenticação JWT para segurança.
- Documentação da API com Swagger.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Swagger para documentação da API
- JUnit para testes

## Pré-requisitos

- JDK 17 ou superior
- Maven 3.6.3 ou superior

## Como Executar o Projeto

1. Clone o repositório:

    ```sh
    git clone https://github.com/seu-usuario/seu-repositorio.git
    cd seu-repositorio
    ```

2. Compile e execute o projeto:
    No Linux ou macOS:

    ```sh
    ./mvnw spring-boot:run
    ```

    No Windows:

    ```sh
    .\mvnw.cmd spring-boot:run
    ```

3. Acesse a aplicação:
    - A aplicação estará disponível em `http://localhost:8081`.
    - A documentação da API com Swagger estará disponível em `http://localhost:8081/swagger-ui.html`.

## Endpoints da API

### Autenticação

- **Registrar Usuário**

    ```http
    POST /solicitacoes/register
    ```

    Body (JSON):

    ```json
    {
        "username": "user",
        "password": "password"
    }
    ```

- **Login**

    ```http
    POST /solicitacoes/login
    ```

    Body (JSON):

    ```json
    {
        "username": "user",
        "password": "password"
    }
    ```

### Recursos Protegidos

- **Acessar Recurso Protegido**

    ```http
    GET /solicitacoes/protected
    ```

    Header:

    ```http
    Authorization: Bearer {token}
    ```
