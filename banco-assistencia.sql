create database assistencia;
use assistencia;


-- TABELA USUARIOS
create table usuario(
	id int primary key auto_increment,
    usuario varchar(50) not null,
    login varchar(20) not null unique,
    senha varchar(250) not null,
    perfil varchar(50) not null
);


-- TABELA CLIENTES
create table clientes (
	idFor int primary key auto_increment,
    cep varchar(10) not null,
    endereço varchar(50) not null,
    numero varchar(6) not null,
    complemento varchar(20),
    bairro varchar(50) not null,
    cidade varchar(50) not null,
    uf char(3) not null,
    nomeContato varchar(30) not null,
    fone varchar(15) not null,
    whatsapp varchar(15),
    email varchar (50) not null,
    obs varchar(250),
    razao varchar(50),
    fantasia varchar(50),
    cnpj varchar(50),
    inscricao varchar(50),
    cpf varchar(11) not null
);

drop table clientes;

-- INSERIR
insert into usuario (usuario,login,senha,perfil)
values('Alessandro','sandro',md5('123@senac'),'admin');

-- ATUALIZAR
update usuario set usuario = 'Alessandro', login = 'sandro',
senha = md5('123@senac'), perfil = 'admin' where id = 2;

-- DELETAR
delete from usuario where id = 1;

select * from usuario;

-- TABELA SERVIÇOS
create table servico (
idOs int primary key auto_increment,
-- idCliente varchar(50) not null,
idCliente int not null,
foreign key(idCliente) references clientes(idFor),
pagamento varchar(20),
equipamento varchar(50) not null,
IMEI varchar(50) not null,
defeito varchar(50),
detectado varchar(50),
garantia char(3) not null,
nota char(3) not null,
tecnico varchar(50),
valor varchar(20),
statusreparo char(50),
datacad timestamp default current_timestamp,
foto longblob,
solucao varchar(50)
);

drop table servico;

-- INSERIR
insert into servico (pagamento,equipamento,IMEI,relatado,detectado,garantia,nota,tecnico,valor,statusreparo,solucao)
values('pendente','samsung a71','11111111','tela trincada','tela trincada','não','não','Pedro','150,00','em analise','trocar a tela');

select * from servico;

select idOs as ID, defeito as Defeito, statusreparo as Status, valor as Valor, nomeContato as Cliente from servico inner join clientes on servico.idCliente = clientes.idFor where statusreparo = 'Pendente';


