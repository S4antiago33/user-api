# user-api

## 📌 Descrição

Este projeto é uma API REST desenvolvida com **Spring Boot**, que implementa um sistema básico de autenticação com **JWT (JSON Web Token)**. O foco está na estruturação de um backend seguro, com login e proteção de rotas, utilizando banco de dados MySQL para persistência de usuários.

---

## 🔐 Funcionalidades

- ✅ Criação de usuários (rota `/login/create`)
- ✅ Autenticação com retorno de token JWT (rota `/login`)
- ✅ Proteção de rotas com filtro JWT (`JwtFilter`)
- ✅ Decodificação e validação de token com `JwtService`
- ✅ Integração com banco de dados MySQL via JPA
- ✅ Uso de DTOs para segurança e clareza nos dados de entrada e saída
- ✅ Configuração de CORS e Spring Security
- ✅ Flyway configurado para controle de versão do banco de dados

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5**
- **Spring Security**
- **Auth0 JWT**
- **MySQL**
- **JPA / Hibernate**
- **Flyway**
- **Lombok**
- **Maven**

---

## 🚀 Como Executar Localmente

### ✔ Pré-requisitos

- Java 17+
- MySQL (rodando na porta padrão `3306`)
- Maven
- IntelliJ IDEA (ou outro editor)

### ⚙ Configuração do Banco de Dados

Crie o banco manualmente (se necessário):

```sql
CREATE DATABASE user_api_db;
