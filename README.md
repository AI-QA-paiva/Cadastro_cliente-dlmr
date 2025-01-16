# Cadastro_cliente-dlmr
Trata-se de um cadastro de clientes para fins de controle e prospecção


#aplicando Docker

Criei um container por linha de comando no teminal

docker run --name mysql-container -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=clientes_db -e MYSQL_USER=xxxx -e MYSQL_PASSWORD=yyyy -p 3306:3306 -d mysql:latest

run >> agrupa funçoes como pull, exec, start
--name mysql-container >> personifica o nome do meu container
-e MYSQL_ROOT_PASSWORD=root >>  variavel que tem senha do usuario adminsitrador do bd
-e MYSQL_DATABASE=clientes_db >> cria o banco de dados
-e MYSQL_USER=xxxx 
-e MYSQL_PASSWORD= yyyyy
-p 3036:3036 >> define a porta para comunicaçao do lado externo (aplciaçao) : lado interno (mysql)
-d >> indica modo daemon
mysql:latest  >> indica a versão do mysql >> latest é a ultima versão que tem imagem no dockerhub



