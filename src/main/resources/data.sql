
INSERT ignore INTO ecommerce_pff.user_credentials (id, email, is_active, password, verfication_token) VALUES (1, 'medrassachanuwu@gmail.com', true, '$2a$10$1SZe2wskyPD0pbQS0Nnxp.ItotddxQ7eoyNUf.xuBKdX7EC05QUxW', 'a168dc9b-5cd8-4641-8a76-adbbaf8c64f4');
INSERT ignore INTO ecommerce_pff.user (id, birth_date, email, first_name, gender, img, is_active, last_name, phone_number, user_crendials_id) VALUES (1, '2002-12-11', 'medrassachanuwu@gmail.com', 'mohazmm', 'male', null, true, null, '01123fm112323l1', 1);
INSERT IGNORE INTO ecommerce_pff.cart (id, user_id) VALUES (1, 1);
# user password is : User@123
INSERT IGNORE INTO ecommerce_pff.cart (id, user_id) VALUES (1, 1);
INSERT IGNORE into ecommerce_pff.product (id, description, name, price, quantity_to_sell, categorie_id) VALUES ('ba3e610c-8b64-4cf9-939a-d8314d41ae16', 'loreme lomerelm,mla', 'sabon', 121, 0, null);

insert IGNORE into role(name) values ("ADMIN");
insert IGNORE into role(name) values  ("CUSTOMER");

INSERT IGNORE INTO ecommerce_pff.user_roles (user_id, role_id) VALUES (1, 2);
INSERT IGNORE INTO ecommerce_pff.user_roles (user_id, role_id) VALUES (1, 1);


