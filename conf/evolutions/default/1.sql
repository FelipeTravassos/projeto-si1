# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table cadeira (
  id                        integer not null,
  nome                      varchar(255) not null,
  creditos                  integer,
  periodo                   integer,
  dificuldade               integer,
  constraint pk_cadeira primary key (id))
;

create table plano_de_curso (
  id                        bigint not null,
  constraint pk_plano_de_curso primary key (id))
;

create sequence cadeira_seq;

create sequence plano_de_curso_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists cadeira;

drop table if exists plano_de_curso;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists cadeira_seq;

drop sequence if exists plano_de_curso_seq;

