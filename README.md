# 📘 User API - Teste Técnico

API RESTful desenvolvida em Java com Spring Boot para gerenciamento de usuários, incluindo autenticação com JWT, controle de acesso seguro, migrações com Flyway e integração com banco de dados MySQL.

---

## 🚀 Tecnologias Utilizadas

- Java 17 (Amazon Corretto)
- Spring Boot 3.5.0
- Spring Security
- Spring Data JPA
- Flyway
- MySQL
- Docker (opcional)
- JWT (Java JWT - Auth0)
- Lombok
- Maven

---

## ⚙️ Configuração do Projeto

### 1. Clonar o repositório
```bash
git clone https://github.com/S4antiago33/user-api.git
cd user-api
```

### 2. Configurar o banco de dados

Você pode rodar o MySQL manualmente ou com Docker:

#### ➤ Rodando com Docker (opcional)
```yaml
# docker-compose.yml
version: '3.8'
services:
  mysql:
    image: mysql:8.0
    restart: always
    environment:
      MYSQL_DATABASE: user_api_db
      MYSQL_ROOT_PASSWORD: 123456
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
volumes:
  mysql-data:
```
```bash
docker-compose up -d
```

---

### 3. Configurar o `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/user_api_db
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

app.jwt.secret=chave-secreta-super-segura
app.jwt.expiration=86400000

spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
```

---

### 4. Rodar o projeto
```bash
./mvnw spring-boot:run
```

---

## 🔐 Autenticação

A API utiliza JWT para autenticação de usuários. Após o login, o token JWT deve ser enviado no header das requisições protegidas:

```
Authorization: Bearer <seu_token_jwt>
```

---

## 📮 Endpoints da API

### 🟢 Registro (público)
```
POST /register
```
```json
{
  "nome": "João",
  "email": "joao@email.com",
  "password": "123456"
}
```

### 🟢 Login (público)
```
POST /login
```
```json
{
  "email": "joao@email.com",
  "password": "123456"
}
```

### 🔒 Listar todos os usuários
```
GET /usuarios
```

### 🔒 Buscar por ID
```
GET /usuarios/{id}
```

### 🔒 Buscar por email
```
GET /usuarios/email/{email}
```

### 🔒 Buscar por nome
```
GET /usuarios/nome/{nome}
```

### 🔒 Atualizar usuário
```
PUT /usuarios/{id}
```
```json
{
  "nome": "João Atualizado",
  "email": "joao@novoemail.com"
}
```

### 🔒 Deletar usuário
```
DELETE /usuarios/{id}
```

---

## 📁 Organização do Projeto

```
src/
├── controller/      # Endpoints REST
├── service/         # Regras de negócio
├── repository/      # Interface com o banco
├── models/          # Entidade JPA (Usuario)
├── DTOs/            # Transferência de dados
├── filter/          # Filtro JWT
├── configs/         # Configurações de segurança
└── db/migration/    # Migrations Flyway (V1__create_table.sql)
```

---

## ✅ Testado com

- [x] Postman / Insomnia
- [x] Banco MySQL
- [x] Docker Compose
- [x] Flyway

---

## 📌 Autor

Desenvolvido por [Luis Eduardo Santiago](https://github.com/S4antiago33) como parte de um teste técnico de backend Java + Spring Boot.

---

## 🧩 Possíveis Melhorias Futuras

- Adicionar Swagger para documentação automática
- Implementar testes unitários com JUnit + Mockito
- Paginação nas requisições GET
- Cache com Spring Cache
- Criação de roles e perfis de acesso
