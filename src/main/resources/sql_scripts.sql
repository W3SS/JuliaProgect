DROP TABLE IF EXISTS Goods;
DROP TABLE IF EXISTS Shop;
DROP TABLE IF EXISTS Purchase;


CREATE TABLE Goods
(
  id int PRIMARY KEY NOT NULL,
  name varchar (20) NOT NULL,
  description varchar (50),
  goods_version int
);
ALTER TABLE Goods ADD CONSTRAINT unique_goods_name UNIQUE (name);

CREATE TABLE Shop
 (
     id INT PRIMARY KEY NOT NULL,
     name varchar(20) NOT NULL,
     Address VARCHAR (80),
     shops_version int
 );
ALTER TABLE Shop ADD CONSTRAINT unique_shops_name UNIQUE (name);

CREATE TABLE Purchase
(
    id INT PRIMARY KEY NOT NULL,
    goods_id INT NOT NULL,
    shop_id INT NOT NULL,
    price decimal (10,2)  NOT NULL,
    purchase_version int,

);
ALTER TABLE purchase  ADD COLUMN time_stamp DATE;
ALTER TABLE Purchase ADD CONSTRAINT fk_GoodsId FOREIGN KEY (goods_id) REFERENCES Goods(id) ON DELETE CASCADE;
ALTER TABLE Purchase ADD CONSTRAINT fk_ShopId FOREIGN KEY (shop_id) REFERENCES Shop(id) ON DELETE CASCADE;
ALTER TABLE Purchase ADD CONSTRAINT uc_GoodsShopId UNIQUE (goods_id, shop_id);

