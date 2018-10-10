-- -----------------------------------------------------
-- Schema SuperHeroSighting
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `SuperHeroSighting` DEFAULT CHARACTER SET utf8 ;
USE `SuperHeroSighting` ;

-- -----------------------------------------------------
-- Table `Hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero` (
  `HeroID` INT NOT NULL AUTO_INCREMENT,
  `HeroName` VARCHAR(45) NOT NULL,
  `HeroDescription` TINYTEXT NOT NULL,
  PRIMARY KEY (`HeroID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Location` (
  `LocationID` INT NOT NULL AUTO_INCREMENT,
  `LocationName` VARCHAR(30) NOT NULL,
  `LocationDescription` TINYTEXT NOT NULL,
  `Longitude` DECIMAL(11,8) NOT NULL,
  `Latitude` DECIMAL(11,8) NOT NULL,
  `Street` VARCHAR(30) NOT NULL,
  `State` VARCHAR(30) NOT NULL,
  `ZipCode` VARCHAR(5) NOT NULL,
  `City` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`LocationID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Organization` (
  `OrganizationID` INT NOT NULL AUTO_INCREMENT,
  `OrganizationName` VARCHAR(60)  NOT NULL,
  `OrganizationDescription` TINYTEXT NOT NULL,
  `LocationID` INT NOT NULL,
  `TelephoneNumber` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`OrganizationID`),
  INDEX `fk_Organization_Location` (`LocationID` ASC),
  CONSTRAINT `fk_Organization_Location`
    FOREIGN KEY (`LocationID`)
    REFERENCES `Location` (`LocationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Member` (
  `MemberID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(30) NOT NULL,
  `LastName` VARCHAR(30) NOT NULL,
  `OrganizationID` INT NOT NULL,
  PRIMARY KEY (`MemberID`),
  INDEX `fk_Member_Organization` (`OrganizationID` ASC),
  CONSTRAINT `fk_Member_Organization`
    FOREIGN KEY (`OrganizationID`)
    REFERENCES `Organization` (`OrganizationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Sighting` (
  `SightingID` INT NOT NULL AUTO_INCREMENT,
  `SightingDate` DATE NOT NULL,
  `LocationID` INT NOT NULL,
  PRIMARY KEY (`SightingID`),
  INDEX `fk_Sighting_Location` (`LocationID` ASC),
  CONSTRAINT `fk_Sighting_Location`
    FOREIGN KEY (`LocationID`)
    REFERENCES `Location` (`LocationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero_Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero_Sighting` (
  `HeroID` INT NOT NULL,
  `SightingID` INT NOT NULL,
  PRIMARY KEY (`HeroID`, `SightingID`),
  INDEX `fk_HeroSighting_SightingID` (`SightingID` ASC),
  INDEX `fk_HeroSighting_HeroID` (`HeroID` ASC),
  CONSTRAINT `fk_HeroSighting_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HeroSighting_SightingID`
    FOREIGN KEY (`SightingID`)
    REFERENCES `Sighting` (`SightingID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SuperPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SuperPower` (
  `SuperPowerID` INT NOT NULL AUTO_INCREMENT,
  `SuperPowerName` VARCHAR(30) NOT NULL,
  `SuperPowerDescription` TINYTEXT NOT NULL,
  PRIMARY KEY (`SuperPowerID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero_SuperPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero_SuperPower` (
  `HeroID` INT NOT NULL,
  `SuperPowerID` INT NOT NULL,
  PRIMARY KEY (`HeroID`, `SuperPowerID`),
  INDEX `fk_HeroSuperPower_SuperPowerID` (`SuperPowerID` ASC),
  INDEX `fk_HeroSuperPower_HeroID` (`HeroID` ASC),
  CONSTRAINT `fk_HeroSuperPower_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HeroSuperPower_SuperPowerID`
    FOREIGN KEY (`SuperPowerID`)
    REFERENCES `SuperPower` (`SuperPowerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Organization_Hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Organization_Hero` (
  `OrganizationID` INT NOT NULL,
  `HeroID` INT NOT NULL,
  PRIMARY KEY (`OrganizationID`, `HeroID`),
  INDEX `fk_OrganizationHero_HeroID` (`HeroID` ASC),
  INDEX `fk_OrganizationHero_OrganizationID` (`OrganizationID` ASC),
  CONSTRAINT `fk_OrganizationHero_OrganizationID`
    FOREIGN KEY (`OrganizationID`)
    REFERENCES `Organization` (`OrganizationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrganizationHero_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE SCHEMA IF NOT EXISTS `SuperHeroSighting_Test` DEFAULT CHARACTER SET utf8 ;
USE `SuperHeroSighting_Test` ;

-- -----------------------------------------------------
-- Table `Hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero` (
  `HeroID` INT NOT NULL AUTO_INCREMENT,
  `HeroName` VARCHAR(45) NOT NULL,
  `HeroDescription` TINYTEXT NOT NULL,
  PRIMARY KEY (`HeroID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Location`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Location` (
  `LocationID` INT NOT NULL AUTO_INCREMENT,
  `LocationName` VARCHAR(30) NOT NULL,
  `LocationDescription` TINYTEXT NOT NULL,
  `Longitude` DECIMAL(11,8) NOT NULL,
  `Latitude` DECIMAL(11,8) NOT NULL,
  `Street` VARCHAR(30) NOT NULL,
  `State` VARCHAR(30) NOT NULL,
  `ZipCode` VARCHAR(5) NOT NULL,
  `City` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`LocationID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Organization`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Organization` (
  `OrganizationID` INT NOT NULL AUTO_INCREMENT,
  `OrganizationName` VARCHAR(60)  NOT NULL,
  `OrganizationDescription` TINYTEXT NOT NULL,
  `LocationID` INT NOT NULL,
  `TelephoneNumber` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`OrganizationID`),
  INDEX `fk_Organization_Location` (`LocationID` ASC),
  CONSTRAINT `fk_Organization_Location`
    FOREIGN KEY (`LocationID`)
    REFERENCES `Location` (`LocationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Member`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Member` (
  `MemberID` INT NOT NULL AUTO_INCREMENT,
  `FirstName` VARCHAR(30) NOT NULL,
  `LastName` VARCHAR(30) NOT NULL,
  `OrganizationID` INT NOT NULL,
  PRIMARY KEY (`MemberID`),
  INDEX `fk_Member_Organization` (`OrganizationID` ASC),
  CONSTRAINT `fk_Member_Organization`
    FOREIGN KEY (`OrganizationID`)
    REFERENCES `Organization` (`OrganizationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Sighting` (
  `SightingID` INT NOT NULL AUTO_INCREMENT,
  `SightingDate` DATE NOT NULL,
  `LocationID` INT NOT NULL,
  PRIMARY KEY (`SightingID`),
  INDEX `fk_Sighting_Location` (`LocationID` ASC),
  CONSTRAINT `fk_Sighting_Location`
    FOREIGN KEY (`LocationID`)
    REFERENCES `Location` (`LocationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero_Sighting`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero_Sighting` (
  `HeroID` INT NOT NULL,
  `SightingID` INT NOT NULL,
  PRIMARY KEY (`HeroID`, `SightingID`),
  INDEX `fk_HeroSighting_SightingID` (`SightingID` ASC),
  INDEX `fk_HeroSighting_HeroID` (`HeroID` ASC),
  CONSTRAINT `fk_HeroSighting_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HeroSighting_SightingID`
    FOREIGN KEY (`SightingID`)
    REFERENCES `Sighting` (`SightingID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `SuperPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SuperPower` (
  `SuperPowerID` INT NOT NULL AUTO_INCREMENT,
  `SuperPowerName` VARCHAR(30) NOT NULL,
  `SuperPowerDescription` TINYTEXT NOT NULL,
  PRIMARY KEY (`SuperPowerID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Hero_SuperPower`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Hero_SuperPower` (
  `HeroID` INT NOT NULL,
  `SuperPowerID` INT NOT NULL,
  PRIMARY KEY (`HeroID`, `SuperPowerID`),
  INDEX `fk_HeroSuperPower_SuperPowerID` (`SuperPowerID` ASC),
  INDEX `fk_HeroSuperPower_HeroID` (`HeroID` ASC),
  CONSTRAINT `fk_HeroSuperPower_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HeroSuperPower_SuperPowerID`
    FOREIGN KEY (`SuperPowerID`)
    REFERENCES `SuperPower` (`SuperPowerID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Organization_Hero`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Organization_Hero` (
  `OrganizationID` INT NOT NULL,
  `HeroID` INT NOT NULL,
  PRIMARY KEY (`OrganizationID`, `HeroID`),
  INDEX `fk_OrganizationHero_HeroID` (`HeroID` ASC),
  INDEX `fk_OrganizationHero_OrganizationID` (`OrganizationID` ASC),
  CONSTRAINT `fk_OrganizationHero_OrganizationID`
    FOREIGN KEY (`OrganizationID`)
    REFERENCES `Organization` (`OrganizationID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_OrganizationHero_HeroID`
    FOREIGN KEY (`HeroID`)
    REFERENCES `Hero` (`HeroID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;