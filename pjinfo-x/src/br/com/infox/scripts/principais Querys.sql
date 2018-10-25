show databases;
use dbinfox;
show tables;
select * from tbclientes;
select * from tbusuarios;
select * from tbos;

-- login e senha
select * from tbusuarios where login ='admin' and senha = 'admin';

-- inserir usuario
insert into tbusuarios(iduser, usario, fone, login, senha )
values(4,'Leandro Ramos' , '99999-9999','leandro', '123');
 