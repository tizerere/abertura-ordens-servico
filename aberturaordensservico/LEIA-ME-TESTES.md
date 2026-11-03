# Abertura de Ordens de Serviço — SENAI

## Iniciar
1. No MySQL, execute `CREATE DATABASE IF NOT EXISTS ordens_servico;`.
2. Configure as variáveis de ambiente `DB_USER` e `DB_PASSWORD` para o MySQL local (ou ajuste `application.properties` localmente; não publique senhas).
3. Na pasta do projeto execute `./mvnw.cmd spring-boot:run` no PowerShell do Windows. Requer JDK 17+ e acesso à internet na primeira execução do Maven Wrapper.
4. No Postman use `http://localhost:8080` e Body > raw > JSON.

## Testes — setores
- POST `/setores`: `{"nome":"Costura"}` (201)
- POST `/setores`: `{"nome":"Montagem"}` (201)
- GET `/setores` (200)
- GET `/setores/1` (200 se existir)
- PUT `/setores/1`: `{"nome":"Costura de Acabamento"}` (200)
- DELETE `/setores/999` (404)
- DELETE de um setor sem equipamentos (204)

## Testes — equipamentos
- POST `/equipamentos`: `{"nome":"Máquina de Costura Industrial","numeroPatrimonio":"PAT-001","setor":{"id":1}}` (201 se setor 1 existir)
- POST `/equipamentos`: `{"nome":"Prensa de Montagem","numeroPatrimonio":"PAT-002","setor":{"id":2}}` (201 se setor 2 existir)
- GET `/equipamentos` (200)
- GET `/equipamentos/1` (200 se existir)
- PUT `/equipamentos/1`: `{"nome":"Máquina de Costura Industrial Revisada","numeroPatrimonio":"PAT-001","setor":{"id":2}}` (200)
- POST `/equipamentos` com `setor.id=9999` (404)
- DELETE `/equipamentos/1` (204); depois GET `/equipamentos/1` (404)

**Observações:** IDs são exemplos; utilize os IDs retornados pelo seu banco. Um setor com equipamentos não pode ser excluído (409). As tabelas são geradas pelo JPA, mas o banco deve existir.
