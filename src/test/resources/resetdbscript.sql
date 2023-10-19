
TRUNCATE address, inventory, local_user, product, verification_token, web_order, web_order_quantities RESTART IDENTITY CASCADE;

INSERT INTO local_user (email, first_name, last_name, password, username, email_verified)
    VALUES ('UserA@junit.com', 'UserA-FirstName', 'UserA-LastName', '$2a$10$hBn5gu6cGelJNiE6DDsaBOmZgyumCSzVwrOK/37FWgJ6aLIdZSSI2', 'UserA', true)
    , ('UserB@junit.com', 'UserB-FirstName', 'UserB-LastName', '$2a$10$TlYbg57fqOy/1LJjispkjuSIvFJXbh3fy0J9fvHnCpuntZOITAjVG', 'UserB', false)
    , ('UserC@junit.com', 'UserC-FirstName', 'UserC-LastName', '$2a$10$SYiYAIW80gDh39jwSaPyiuKGuhrLi7xTUjocL..NOx/1COWe5P03.', 'UserC', false);

INSERT INTO address(address_line_1, city, country, user_id)
    VALUES ('123 Tester Hill', 'Testerton', 'England', 1)
    , ('312 Spring Boot', 'Hibernate', 'England', 3);

INSERT INTO product (name, short_description, long_description, price)
    VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50)
    , ('Product #2', 'Product two short description.', 'This is a very long description of product #2.', 10.56)
    , ('Product #3', 'Product three short description.', 'This is a very long description of product #3.', 2.74)
    , ('Product #4', 'Product four short description.', 'This is a very long description of product #4.', 15.69)
    , ('Product #5', 'Product five short description.', 'This is a very long description of product #5.', 42.59);









DO $$
DECLARE
    userId1 INT := 1;
    userId2 INT := 2;
    product1 INT;
    product2 INT;
    product3 INT;
    product4 INT;
    product5 INT;
    address1 INT;
    address2 INT;
    order1 INT;
    order2 INT;
    order3 INT;
    order4 INT;
    order5 INT;
BEGIN

-- The DELETE statements remain the same
DELETE FROM web_order_quantities;
DELETE FROM web_order;
DELETE FROM inventory;
DELETE FROM product;
DELETE FROM address;

-- The INSERT INTO statements remain the same, but remove square brackets from column names
INSERT INTO product (name, short_description, long_description, price) VALUES ('Product #1', 'Product one short description.', 'This is a very long description of product #1.', 5.50);
--... (and so on for other INSERT statements)

-- Use the INTO keyword to store the result of a SELECT statement into a variable
SELECT id INTO product1 FROM product WHERE name = 'Product #1';
--... (and so on for other SELECT statements)

-- For getting the last inserted id, you can use the RETURNING clause with the INSERT statement
INSERT INTO address (address_line_1, city, country, user_id) VALUES ('123 Tester Hill', 'Testerton', 'England', userId1) RETURNING id INTO address1;
INSERT INTO address (address_line_1, city, country, user_id) VALUES ('312 Spring Boot', 'Hibernate', 'England', userId2) RETURNING id INTO address2;

-- Use the LIMIT and OFFSET clauses instead of TOP and FETCH FIRST ROW ONLY
SELECT id INTO order1 FROM web_order WHERE address_id = address1 AND user_id = userId1 ORDER BY id DESC LIMIT 1;
SELECT id INTO order2 FROM web_order WHERE address_id = address1 AND user_id = userId1 ORDER BY id DESC OFFSET 1 LIMIT 1;
--... (and so on for other SELECT statements)

-- The remaining INSERT INTO statements remain the same
INSERT INTO web_order_quantities (order_id, product_id, quantity) VALUES (order1, product1, 5);
--... (and so on for other INSERT statements)

END $$;
