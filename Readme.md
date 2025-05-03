# Basic Backend

Este é um projeto backend desenvolvido com **Spring Boot** que oferece funcionalidades para gerenciamento de tarefas, cadastros e ordens de serviço. Ele também inclui recursos como geração de PDFs e envio de e-mails.

## Funcionalidades

### Tarefas
- CRUD completo para tarefas.
- Atualização de status de tarefas.
- Listagem de tarefas por técnico.

### Cadastro
- Gerenciamento de usuários (supervisores e técnicos).
- Autenticação de usuários.
- Listagem de supervisores e técnicos.

### Ordem de Serviço
- Criação de ordens de serviço com suporte a upload de fotos.
- Geração de PDFs personalizados.
- Envio de PDFs por e-mail.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.3.5**
  - Spring Data JPA
  - Spring Web
  - Spring Validation
  - Spring Mail
- **PostgreSQL** como banco de dados.
- **iTextPDF** para geração de PDFs.
- **Docker** para containerização.

## Estrutura do Projeto

- **`tarefas`**: Gerenciamento de tarefas.
- **`cadastro`**: Gerenciamento de usuários e autenticação.
- **`ordemDeServico`**: Geração de PDFs e envio de e-mails.
- **`config`**: Configurações globais, como CORS.

## Configuração do Ambiente

### Pré-requisitos
- **Java 17** ou superior.
- **Maven**.
- **PostgreSQL**.
- **Docker** (opcional, para execução em container).

### Configuração do Banco de Dados
No arquivo `application.properties`, configure as variáveis de ambiente para o banco de dados:

```properties
spring.datasource.url=jdbc:postgresql://${PG_HOST}:${PG_PORT}/${PG_DATABASE}
spring.datasource.username=${PG_USER}
spring.datasource.password=${PG_PASSWORD}
```

### Configuração do E-mail
Configure as variáveis de ambiente para o envio de e-mails:

```properties
spring.mail.host=${MAIL_HOST}
spring.mail.port=${MAIL_PORT}
spring.mail.username=${MAIL_USERNAME}
spring.mail.password=${MAIL_PASSWORD}
```

## Como Executar

### Localmente
1. Clone o repositório:
   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd basic_backend
   ```

2. Compile e execute o projeto:
   ```bash
   ./mvnw spring-boot:run
   ```

3. O backend estará disponível em `http://localhost:8080`.

### Usando Docker
1. Construa a imagem Docker:
   ```bash
   docker build -t basic-backend .
   ```

2. Execute o container:
   ```bash
   docker run -p 8080:8080 --env-file .env basic-backend
   ```

## Endpoints Principais

### Tarefas
- `GET /tarefas`: Lista todas as tarefas.
- `GET /tarefas/{id}`: Busca uma tarefa pelo ID.
- `POST /tarefa`: Cria uma nova tarefa.
- `PUT /tarefas/{id}`: Atualiza uma tarefa.
- `DELETE /tarefas/{id}`: Deleta uma tarefa.
- `PUT /tarefas/{id}/status`: Atualiza o status de uma tarefa.

### Cadastro
- `POST /cadastro`: Cria um novo cadastro.
- `POST /login`: Autentica um usuário.
- `GET /cadastros/supervisores`: Lista todos os supervisores.
- `GET /cadastros/tecnicos`: Lista todos os técnicos.

### Ordem de Serviço
- `POST /os`: Cria uma nova ordem de serviço e envia por e-mail.
- `GET /os`: Lista todas as ordens de serviço.

## Geração de PDFs

O projeto utiliza a biblioteca **iTextPDF** para gerar PDFs personalizados com informações de ordens de serviço, incluindo imagens e assinaturas.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).