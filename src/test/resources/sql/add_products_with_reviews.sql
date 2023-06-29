INSERT INTO PRODUCT (id, date_created, date_updated, available_quantity, price, product_name, description)
VALUES (1, TIMESTAMP '2023-06-22 10:30:00.124', TIMESTAMP '2023-06-22 10:30:00.124', 50, 99.9, 'Сок апельсиновый, 0.75 л', '100% натуральный сок, без сахара и консервантов');

INSERT INTO PRODUCT (id, date_created, date_updated, available_quantity, price, product_name)
VALUES (2, TIMESTAMP '2023-06-22 10:31:00.341', TIMESTAMP '2023-06-22 10:31:00.341', 19, 85, 'Шоколад Milka');

INSERT INTO REVIEW (id, date_created, date_updated, product_id, post_message, rating)
VALUES (1, TIMESTAMP '2023-06-25 20:03:51.021', TIMESTAMP '2023-06-25 20:03:51.021', 2, 'Хороший шоколад!', 5);

INSERT INTO REVIEW (id, date_created, date_updated, product_id, post_message, rating)
VALUES (2, TIMESTAMP '2023-06-27 19:54:51.021', TIMESTAMP '2023-06-27 19:54:51.021', 2, 'Слишком сладкий', 4);