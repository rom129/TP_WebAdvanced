-- Database sakila
CREATE DATABASE sakila;

-- Tables


-- actor
CREATE TABLE actor (
	`actor_id` SMALLINT(5) UNSIGNED NOT NULL,
	`first_name` VARCHAR(45) NOT NULL,
	`last_name` VARCHAR(45) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table actor
--
ALTER TABLE actor 
	ADD PRIMARY KEY (`actor_id` ),
	ADD KEY `idx_actor_last_name`(`last_name` );



-- address
CREATE TABLE address (
	`address_id` SMALLINT(5) UNSIGNED NOT NULL,
	`address` VARCHAR(50) NOT NULL,
	`address2` VARCHAR(50) NULL,
	`district` VARCHAR(20) NOT NULL,
	`city_id` SMALLINT(5) UNSIGNED NOT NULL,
	`postal_code` VARCHAR(10) NULL,
	`phone` VARCHAR(20) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table address
--
ALTER TABLE address 
	ADD PRIMARY KEY (`address_id` ),
	ADD KEY `idx_fk_city_id`(`city_id` );



-- category
CREATE TABLE category (
	`category_id` TINYINT(3) UNSIGNED NOT NULL,
	`name` VARCHAR(25) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table category
--
ALTER TABLE category 
	ADD PRIMARY KEY (`category_id` );



-- city
CREATE TABLE city (
	`city_id` SMALLINT(5) UNSIGNED NOT NULL,
	`city` VARCHAR(50) NOT NULL,
	`country_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table city
--
ALTER TABLE city 
	ADD PRIMARY KEY (`city_id` ),
	ADD KEY `idx_fk_country_id`(`country_id` );



-- country
CREATE TABLE country (
	`country_id` SMALLINT(5) UNSIGNED NOT NULL,
	`country` VARCHAR(50) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table country
--
ALTER TABLE country 
	ADD PRIMARY KEY (`country_id` );



-- customer
CREATE TABLE customer (
	`customer_id` SMALLINT(5) UNSIGNED NOT NULL,
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
-- Index pour la table customer
--
ALTER TABLE customer 
	ADD PRIMARY KEY (`customer_id` ),
	ADD KEY `idx_fk_store_id`(`store_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` ),
	ADD KEY `idx_last_name`(`last_name` );



-- film
CREATE TABLE film (
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
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
-- Index pour la table film
--
ALTER TABLE film 
	ADD PRIMARY KEY (`film_id` ),
	ADD KEY `idx_title`(`title` ),
	ADD KEY `idx_fk_language_id`(`language_id` ),
	ADD KEY `idx_fk_original_language_id`(`original_language_id` );



-- film_actor
CREATE TABLE film_actor (
	`actor_id` SMALLINT(5) UNSIGNED NOT NULL,
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table film_actor
--
ALTER TABLE film_actor 
	ADD PRIMARY KEY (`actor_id` ),
	ADD KEY `idx_fk_film_id`(`film_id` );



-- film_category
CREATE TABLE film_category (
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
	`category_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table film_category
--
ALTER TABLE film_category 
	ADD PRIMARY KEY (`category_id` ),
	ADD KEY `fk_film_category_category`(`category_id` );



-- film_text
CREATE TABLE film_text (
	`film_id` SMALLINT(5)  NOT NULL,
	`title` VARCHAR(255) NOT NULL,
	`description` TEXT(65535) NULL,


--
-- Index pour la table film_text
--
ALTER TABLE film_text 
	ADD PRIMARY KEY (`film_id` ),
	ADD KEY `idx_title_description`(`title`, `description` );



-- inventory
CREATE TABLE inventory (
	`inventory_id` MEDIUMINT(8) UNSIGNED NOT NULL,
	`film_id` SMALLINT(5) UNSIGNED NOT NULL,
	`store_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table inventory
--
ALTER TABLE inventory 
	ADD PRIMARY KEY (`inventory_id` ),
	ADD KEY `idx_fk_film_id`(`film_id` ),
	ADD KEY `idx_store_id_film_id`(`store_id`, `film_id` );



-- language
CREATE TABLE language (
	`language_id` TINYINT(3) UNSIGNED NOT NULL,
	`name` CHAR(20) NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table language
--
ALTER TABLE language 
	ADD PRIMARY KEY (`language_id` );



-- payment
CREATE TABLE payment (
	`payment_id` SMALLINT(5) UNSIGNED NOT NULL,
	`customer_id` SMALLINT(5) UNSIGNED NOT NULL,
	`staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`rental_id` INT(10)  NULL,
	`amount` DECIMAL(5,2) NOT NULL,
	`payment_date` DATETIME NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table payment
--
ALTER TABLE payment 
	ADD PRIMARY KEY (`payment_id` ),
	ADD KEY `idx_fk_staff_id`(`staff_id` ),
	ADD KEY `idx_fk_customer_id`(`customer_id` ),
	ADD KEY `fk_payment_rental`(`rental_id` );



-- rental
CREATE TABLE rental (
	`rental_id` INT(10)  NOT NULL,
	`rental_date` DATETIME NOT NULL,
	`inventory_id` MEDIUMINT(8) UNSIGNED NOT NULL,
	`customer_id` SMALLINT(5) UNSIGNED NOT NULL,
	`return_date` DATETIME NULL,
	`staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table rental
--
ALTER TABLE rental 
	ADD PRIMARY KEY (`rental_id` ),
	ADD UNIQUE KEY `rental_date`(`rental_date`, `inventory_id`, `customer_id` ),
	ADD KEY `idx_fk_inventory_id`(`inventory_id` ),
	ADD KEY `idx_fk_customer_id`(`customer_id` ),
	ADD KEY `idx_fk_staff_id`(`staff_id` );



-- staff
CREATE TABLE staff (
	`staff_id` TINYINT(3) UNSIGNED NOT NULL,
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
-- Index pour la table staff
--
ALTER TABLE staff 
	ADD PRIMARY KEY (`staff_id` ),
	ADD KEY `idx_fk_store_id`(`store_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` );



-- store
CREATE TABLE store (
	`store_id` TINYINT(3) UNSIGNED NOT NULL,
	`manager_staff_id` TINYINT(3) UNSIGNED NOT NULL,
	`address_id` SMALLINT(5) UNSIGNED NOT NULL,
	`last_update` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


--
-- Index pour la table store
--
ALTER TABLE store 
	ADD PRIMARY KEY (`store_id` ),
	ADD UNIQUE KEY `idx_unique_manager`(`manager_staff_id` ),
	ADD KEY `idx_fk_address_id`(`address_id` );

