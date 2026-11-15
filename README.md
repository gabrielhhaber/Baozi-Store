# Baozi-Store

API REST da Baozi Store, uma loja de pãozinho chinês, criada como trabalho acadêmico da disciplina Desenvolvimento Web Back-End (Uninter), com Java e Spring Boot.

A API controla clientes, produtos e pedidos. Cada pedido possui armazenado o cliente que comprou, o produto comprado e a quantidade, referenciando o cliente e o produto por ID.

- Autor: Gabriel Haberkamp
- RU: 5153997

## Tecnologias

- Java 25
- Spring Boot 4.1.2 (Spring Web MVC, Spring Data JPA, Bean Validation)
- Banco relacional H2, em arquivo (`./data/baozidb`)
- Maven (com Maven Wrapper, não precisa instalar o Maven)
- JSON em todos os endpoints

## Como executar

Pré-requisito: JDK 25 instalado.

No Windows (PowerShell):

```
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8080`. Na primeira execução, o Maven baixa as dependências e isso pode levar alguns minutos.

Para parar, use `Ctrl+C`. Os dados ficam na pasta `data/`, criada automaticamente. Para zerar o banco, pare a API e apague essa pasta. Apagar a pasta sem parar a API pode gerar erros (as requisições dão 201 sem criar nada real no banco).

## Endpoints

Os três recursos (`/clientes`, `/produtos` e `/pedidos`) têm o mesmo conjunto de operações.

| Método | Caminho | Função | Sucesso | Erros |
|--------|---------|--------|---------|-------|
| POST | `/clientes` | Criar cliente | 201 | 400 |
| GET | `/clientes` | Listar todos | 200 | |
| GET | `/clientes/{id}` | Consultar por ID | 200 | 404 |
| DELETE | `/clientes/{id}` | Apagar | 204 | 404, 409 (cliente com pedidos) |
| POST | `/produtos` | Criar produto | 201 | 400 |
| GET | `/produtos` | Listar todos | 200 | |
| GET | `/produtos/{id}` | Consultar por ID | 200 | 404 |
| DELETE | `/produtos/{id}` | Apagar | 204 | 404, 409 (produto com pedidos) |
| POST | `/pedidos` | Registrar pedido | 201 | 400, 404 (cliente ou produto inexistente), 409 (produto sem estoque) |
| GET | `/pedidos` | Listar todos | 200 | |
| GET | `/pedidos/{id}` | Consultar por ID | 200 | 404 |
| DELETE | `/pedidos/{id}` | Apagar | 204 | 404 |

### Corpos de exemplo

Criar cliente (`POST /clientes`). O campo `clienteDesde` é opcional e, se omitido, vale a data de hoje:

```json
{ "nome": "Gabriel5153997" }
```

Criar produto (`POST /produtos`). `estoque` é `true` quando o produto está disponível:

```json
{ "nome": "Baozi de Queijo", "preco": 8.50, "estoque": true }
```

Registrar pedido (`POST /pedidos`):

```json
{ "clienteId": 1, "produtoId": 1, "quantidade": 5 }
```

A resposta do pedido traz também os nomes e o total:

```json
{
  "id": 1,
  "clienteId": 1,
  "clienteNome": "Gabriel5153997",
  "produtoId": 1,
  "produtoNome": "Baozi de Queijo",
  "precoUnitario": 8.50,
  "quantidade": 5,
  "valorTotal": 42.50
}
```

Erros são devolvidos em JSON, por exemplo:

```json
{ "status": 404, "erro": "Pedido não encontrado: 1" }
```

## Estrutura do projeto

```
src/main/java/br/com/baozi/store/
  model/        entidades JPA: Cliente, Produto, Pedido
  repository/   repositórios Spring Data JPA
  controller/   controladores REST
  dto/          PedidoRequest e PedidoResponse
  exception/    tratamento de erros em JSON
src/main/resources/application.properties   configuração do banco H2
requests/baozi.http                         requisições de teste
docs/                                       diagramas em PlantUML (.puml) e imagens em docs/out
prints/                                     capturas de tela dos testes
```

## Testes da API

As requisições estão em `requests/baozi.http`, para a extensão REST Client do Visual Studio Code (`humao.rest-client`). Com a API rodando, abra o arquivo, coloque o cursor dentro de uma requisição e use `Ctrl+Alt+R`. Execute na ordem do arquivo, com o banco vazio.

As capturas de tela dos testes estão na pasta `prints/`:

| Arquivo | Teste |
|---------|-------|
| `01-post-cliente.png` | POST /clientes |
| `02-post-produto.png` | POST /produtos |
| `03-post-pedido.png` | POST /pedidos |
| `04-get-clientes.png` | GET /clientes |
| `05-get-produtos.png` | GET /produtos |
| `06-get-pedidos.png` | GET /pedidos |
| `07-get-cliente-id.png` | GET /clientes/1 |
| `08-get-produto-id.png` | GET /produtos/1 |
| `09-get-pedido-id.png` | GET /pedidos/1 |
| `10-delete-pedido.png` | DELETE /pedidos/1 |
| `11-confirmar-pedido-apagado.png` | GET /pedidos/1 depois do DELETE (404) |
| `12-delete-produto.png` | DELETE /produtos/1 |
| `13-delete-cliente.png` | DELETE /clientes/1 |
| `14-erro-validacao-nome.png` | POST /clientes com nome vazio (400) |

## Diagramas

Os diagramas de caso de uso e de classes estão em `docs/` como código PlantUML (`caso-de-uso.puml` e `modelo-de-classes.puml`), e as imagens exportadas em `docs/out/`.

## Licença

GNU General Public License v3.0. Veja o arquivo `LICENSE`.
