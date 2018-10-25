-- A linha abaixo cria um banco de dados
create database if not exists dbinfox;
-- A Linha abaixo escolhe o banco de dados
use dbinfox;
-- O bloco de instrução abaixo cria uma tabela
create table if not exists tbusuarios(
	iduser int primary key,
	usario varchar(50) not null,
	fone varchar(15),
	login varchar(15) not null unique, 
	senha varchar(15) not null
);
-- O comando abaixo descreve a tabela
describe tbusuarios;
desc tbusuarios;
-- A linha Abaixo INSERE dados na tabela ( CRUD )
-- Parte Create do Crud -> CREATE
insert into tbusuarios(iduser, usario, fone, login, senha )
values(1,'José de Assis' , '99999-9999','joseassis', '12345');

-- A linha abaixo EXIBE na tabela ( CRUD )
-- Parte read do cRud  ->SELECT
select * from tbusuarios;

-- inserindo mais alguns elementos para poder consultar(read)
insert into tbusuarios(iduser, usario, fone, login, senha )
values(2,'Administrador' , '99999-9999','admin', 'admin');
insert into tbusuarios(iduser, usario, fone, login, senha )
values(3,'Bill Gates' , '99999-9999','Bill', '12345');
insert into tbusuarios(iduser, usario, fone, login, senha )
values(4,'Leandro Ramos' , '99999-9999','leandro', '123');
 
-- A linha abaixo MODIFICA um registro na tabela ( CRUD )
-- Parte 	update do crUd -> UPDATE
-- OBS: Caso não seja inserida a clausula where toda a coluna será  modificada.
update tbusuarios set fone='8888-8888' where iduser=2;

-- A linha abaixo DELETA um registro na tabela ( CRUD )
-- Parte 	delete do cruD -> DELETE
-- lete from tbusuarios where iduser=3;

-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++

-- Criação da tabela cliente
create table IF NOT EXISTS tbclientes(
	idcli int primary key auto_increment,
	nomecli varchar(50) not null,
	endcli varchar(100),
	fonecli varchar(50) not null,
	emailcli varchar(50)
);

desc tbclientes;

insert into tbclientes(nomeCli, endcli,fonecli,emailcli)
values('Linus Torvalds', 'Rua Tux, 2015', '9999-9999', 'linus@lunux.com');

select * from tbclientes;

-- Criação da tabela tbos
create table IF NOT EXISTS tbos(
	os int primary key auto_increment,
	data_os timestamp default current_timestamp,
	equipamento varchar(50) not null,
	defeito varchar(150) not null,
	servico varchar(150),
	tecnico varchar(30),
	valor decimal(10, 2), -- dez digitos com duas casas decimais depois da virgula
	idcli int not null,
	foreign key(idcli) references tbclientes(idcli)
);	
    
desc tbos; 

insert into tbos (equipamento,defeito,servico,tecnico,valor,idcli)
values ('NOTEBOOK','Não Liga','Troca da fonte','Zé',87.50,1 );   


-- 	O CÓDIGO ABAIXO TRAZ INFORMAÇÕES DE DUAS TABELAS
-- O é uma variável -- C é uma variável
select 
O.os, equipamento, defeito,servico,valor,
C.nomeCli,fonecli 
	from tbos as O
	inner join tbclientes as C
	on(O.idcli = C.idcli); -- regra
	
	
	
-- +++++++++++++++++++++++++EXECUTAR A PARTIR DA CRIAÇÃO DO PERFIL DE USUÁRIO +++++++++++++
-- AULA 11
use dbinfox;
-- a linha abaixo ADICIONA um campo a tabela
alter table tbusuarios add column perfil varchar(20) not null;

-- a linha abaixo REMOVE um campo a tabela
-- alter table tbusuarios drop column perfil;

update tbusuarios set perfil = 'admin' where  iduser=1;
update tbusuarios set perfil = 'admin' where  iduser=2;
update tbusuarios set perfil = 'user' where  iduser=3;

insert into tbusuarios(iduser, usario, fone, login, senha, perfil )
values(5,'USERTESTEADMIN' , '99999-9999','A', '', 'admin');

insert into tbusuarios(iduser, usario, fone, login, senha, perfil )
values(6,'USERTESTEUSER' , '99999-9999','U', '', 'user');	