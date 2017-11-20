-- Database sakila
CREATE DATABASE sakila;

-- Tables


-- `address`
CREATE TABLE `address` (
	`address` VARCHAR(50) NOT NULL,
	`address2` VARCHAR(50) NULL,
	`district` VARCHAR(20) NOT NULL,
	`city_id` SMALLINT(5) UNSIGNED NOT NULL,
	`postal_code` VARCHAR(10) NULL,
	`phone` VARCHAR(20) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `address`
--
ALTER TABLE `address` 
	ADD PRIMARY KEY (`address_id` ),
	ADD KEY `idx_fk_city_id`(`city_id` );



--
-- Contraintes pour la table `address`
--
ALTER TABLE `address` 
	ADD CONSTRAINT `fk_address_city` FOREIGN KEY (`city_id` ) REFERENCES `fk_address_city`(`city_id` );



-- `category`
CREATE TABLE `category` (
	`name` VARCHAR(25) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `category`
--
ALTER TABLE `category` 
	ADD PRIMARY KEY (`category_id` );



--
-- Contraintes pour la table `category`
--


-- `city`
CREATE TABLE `city` (
	`city` VARCHAR(50) NOT NULL,
	`country_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `city`
--
ALTER TABLE `city` 
	ADD PRIMARY KEY (`city_id` ),
	ADD KEY `idx_fk_country_id`(`country_id` );



--
-- Contraintes pour la table `city`
--
ALTER TABLE `city` 
	ADD CONSTRAINT `fk_city_country` FOREIGN KEY (`country_id` ) REFERENCES `fk_city_country`(`country_id` );



-- `country`
CREATE TABLE `country` (
	`country` VARCHAR(50) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `country`
--
ALTER TABLE `country` 
	ADD PRIMARY KEY (`country_id` );



--
-- Contraintes pour la table `country`
--


-- `customer`
CREATE TABLE `customer` (
	`store_id` TINYINT(3) UNSIGNED NOT NULL,
	`first_name` VARCHAR(45) NOT NULL,
	`last_name` VARCHAR(45) NOT NULL,
	`email` VARCHAR(50) NULL,
	`address_id` SMALLINT(5) UNSIGNED NOT NULL,
	`active` BIT(0)  NOT NULL DEFAULT 1,
	`create_date` DATETIME NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `customer`
--
ALTER TABLE `customer` 
	ADD PRIMARY KEY (`customer_id` ),
	ADD KEY `idx_fk_store_id`(`store_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` ),
	ADD KEY `idx_last_name`(`last_name` );



--
-- Contraintes pour la table `customer`
--
ALTER TABLE `customer` 
	ADD CONSTRAINT `fk_customer_address` FOREIGN KEY (`address_id` ) REFERENCES `fk_customer_address`(`address_id` ),
	ADD CONSTRAINT `fk_customer_store` FOREIGN KEY (`store_id` ) REFERENCES `fk_customer_store`(`store_id` );



-- `film`
CREATE TABLE `film` (
	`title` VARCHAR(255) NOT NULL,
	`description` TEXT(65535) NULL,
	`release_year` YEAR(0)  NULL,
	`language_id` TINYINT(3) UNSIGNED NOT NULL,
	`original_language_id` TINYINT(3) UNSIGNED NULL,
	`rental_duration` TINYINT(3) UNSIGNED NOT NULL DEFAULT 3,
	`rental_rate` DECIMAL(4,2) NOT NULL DEFAULT 4.99,
	`length` SMALLINT(5) UNSIGNED NULL,
	`replacement_cost` DECIMAL(5,2) NOT NULL DEFAULT 19.99,
	`rating` ENUM(5) NULL,
	`special_features` SET(54) NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `film`
--
ALTER TABLE `film` 
	ADD PRIMARY KEY (`film_id` ),
	ADD KEY `idx_title`(`title` ),
	ADD KEY `idx_fk_language_id`(`language_id` ),
	ADD KEY `idx_fk_original_language_id`(`original_language_id` );



--
-- Contraintes pour la table `film`
--
ALTER TABLE `film` 
	ADD CONSTRAINT `fk_film_language` FOREIGN KEY (`language_id` ) REFERENCES `fk_film_language`(`language_id` ),
	ADD CONSTRAINT `fk_film_language_original` FOREIGN KEY (`original_language_id` ) REFERENCES `fk_film_language_original`(`language_id` );



-- `film_actor`
CREATE TABLE `film_actor` (
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `film_actor`
--
ALTER TABLE `film_actor` 
	ADD PRIMARY KEY (`actor_id` ),
	ADD KEY `idx_fk_film_id`(`film_id` );



--
-- Contraintes pour la table `film_actor`
--
ALTER TABLE `film_actor` 
	ADD CONSTRAINT `fk_film_actor_actor` FOREIGN KEY (`actor_id` ) REFERENCES `fk_film_actor_actor`(`actor_id` ),
	ADD CONSTRAINT `fk_film_actor_film` FOREIGN KEY (`film_id` ) REFERENCES `fk_film_actor_film`(`film_id` );



-- `film_category`
CREATE TABLE `film_category` (
	`category_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `film_category`
--
ALTER TABLE `film_category` 
	ADD PRIMARY KEY (`category_id` ),
	ADD KEY `fk_film_category_category`(`category_id` );



--
-- Contraintes pour la table `film_category`
--
ALTER TABLE `film_category` 
	ADD CONSTRAINT `fk_film_category_category` FOREIGN KEY (`category_id` ) REFERENCES `fk_film_category_category`(`category_id` ),
	ADD CONSTRAINT `fk_film_category_film` FOREIGN KEY (`film_id` ) REFERENCES `fk_film_category_film`(`film_id` );



-- `film_text`
CREATE TABLE `film_text` (
	`title` VARCHAR(255) NOT NULL,
	`description` TEXT(65535) NULL,


--
-- Index pour la table `film_text`
--
ALTER TABLE `film_text` 
	ADD PRIMARY KEY (`film_id` ),
	ADD KEY `idx_title_description`(`title`, `description` );



--
-- Contraintes pour la table `film_text`
--


-- `inventory`
CREATE TABLE `inventory` (
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
	`store_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `inventory`
--
ALTER TABLE `inventory` 
	ADD PRIMARY KEY (`inventory_id` ),
	ADD KEY `idx_fk_film_id`(`film_id` ),
	ADD KEY `idx_store_id_film_id`(`store_id`, `film_id` );



--
-- Contraintes pour la table `inventory`
--
ALTER TABLE `inventory` 
	ADD CONSTRAINT `fk_inventory_film` FOREIGN KEY (`film_id` ) REFERENCES `fk_inventory_film`(`film_id` ),
	ADD CONSTRAINT `fk_inventory_store` FOREIGN KEY (`store_id` ) REFERENCES `fk_inventory_store`(`store_id` );



-- `language`
CREATE TABLE `language` (
	`name` CHAR(20) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `language`
--
ALTER TABLE `language` 
	ADD PRIMARY KEY (`language_id` );



--
-- Contraintes pour la table `language`
--


-- `payment`
CREATE TABLE `payment` (
	`customer_id` SMALLINT(5) UNSIGNED NOT NULL,
	`staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`rental_id` INT(10)  NULL,
	`amount` DECIMAL(5,2) NOT NULL,
	`payment_date` DATETIME NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `payment`
--
ALTER TABLE `payment` 
	ADD PRIMARY KEY (`payment_id` ),
	ADD KEY `idx_fk_staff_id`(`staff_id` ),
	ADD KEY `idx_fk_customer_id`(`customer_id` ),
	ADD KEY `fk_payment_rental`(`rental_id` );



--
-- Contraintes pour la table `payment`
--
ALTER TABLE `payment` 
	ADD CONSTRAINT `fk_payment_customer` FOREIGN KEY (`customer_id` ) REFERENCES `fk_payment_customer`(`customer_id` ),
	ADD CONSTRAINT `fk_payment_rental` FOREIGN KEY (`rental_id` ) REFERENCES `fk_payment_rental`(`rental_id` ),
	ADD CONSTRAINT `fk_payment_staff` FOREIGN KEY (`staff_id` ) REFERENCES `fk_payment_staff`(`staff_id` );



-- `rental`
CREATE TABLE `rental` (
	`rental_date` DATETIME NOT NULL,
	`inventory_id` MEDIUMINT(8) UNSIGNED NOT NULL,
	`customer_id` SMALLINT(5) UNSIGNED NOT NULL,
	`return_date` DATETIME NULL,
	`staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `rental`
--
ALTER TABLE `rental` 
	ADD PRIMARY KEY (`rental_id` ),
	ADD UNIQUE KEY `rental_date`(`rental_date`, `inventory_id`, `customer_id` ),
	ADD KEY `idx_fk_inventory_id`(`inventory_id` ),
	ADD KEY `idx_fk_customer_id`(`customer_id` ),
	ADD KEY `idx_fk_staff_id`(`staff_id` );



--
-- Contraintes pour la table `rental`
--
ALTER TABLE `rental` 
	ADD CONSTRAINT `fk_rental_customer` FOREIGN KEY (`customer_id` ) REFERENCES `fk_rental_customer`(`customer_id` ),
	ADD CONSTRAINT `fk_rental_inventory` FOREIGN KEY (`inventory_id` ) REFERENCES `fk_rental_inventory`(`inventory_id` ),
	ADD CONSTRAINT `fk_rental_staff` FOREIGN KEY (`staff_id` ) REFERENCES `fk_rental_staff`(`staff_id` );



-- `staff`
CREATE TABLE `staff` (
	`first_name` VARCHAR(45) NOT NULL,
	`last_name` VARCHAR(45) NOT NULL,
	`address_id` SMALLINT(5) UNSIGNED NOT NULL,
	`picture` BLOB(65535) NULL,
	`email` VARCHAR(50) NULL,
	`store_id` TINYINT(3) UNSIGNED NOT NULL,
	`active` BIT(0)  NOT NULL DEFAULT 1,
	`username` VARCHAR(16) NOT NULL,
	`password` VARCHAR(40) NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `staff`
--
ALTER TABLE `staff` 
	ADD PRIMARY KEY (`staff_id` ),
	ADD KEY `idx_fk_store_id`(`store_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` );



--
-- Contraintes pour la table `staff`
--
ALTER TABLE `staff` 
	ADD CONSTRAINT `fk_staff_address` FOREIGN KEY (`address_id` ) REFERENCES `fk_staff_address`(`address_id` ),
	ADD CONSTRAINT `fk_staff_store` FOREIGN KEY (`store_id` ) REFERENCES `fk_staff_store`(`store_id` );



-- `store`
CREATE TABLE `store` (
	`manager_staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`address_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table `store`
--
ALTER TABLE `store` 
	ADD PRIMARY KEY (`store_id` ),
	ADD UNIQUE KEY `idx_unique_manager`(`manager_staff_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` );



--
-- Contraintes pour la table `store`
--
ALTER TABLE `store` 
	ADD CONSTRAINT `fk_store_address` FOREIGN KEY (`address_id` ) REFERENCES `fk_store_address`(`address_id` ),
	ADD CONSTRAINT `fk_store_staff` FOREIGN KEY (`manager_staff_id` ) REFERENCES `fk_store_staff`(`staff_id` );

