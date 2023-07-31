create table destinos(

    id serial not null,
    foto_url varchar(100) not null,
    nome varchar(50) not null,
    preco real not null,
    ativo boolean not null,

    primary key(id)
);