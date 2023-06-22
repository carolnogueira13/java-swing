CREATE DATABASE `campeonato` ;
USE `campeonato` ;

-- -----------------------------------------------------
-- Table `campeonato`.`time`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `campeonato`.`time` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `tecnico` VARCHAR(45) NULL,
  `estado` CHAR(2) NOT NULL,
  `cidade` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `campeonato`.`jogador`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `campeonato`.`jogador` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(45) NOT NULL,
  `nascimento` DATE NULL,
  `id_time` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `idTime_idx` (`id_time` ASC),
  CONSTRAINT `idTime`
    FOREIGN KEY (`id_time`)
    REFERENCES `campeonato`.`time` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `campeonato`.`classificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `campeonato`.`classificacao` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `id_time` INT NULL,
  `pontuacao` INT NULL,
  `vitorias` INT NULL,
  `derrotas` INT NULL,
  `empates` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `IdTime2_idx` (`id_time` ASC),
  CONSTRAINT `IdTime2`
    FOREIGN KEY (`id_time`)
    REFERENCES `campeonato`.`time` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

