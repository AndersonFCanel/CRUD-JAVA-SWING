show databases;
use dbinfox;
show tables;
select * from tbclientes;
select * from tbusuarios;
select * from tbos;
desc tbos; 
desc tbclientes;
desc tbusuarios;

-- login e senha
select * from tbusuarios where login ='admin' and senha = 'admin';


 
 
 -- 	O CÓDIGO ABAIXO TRAZ INFORMAÇÕES DE DUAS TABELAS
-- O é uma variável -- C é uma variável
select 
O.os, equipamento, defeito,servico,valor,
C.nomeCli,fonecli 
	from tbos as O
	inner join tbclientes as C
	on(O.idcli = C.idcli); -- regra
	