create schema if not exists reeste;

create table reeste.budget
(
    id      serial primary key,
    name    varchar(100) not null check (length(trim(name)) >= 3),
    year    integer      not null,
    quarter integer      not null
);

create table reeste.budget_lines_type
(
    id   serial primary key,
    name varchar not null check (length(trim(name)) > 0)
);

create table reeste.budget_lines_item
(
    id      serial primary key,
    name    varchar not null check (length(trim(name)) > 0),
    id_type integer not null references reeste.budget_lines_type (id)
);

create table reeste.budget_lines
(
    id        serial primary key,
    item_id   integer        not null references reeste.budget_lines_item (id),
    sum       numeric(38, 2) not null default 0,
    budget_id integer        not null references reeste.budget (id)
);