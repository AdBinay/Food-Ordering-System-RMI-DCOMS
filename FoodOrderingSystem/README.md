####Quary for create database

create database dcoms_db;
use dcoms_db;
create table users(id int(20) unsigned auto_increment primary key not null, name varchar(255) not null, username varchar(255) not null, email varchar(255) null, u_password varchar(255) not null, role int(255) null);
\
insert into users (name, username, u_password, role) values ('admin', 'admin', 'admin', 0);

create table food(id int(20) unsigned auto_increment primary key not null, fname varchar(255) not null, price varchar(10) not null, category varchar(50) not null);

create table orders(id int(20) unsigned auto_increment primary key not null, customer_id int(20), timestamp timestamp not null default current_timestamp on update current_timestamp, address varchar(500), status varchar(10) default 'pending');

create table order_items(id int(20) unsigned auto_increment primary key not null,order_id int(20) not null, food_id int(20) not null, quantity int(20) default 1);


####completed parts

Signup

admin::
1. view foods
2. view orders
3. view a order
4. update order status

user::
1. view food menu
2. add item to the order (ctrl+select items and then press the button )
3. view my order
4. edit quantity from order (click the cell and enter new value and press enter)
5. Calculate total of the ordre items
6. add order (press confirm order button)
7. view specific order from my orders
8. delete selected order
