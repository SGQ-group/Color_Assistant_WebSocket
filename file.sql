CREATE TABLE `combo_colors` (
  `id_col` INT NOT NULL AUTO_INCREMENT,
  `col_1` VARCHAR(8) NOT NULL,
  `col_2` VARCHAR(8) NULL,
  `col_3` VARCHAR(8) NULL,
  `col_4` VARCHAR(8) NULL,
  `col_5` VARCHAR(8) NULL,
  `like` INT NOT NULL,
  `check` INT NOT NULL,
  PRIMARY KEY (`id_col`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


CREATE TABLE `update` (
  `check` INT NOT NULL,
  PRIMARY KEY (`check`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;
