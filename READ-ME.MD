# Projeto de Distribuição de Solicitações da Invext

Projeto de software para desafio técnico Ubots, que distribui solicitações de clientes para os times apropriados (Cartões, Empréstimos e Outros Assuntos) com base no tipo de solicitação. O software garante que cada atendente atenda no máximo 3 solicitações simultâneas e gerencia uma fila de espera quando necessário.

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
    .\\mvnw.cmd spring-boot:run
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

### Gerenciamento de Solicitações

- **Criar Solicitação**

    ```http
    POST /solicitacoes/solicitacao
    ```

    Header:

    ```http
    Authorization: Bearer {token}
    ```

    Body (JSON):

    ```json
    {
        "tipo": "Cartões",
        "descricao": "Problema com o cartão"
    }
    ```

    Resposta:

    ```json
    Solicitação criada com status: {status}
    ```

- **Liberar Atendente**

    ```http
    POST /solicitacoes/atendente/liberar
    ```

    Parâmetros:

  - `nomeAtendente`: Nome do atendente a ser liberado.
  - `tipoAtendimento`: Tipo de atendimento do atendente.

    Header:

    ```http
    Authorization: Bearer {token}
    ```

    Resposta:

    ```json
    Atendente liberado e solicitação atribuída da fila, se disponível.
    ```

- **Listar Atendentes**

    ```http
    GET /solicitacoes/atendentes/{nomeTime}
    ```

    Parâmetros de Caminho:

  - `nomeTime`: Nome do time de atendimento (e.g., "Cartões", "Empréstimos", "Outros Assuntos").

    Header:

    ```http
    Authorization: Bearer {token}
    ```

    Resposta:

    ```json
    [
        {
            "nome": "Atendente 1",
            "solicitacoesAtendidas": 2
        },
        {
            "nome": "Atendente 2",
            "solicitacoesAtendidas": 1
        }
    ]
    ```

- **Listar Solicitações na Fila**

    ```http
    GET /solicitacoes/solicitacoes/{nomeTime}
    ```

    Parâmetros de Caminho:

  - `nomeTime`: Nome do time de atendimento (e.g., "Cartões", "Empréstimos", "Outros Assuntos").

    Header:

    ```http
    Authorization: Bearer {token}
    ```

    Resposta:

    ```json
    [
        {
            "tipo": "Cartões",
            "descricao": "Problema com o cartão",
            "status": "na fila"
        },
        {
            "tipo": "Cartões",
            "descricao": "Outro problema com o cartão",
            "status": "na fila"
        }
    ]
    ```

- **Obter Histórico de Atribuições**

    ```http
    GET /solicitacoes/historico
    ```

    Header:

    ```http
    Authorization: Bearer {token}
    ```

    Resposta:

    ```json
    [
        {
            "atendente": "Atendente 1",
            "solicitacao": "Problema com o cartão",
            "dataHora": "2024-05-28T18:26:54.065+00:00"
        },
        {
            "atendente": "Atendente 2",
            "solicitacao": "Outro problema com o cartão",
            "dataHora": "2024-05-28T19:50:54.065+00:00"
        }
    ]
    ```
