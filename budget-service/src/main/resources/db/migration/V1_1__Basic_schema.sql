create schema if not exists reeste;

create table reeste.budget(
    id      serial primary key,
    name    varchar(100) not null check (length(trim(name)) >= 3),
    year    integer      not null,
    quarter integer      not null
);