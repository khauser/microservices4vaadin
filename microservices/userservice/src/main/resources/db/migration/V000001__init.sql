-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.5.27 - MySQL Community Server (GPL)
-- Server Betriebssystem:        Win32
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE TABLE `user` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(50) NULL DEFAULT NULL,
  `gender` INT(11) NULL DEFAULT NULL,
  `last_modified_by` VARCHAR(50) NULL DEFAULT NULL,
  `last_modified_date_time` DATETIME NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NULL DEFAULT NULL,
  `phone` VARCHAR(255) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `language` VARCHAR(5) NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
)
COLLATE='utf8_general_ci'
ENGINE=InnoDB;

-- Exportiere Daten aus Tabelle lessortestdb.user: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`id`, `first_name`, `gender`, `last_name`, `phone`, `title`, `language`) VALUES
  (1, 'Test', 'MALE', 'Test', '123', 'Dr.', 'en');
INSERT INTO `user` (`id`, `first_name`, `gender`, `last_name`, `phone`, `title`, `language`) VALUES
  (2, 'Test', 'MALE', 'Test', NULL, NULL, 'de');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
