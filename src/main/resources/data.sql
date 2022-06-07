
INSERT ignore INTO ecommerce_pff.user_credentials (id, email, is_active, password, verfication_token) VALUES (1, 'medrassachanuwu@gmail.com', true, '$2a$10$1SZe2wskyPD0pbQS0Nnxp.ItotddxQ7eoyNUf.xuBKdX7EC05QUxW', 'a168dc9b-5cd8-4641-8a76-adbbaf8c64f4');
INSERT ignore INTO ecommerce_pff.user (id,birth_date, email, first_name, gender, img, is_active, is_loged_in, last_name, phone_number, user_crendials_id) VALUES (1,'2002-12-11', 'medrassachanuwu@gmail.com', 'mohazmm', 'male', null, true,false, 'chbani', '01123fm112323l1', 1);
INSERT IGNORE INTO ecommerce_pff.cart (id, user_id) VALUES (1, 1);
-- # user password is : User@123
INSERT IGNORE INTO ecommerce_pff.cart (id, user_id) VALUES (1, 1);
INSERT IGNORE into ecommerce_pff.product (id, description, name, price, quantity_to_sell, categorie_id) VALUES ('ba3e610c-8b64-4cf9-939a-d8314d41ae16', 'loreme lomerelm,mla', 'sabon', 121, 0, null);

insert IGNORE into role(name) values ("ADMIN");
insert IGNORE into role(name) values  ("CUSTOMER");

INSERT IGNORE INTO ecommerce_pff.user_roles (user_id, role_id) VALUES (1, 2);
INSERT IGNORE INTO ecommerce_pff.user_roles (user_id, role_id) VALUES (1, 1);

insert ignore into category(id ,name) VALUES (1,'tecknologie');

INSERT ignore INTO ecommerce_pff.product (id, description, name, price, quantity_to_sell, categorie_id) VALUES ('aa4e43ab-a6e9-4506-aa8c-aace21d3f78f', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Varius praesent vel purus vestibulum adipiscing donec dignissim tincidunt id. Adipiscing eget lectus sagittis egestas.  ', 'MacBook Air', 600, 0, 1);
INSERT ignore ecommerce_pff.product (id, description, name, price, quantity_to_sell, categorie_id) VALUES ('ba3e610c-8b64-4cf9-939a-d8314d41ae16', 'loreme lomerelm,mla', 'sabon', 121, 0, null);
INSERT ignore ecommerce_pff.product (id, description, name, price, quantity_to_sell, categorie_id) VALUES ('d342e1e5-0a71-489e-b2bb-b093a857a966', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Varius praesent vel purus vestibulum adipiscing donec dignissim tincidunt id. Adipiscing eget lectus sagittis egestas.  ', 'the magic of iphone 13', 600, 0, 1);

INSERT ignore INTO ecommerce_pff.product_img (id, is_primary_img, path, product_id) VALUES (15, false, '/images/products/3be35654-ec4d-4856-b3fb-f520b64e8fa2.jpeg', 'ba3e610c-8b64-4cf9-939a-d8314d41ae16');
INSERT ignore INTO ecommerce_pff.product_img (id, is_primary_img, path, product_id) VALUES (18, true, '/images/products/3b635c07-d3f0-4ca7-baf9-3daa651a6924.svg', 'aa4e43ab-a6e9-4506-aa8c-aace21d3f78f');
INSERT ignore INTO ecommerce_pff.product_img (id, is_primary_img, path, product_id) VALUES (22, true, '/images/products/b72d13cc-c005-42e8-b614-ed9c4bcf2557.svg', 'd342e1e5-0a71-489e-b2bb-b093a857a966');

