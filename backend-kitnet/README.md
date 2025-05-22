crie uma simples



docker run --name mysql-kitnet -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=kitnet -p 3306:3306 -d mysql/mysql-server:8.0



docker exec -it mysql-kitnet bash



mysql -u root -proot





-- Garante que o usuário 'root' para 'localhost' tenha a senha 'root'

ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';



-- Cria ou atualiza o usuário 'root' para aceitar conexões de qualquer host ('%') com a senha 'root'

-- Se o usuário 'root'@'%' já existir, ele será atualizado. Se não, será criado.

CREATE USER 'root'@'%' IDENTIFIED BY 'root';



-- Concede todas as permissões em todos os bancos de dados e tabelas para o usuário 'root' vindo de qualquer host

GRANT ALL PRIVILEGES ON *.* TO 'root'@'%' WITH GRANT OPTION;



-- Recarrega as tabelas de privilégios para que as alterações tenham efeito imediato

FLUSH PRIVILEGES;



SELECT host, user FROM mysql.user WHERE user = 'root';
