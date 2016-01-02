-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               5.7.10-log - MySQL Community Server (GPL)
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             8.3.0.4694
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
-- Exportiere Daten aus Tabelle microservice4vaadin_authserverdb.acme_user: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `acme_user` DISABLE KEYS */;
INSERT INTO `acme_user` (`id`, `activated`, `activation_key`, `created_by`, `created_date`, `email`, `first_name`, `last_modified_by`, `last_modified_date`, `last_name`, `password`) VALUES
    (1, b'1', NULL, 'system', '2015-11-26 10:33:14', 'ttester@test.de', 'Udo', NULL, '2015-11-26 10:33:14', 'Tester', '$2a$04$E7J2pUf9rMKV5Sf6nT4wPOWynxT3vEb9Sy8RpmAS1gt9IqfZJSlFy');
/*!40000 ALTER TABLE `acme_user` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle microservice4vaadin_authserverdb.authority: ~2 rows (ungefähr)
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` (`id`, `name`) VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle microservice4vaadin_authserverdb.schema_version: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `schema_version` DISABLE KEYS */;
INSERT INTO `schema_version` (`version_rank`, `installed_rank`, `version`, `description`, `type`, `script`, `checksum`, `installed_by`, `installed_on`, `execution_time`, `success`) VALUES
    (1, 1, '1', 'init', 'SQL', 'V1__init.sql', -2137936499, 'root', '2015-11-26 09:00:25', 67, 0);
/*!40000 ALTER TABLE `schema_version` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle microservice4vaadin_authserverdb.user_2_authority: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `user_2_authority` DISABLE KEYS */;
INSERT INTO `user_2_authority` (`user_id`, `authority_id`) VALUES
    (1, 2);
/*!40000 ALTER TABLE `user_2_authority` ENABLE KEYS */;

-- Exportiere Daten aus Tabelle microservice4vaadin_authserverdb.user_authority: ~0 rows (ungefähr)
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
