create table depoimentos(

    id serial not null,
    comentario text not null,
    nome_autor varchar(100) not null,
    foto_url varchar(100),

    primary key(id)
);