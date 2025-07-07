
# Estoque Nexdom

Sistema feito para cadastro e movimentação de produtos.


## Configurações do Projeto

Versões
```bash
  Java: 21
```
```bash
  Node: 22
```

Clone o projeto

```bash
  git clone https://github.com/Joaocastro20/Estoque-NexDom.git
```

Entre no diretório do docker

```bash
  cd Estoque-NexDom/backend/docker
```

Execute o comando pra subir compose do Kafka

```bash
    docker compose up -d
```

Entre no diretório do backend

```bash
  cd Estoque-NexDom/backend
```

Instale as dependências

```bash
  mvn clean install
```

Inicie o servidor

```bash
  mvn spring-boot:run
```

Entre no diretório do frontend Angular

```bash
  cd Estoque-NexDom/frontend-angular
```

Instale as dependências

```bash
  npm install
```

Inicie o servidor

```bash
  npm start
```