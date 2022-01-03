CREATE TABLE Brand(
	brand_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	brand_name TEXT NOT NULL
);

CREATE TABLE Glass_Type(
	glass_type_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	glass_type_name TEXT NOT NULL
);


INSERT INTO Brand(brand_name) VALUES('Alcatel');
INSERT INTO Brand(brand_name) VALUES('Amazon');
INSERT INTO Brand(brand_name) VALUES('Apple');
INSERT INTO Brand(brand_name) VALUES('Cricket');
INSERT INTO Brand(brand_name) VALUES('Google');
INSERT INTO Brand(brand_name) VALUES('HTC');
INSERT INTO Brand(brand_name) VALUES('LG');
INSERT INTO Brand(brand_name) VALUES('Microsoft');
INSERT INTO Brand(brand_name) VALUES('Motorola');
INSERT INTO Brand(brand_name) VALUES('Nintendo');
INSERT INTO Brand(brand_name) VALUES('Nokia');
INSERT INTO Brand(brand_name) VALUES('One Plus');
INSERT INTO Brand(brand_name) VALUES('Samsung');

INSERT INTO Glass_Type(glass_type_name) VALUES("Standard");
INSERT INTO Glass_Type(glass_type_name) VALUES("FCFA");
INSERT INTO Glass_Type(glass_type_name) VALUES("Privacy");
INSERT INTO Glass_Type(glass_type_name) VALUES("3D/Curved FA");
INSERT INTO Glass_Type(glass_type_name) VALUES("Back Glass");
INSERT INTO Glass_Type(glass_type_name) VALUES("Camera Glass");
INSERT INTO Glass_Type(glass_type_name) VALUES("Tablet Glass");
INSERT INTO Glass_Type(glass_type_name) VALUES("FCSA");
INSERT INTO Glass_Type(glass_type_name) VALUES("Watches");
INSERT INTO Glass_Type(glass_type_name) VALUES("UV");