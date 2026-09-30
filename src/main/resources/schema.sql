DROP TABLE IF EXISTS "widgets";
DROP TABLE IF EXISTS "books";
DROP TABLE IF EXISTS "authors";
drop sequence if exists authors_id_seq;

create sequence authors_id_seq
    increment 1
    minvalue 1
    maxvalue 9223372036854775807
    cache 1;
create table "authors"(
"id" bigint default nextval('authors_id_seq') not null,
"name" text,
"age" integer,
constraint "authors_pkey" primary key ("id")
);

create table "books"(
"isbn" text not null,
"title" text,
"author_id" bigint,
constraint "books_pkey" primary key ("isbn"),
constraint "fk_author" foreign key (author_id) references authors(id)
);