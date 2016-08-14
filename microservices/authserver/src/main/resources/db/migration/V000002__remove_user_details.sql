ALTER TABLE `acme_user`
  DROP COLUMN `first_name`,
  DROP COLUMN `last_name`,
  DROP COLUMN `last_modified_by`,
  DROP COLUMN `last_modified_date`;

ALTER TABLE `acme_user`
  CHANGE COLUMN `created_date` `created_date_time` DATETIME NOT NULL AFTER `created_by`;
