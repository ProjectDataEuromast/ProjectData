SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- Schema projectdb
CREATE SCHEMA IF NOT EXISTS `projectdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `projectdb` ;

-- Table `projectdb`.`user_interface`
CREATE TABLE IF NOT EXISTS `projectdb`.`user_interface` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(16) NOT NULL,
  `password` VARCHAR(32) NOT NULL,
  `twitter_user_id` INT NOT NULL,
  `weather_weather_id` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_UserInterface_twitter_user1_idx` (`twitter_user_id` ASC),
  INDEX `fk_UserInterface_weather1_idx` (`weather_weather_id` ASC),
  CONSTRAINT `fk_UserInterface_twitter_user1`
    FOREIGN KEY (`twitter_user_id`)
    REFERENCES `projectdb`.`twitter_user` (`twitter_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_UserInterface_weather1`
    FOREIGN KEY (`weather_weather_id`)
    REFERENCES `projectdb`.`weather` (`weather_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
    
-- Table `projectdb`.`twitter_user`
CREATE TABLE IF NOT EXISTS `projectdb`.`twitter_user` (
  `twitter_user_id` INT NOT NULL AUTO_INCREMENT,
  `twitter_user_location_id` INT NOT NULL,
  `twitter_message_id` INT NOT NULL,
  PRIMARY KEY (`twitter_user_id`),
  INDEX `fk_twitter_user_twitter_message1_idx` (`twitter_message_id` ASC),
  INDEX `fk_twitter_user_twitter_location1_idx` (`twitter_user_location_id` ASC),
  CONSTRAINT `fk_twitter_user_twitter_message1`
    FOREIGN KEY (`twitter_message_id`)
    REFERENCES `projectdb`.`twitter_message` (`twitter_message_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_twitter_user_twitter_location1`
    FOREIGN KEY (`twitter_user_location_id`)
    REFERENCES `projectdb`.`twitter_user_location` (`twitter_user_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
-- Table `projectdb`.`twitter_user_location`
CREATE TABLE IF NOT EXISTS `projectdb`.`twitter_user_location` (
  `twitter_user_location_id` INT NOT NULL AUTO_INCREMENT,
  `twitter_user_location` VARCHAR(200) NOT NULL,
  PRIMARY KEY (`twitter_user_location_id`));
  
-- Table `projectdb`.`twitter_message`
CREATE TABLE IF NOT EXISTS `projectdb`.`twitter_message` (
  `twitter_message_id` INT NOT NULL AUTO_INCREMENT,
  `twitter_message` VARCHAR(300) NOT NULL,
  `twitter_time_twitter_time_id` INT NOT NULL,
  PRIMARY KEY (`twitter_message_id`),
  INDEX `fk_twitter_message_twitter_time1_idx` (`twitter_time_twitter_time_id` ASC),
  CONSTRAINT `fk_twitter_message_twitter_time1`
    FOREIGN KEY (`twitter_time_twitter_time_id`)
    REFERENCES `projectdb`.`twitter_time` (`twitter_time_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

-- Table `projectdb`.`twitter_time`
CREATE TABLE IF NOT EXISTS `projectdb`.`twitter_time` (
  `twitter_time_id` INT NOT NULL AUTO_INCREMENT,
  `twitter_date` DATE NOT NULL,
  PRIMARY KEY (`twitter_time_id`));
  
  -- Table `projectdb`.`weather`
CREATE TABLE IF NOT EXISTS `projectdb`.`weather` (
  `weather_id` INT NOT NULL AUTO_INCREMENT,
  `temp_id` INT NOT NULL,
  `weather_Location_id` INT NOT NULL,
  `rain_id` INT NOT NULL,
  INDEX `fk_Buienradar_BuienradarLocation_idx` (`weather_Location_id` ASC),
  INDEX `fk_Buienradar_buienradarmaxtemp1_idx` (`temp_id` ASC),
  INDEX `fk_Buienradar_buienradarregen1_idx` (`rain_id` ASC),
  PRIMARY KEY (`weather_id`),
  CONSTRAINT `fk_Buienradar_BuienradarLocation`
    FOREIGN KEY (`weather_Location_id`)
    REFERENCES `projectdb`.`weather_location` (`weather_location_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Buienradar_buienradarmaxtemp1`
    FOREIGN KEY (`temp_id`)
    REFERENCES `projectdb`.`weather_temp` (`temp_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Buienradar_buienradarregen1`
    FOREIGN KEY (`rain_id`)
    REFERENCES `projectdb`.`weather_rain` (`rain_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
  
-- Table `projectdb`.`weather_location`
CREATE TABLE IF NOT EXISTS `projectdb`.`weather_location` (
  `weather_location_id` INT NOT NULL AUTO_INCREMENT,
  `weather_location` VARCHAR(50) NOT NULL,
  `weather_time` TIME NOT NULL,
  `weather_date` DATE NOT NULL,
  PRIMARY KEY (`weather_location_id`));

-- Table `projectdb`.`weather_temp`
CREATE TABLE IF NOT EXISTS `projectdb`.`weather_temp` (
  `temp_id` INT NOT NULL AUTO_INCREMENT,
  `temp_max` DOUBLE NOT NULL,
  `temp_min` DOUBLE NOT NULL,
  PRIMARY KEY (`temp_id`));

-- Table `projectdb`.`weather_rain`
CREATE TABLE IF NOT EXISTS `projectdb`.`weather_rain` (
  `rain_id` INT NOT NULL AUTO_INCREMENT,
  `rain_max` DOUBLE NOT NULL,
  PRIMARY KEY (`rain_id`));

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
