# aws operation for dev

## Stack:

- Java 11 (Open J9)
- Spring Boot 2.2.1
- Postgres
- Flyway
- Localstack

## Como configurar o ambiente de desenvolvimento

1. Criar o banco de dados `aws_devtools`:</br>
   conectado ao postgres, execute o sql: `create database aws_devtools;`</br>
  
2. Executar flyway para construção da estrutura do banco:</br>
   `cd {PROJECT_HOME}/development-environment`</br>
   `make -f Makefile.mk local-migration`</br>
    por padrão, o acesso ao banco é feito com a senha `postgres` e usuário `postgres`_

3. Inicialização de recursos AWS:</br>
   `cd {PROJECT_HOME}/development-environment`</br>
   `make -f Makefile.mk start-local_aws`</br>

4. Adicionar variavel de ambiente:</br>
   `spring.profiles.active=development`</br>
   `AWS_ACCESS_KEY_ID=no_secret_key`</br>
   `AWS_SECRET_ACCESS_KEY=no_secret_key`</br>
   `AWS_REGION=us-east-1`</br>
