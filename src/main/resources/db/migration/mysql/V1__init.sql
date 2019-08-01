create table empresa (
       id bigint not null,
        cnpj varchar(255) not null,
        data_atualizacao datetime not null,
        data_criacao datetime not null,
        razao_social varchar(255) not null,
        primary key (id)
    ) engine=InnoDB 
 ;   
create table funcionario (
       id bigint not null,
        cpf varchar(255) not null,
        data_atualizacao datetime not null,
        data_criacao datetime not null,
        email varchar(255) not null,
        nome varchar(255) not null,
        perfil varchar(255) not null,
        qtd_horas_almoco double precision not null,
        qtd_horas_trabalhadas_dia double precision not null,
        senha varchar(255) not null,
        valor_hora decimal(19,2) not null,
        empresa_id bigint,
        primary key (id)
    ) engine=InnoDB  
   
  ;
create table hibernate_sequence (next_val bigint) engine=InnoDB;    
insert into hibernate_sequence values ( 1 );    
insert into hibernate_sequence values ( 1 );    
insert into hibernate_sequence values ( 1 );
 
    
create table lancamento (
       id bigint not null,
        data datetime not null,
        data_atualizacao datetime not null,
        data_criacao datetime not null,
        descricao varchar(255) not null,
        localizacao varchar(255) not null,
        tipo varchar(255) not null,
        funcionario_id bigint,
        primary key (id)
    ) engine=InnoDB
 ;

alter table funcionario add constraint FK4cm1kg523jlopyexjbmi6y54j  foreign key (empresa_id) references empresa (id);

alter table lancamento add constraint FK46i4k5vl8wah7feutye9kbpi4 foreign key (funcionario_id)  references funcionario (id);