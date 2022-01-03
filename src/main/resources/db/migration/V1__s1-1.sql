CREATE TABLE Item(
	item_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name TEXT NOT NULL,
	sku TEXT NOT NULL,
	barcode TEXT,
	brand TEXT NOT NULL,
	glass_type TEXT NOT NULL,
	isUV TEXT NOT NULL,
	item_type INT,
	week1 INT,
	week2 INT,
	week3 INT,
	week4 INT,
	week5 INT,
	previousMonthTotal INT,
	actualTotal INT,
	orderedLastMonth INT,
	orderedFromManufacturer INT,
	coming INT,
	month INT,
	year INT
);

CREATE TABLE `Order`(
	order_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	orderDate DATETIME
);

CREATE TABLE OrderItem(
	orderitem_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	item_id INT NOT NULL,
	order_id INT NOT NULL,
	FOREIGN KEY(item_id) REFERENCES Item(item_id),
	FOREIGN KEY(order_id) REFERENCES `Order`(order_id)
);

CREATE TABLE MonthYear(
	nowMonth INT NOT NULL,
	nowYear INT NOT NULL,
	access TINYTEXT NOT NULL
);