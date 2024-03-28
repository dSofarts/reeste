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
    id_type integer references reeste.budget_lines_type (id) on delete cascade
);

create table reeste.budget_lines
(
    id        serial primary key,
    item_id   integer        references reeste.budget_lines_item (id) on delete cascade,
    sum       numeric(38, 2) not null default 0,
    budget_id integer        references reeste.budget (id)
);