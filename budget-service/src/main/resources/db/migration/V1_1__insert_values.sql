insert into reeste.budget_lines_type(name) values ('Доходы');

insert into reeste.budget_lines_type(name) values ('Расходы');

insert into reeste.budget_lines_item(name, id_type) values ('Поступления от реализации товаров, работ, услуг', 1);

insert into reeste.budget_lines_item(name, id_type) values ('Поступления от прочей реализации', 1);

insert into reeste.budget_lines_item(name, id_type) values ('Прочие поступления по операционной деятельности', 1);

insert into reeste.budget_lines_item(name, id_type) values ('Оплата сырья и материалов', 2);

insert into reeste.budget_lines_item(name, id_type) values ('Расходы на оплату труда', 2);

insert into reeste.budget_lines_item(name, id_type) values ('Коммунальные расходы', 2);

insert into reeste.budget_lines_item(name, id_type) values ('Услуги связи', 2);

insert into reeste.budget_lines_item(name, id_type) values ('Общехозяйственные платежи', 2);

insert into reeste.budget_lines_item(name, id_type) values ('Налоги, за исключением ЕСН и НДФЛ', 2);

INSERT INTO reeste.budget(name, year, quarter) VALUES ('Бюджет АО НАК "Азот" за 1 квартал 2024 года', 2024, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (1, 1, 54300.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (2, 2, 0.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (3, 3, 1000.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (4, 4, 10000.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (5, 5, 30000.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (6, 6, 500.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (7, 7, 1000.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (8, 8, 300.00, 1);

INSERT INTO reeste.budget_lines (id, item_id, sum, budget_id) VALUES (9, 9, 5000.00, 1);