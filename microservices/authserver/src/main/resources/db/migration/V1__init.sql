-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.7.13-log - MySQL Community Server (GPL)
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             9.3.0.4984
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Exportiere Struktur von Tabelle microservice4vaadin_authserverdb.acme_user
CREATE TABLE IF NOT EXISTS `acme_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` datetime NOT NULL,
  `email` varchar(100) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` datetime DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_euubumqfopfaeq6h6lf5fp26c` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle microservice4vaadin_authserverdb.authority
CREATE TABLE IF NOT EXISTS `authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt


-- Exportiere Struktur von Tabelle microservice4vaadin_authserverdb.user_2_authority
CREATE TABLE IF NOT EXISTS `user_2_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  KEY `FK_hyi4f2xn1uu9yw8fjw8c1o6q1` (`authority_id`),
  KEY `FK_kx2fk8fl1d32ta6lpp7rtq0hy` (`user_id`),
  CONSTRAINT `FK_hyi4f2xn1uu9yw8fjw8c1o6q1` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FK_kx2fk8fl1d32ta6lpp7rtq0hy` FOREIGN KEY (`user_id`) REFERENCES `acme_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Daten Export vom Benutzer nicht ausgewählt
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;

INSERT INTO AUTHORITY (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO AUTHORITY (id, name) VALUES (2, 'ROLE_ADMIN');

INSERT INTO ACME_USER (id, activated, activation_key, created_by, created_date, email, first_name, last_modified_by, last_modified_date, last_name, password) VALUES
(1, b'1', NULL, 'system', '2015-11-26 10:33:14', 'ttester@test.de', 'Udo', NULL, '2015-11-26 10:33:14', 'Tester', '$2a$04$E7J2pUf9rMKV5Sf6nT4wPOWynxT3vEb9Sy8RpmAS1gt9IqfZJSlFy');

INSERT INTO USER_2_AUTHORITY (user_id, authority_id) VALUES (1, 2);
