# Cadastro de Clientes

Este projeto é uma aplicação para gerenciamento de clientes, com backend em Java (Spring Boot) e frontend em React.

## Como Rodar

### Usando Docker

1. Clone o repositório:
   ```bash
   https://github.com/leandromartins1912/Eteg.git
   cd cadastro-clientes

2. Construa e inicie os containers:
   ```bash
   docker-compose up --build
   
3. Acesse a aplicação:
   
- **Frontend: http://localhost:3001/clientes**
- **Backend (API): http://localhost:8080**
- **Swagger UI: http://localhost:8080/swagger-ui/index.html#/**

### Sem Docker

1. Backend: Navegue até o diretório api e execute:
   ```bash
mvn spring-boot:run

2. Frontend: Navegue até o diretório frontend e instale as dependências:
   ```bash
   npm install
   npm run dev
3. Acesse a aplicação no navegador:  http://localhost:5173/clientes
