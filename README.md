# desafio_capgemini
DESAFIO CAPGEMINI 

O sistema foi feito em **java 11** e utilizando o **banco de dados H2 em mémoria**.

Esta rodando na **porta 8080**, para alterar adicione a seguinte linha _server.port=8082_ 
ao arquivo _src/main/resources/application.properties_.

###### Sobre a interface gráfica do acesso ao banco de dados

Para acessar a interface gráfica do banco de dados rode o projeto e acesse a url
http://localhost:8080/h2-console/login.jsp por um navegador Web, se você alterou a porta em que o sistema roda também 
altere a informação da porta na url.

No campo _JDBC URL_ adicione o valor _jdbc:h2:mem:db_capgemini_ onde a informação _db_capgemini_ é o nome do banco. 
O username é o padrão _sa_, e **não há password**, deixe-o em branco e **clique em conectar**.

###### Requisições do sistema

**Lembre de alterar a porta caso a tenha mudado.** Todas as transações de dados são feitas no formato Json.

Para **adicionar um cliente** envie uma requisição **POST** para _localhost:8080/clientes_.
Exemplo de **body**. Observação: é necessário um valor de CPF válido e sem pontuação, minha sugestão para obter números
válidos é usar este site https://www.4devs.com.br/gerador_de_cpf)

`
{
    "nome":"Isabella Valentina Jennifer Lima",
    "cpf": "47501982856"
}
`

Para **consultar saldo** envie uma requisição **GET** para _localhost:8080/clientes/{id}_, substitua _{id}_ pelo número 
do id do cliente. Por exemplo _localhost:8080/clientes/1_.

Para realizar um **depósito** envie uma requisição **PUT** para _localhost:8080/clientes/{id}/deposito_, substitua 
_{id}_ pelo número do id do cliente. Por exemplo _localhost:8080/clientes/1/deposito_.
Exemplo de **body**

`{ "valor": 10 }`


Para realizar um **saque** envie uma requisição **PUT** para _localhost:8080/clientes/{id}/saque_, substitua
_{id}_ pelo número do id do cliente. Por exemplo _localhost:8080/clientes/1/saque_.
Exemplo de **body**

`{ "valor": 10 }`
