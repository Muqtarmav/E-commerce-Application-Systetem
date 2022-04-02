set foreign_key_checks = 0;

truncate table product;
truncate table cart;
truncate table cart_item_list;
truncate table item;
truncate table app_user;

insert into product(id, name, price, quantity)
values(12, 'Luxury Map', 2340, 3),
    (13, 'Macbook Air', 5449, 4),
    (14, 'Rocking Chair', 5340, 5),
    (15, 'LPurple T-Shirt', 7340, 7),
     (16, 'Milk', 2536, 8);


insert into item(id, quantity_added, product_id)
values(510, 14, 12),
       (511, 1, 13),
       (512, 13, 14);

insert into cart(id, total_Price)
values(345, 0.0),
(355, 0.0),
(366, 0.0);

insert into app_user(id, first_name, last_name, email, my_cart_id)
values(5005, 'John', 'Badmus', 'John@myspace.com', 345),
(5010, 'Chris', 'Tuck', 'Johng@myspace.com', 355),
(5015, 'Goodnews', 'Badmus', 'Goodnews@myspace.com', 366);


insert into cart_item_list(cart_id, item_list_id)
values(345, 510),
      (355, 511),
      (345, 512);

set foreign_key_checks = 1;