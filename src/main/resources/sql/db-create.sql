
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hotel
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hotel` DEFAULT CHARACTER SET utf8 ;
USE `hotel` ;

-- -----------------------------------------------------
-- Table `hotel`.`account`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`account` ;

CREATE TABLE IF NOT EXISTS `hotel`.`account` (
    `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
    `role` VARCHAR(255) NOT NULL,
    `first_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `email` VARCHAR(255) NOT NULL,
    `state` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
    UNIQUE INDEX `email_UNIQUE` (`email` ASC));


-- -----------------------------------------------------
-- Table `hotel`.`response_to_order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`response_to_order` ;

CREATE TABLE IF NOT EXISTS `hotel`.`response_to_order` (
    `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
    `description` VARCHAR(255) NULL,
    PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `hotel`.`order`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`order` ;

CREATE TABLE IF NOT EXISTS `hotel`.`order` (
   `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
   `guests_number` INT NOT NULL,
   `rooms_number` VARCHAR(45) NOT NULL,
    `apartment_class` VARCHAR(255) NOT NULL,
    `check_out_date` DATE NOT NULL,
    `check_in_date` DATE NOT NULL,
    `account_id` INT UNIQUE NOT NULL,
    `response_to_order_id` INT UNIQUE NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_order_account1_idx` (`account_id` ASC),
    INDEX `fk_order_response_to_order1_idx` (`response_to_order_id` ASC),
    CONSTRAINT `fk_order_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `hotel`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_order_response_to_order1`
    FOREIGN KEY (`response_to_order_id`)
    REFERENCES `hotel`.`response_to_order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `hotel`.`apartment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`apartment` ;

CREATE TABLE IF NOT EXISTS `hotel`.`apartment` (
   `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
   `max_guests_number` INT NOT NULL,
   `rooms_number` VARCHAR(45) NOT NULL,
    `apartment_class` VARCHAR(255) NOT NULL,
    `price` BIGINT(10) NOT NULL,
    `state` TINYINT(1) NOT NULL,
    PRIMARY KEY (`id`));


-- -----------------------------------------------------
-- Table `hotel`.`booking`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`booking` ;

CREATE TABLE IF NOT EXISTS `hotel`.`booking` (
     `id` INT UNIQUE NOT NULL AUTO_INCREMENT,
     `guests_number` INT NOT NULL,
     `check_in_date` DATE NOT NULL,
     `check_out_date` DATE NOT NULL,
     `is_paid_for_reservation` TINYINT(1) NOT NULL DEFAULT 0,
    `reservation_data` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `account_id` INT UNIQUE NOT NULL,
    `apartment_id` INT UNIQUE NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_booking_apartment_idx` (`apartment_id` ASC),
    INDEX `fk_booking_account1_idx` (`account_id` ASC),
    CONSTRAINT `fk_booking_apartment`
    FOREIGN KEY (`apartment_id`)
    REFERENCES `hotel`.`apartment` (`id`)
    ON DELETE NO ACTION
    ON UPDATE CASCADE,
    CONSTRAINT `fk_booking_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `hotel`.`account` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


-- -----------------------------------------------------
-- Table `hotel`.`response_to_order_has_apartment`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `hotel`.`response_to_order_has_apartment` ;

CREATE TABLE IF NOT EXISTS `hotel`.`response_to_order_has_apartment` (
     `response_to_order_id` INT UNIQUE NOT NULL,
     `apartment_id` INT UNIQUE NOT NULL,
     PRIMARY KEY (`response_to_order_id`, `apartment_id`),
    INDEX `fk_response_to_order_has_apartment_apartment1_idx` (`apartment_id` ASC),
    INDEX `fk_response_to_order_has_apartment_response_to_order1_idx` (`response_to_order_id` ASC),
    CONSTRAINT `fk_response_to_order_has_apartment_response_to_order1`
    FOREIGN KEY (`response_to_order_id`)
    REFERENCES `hotel`.`response_to_order` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
    CONSTRAINT `fk_response_to_order_has_apartment_apartment1`
    FOREIGN KEY (`apartment_id`)
    REFERENCES `hotel`.`apartment` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
