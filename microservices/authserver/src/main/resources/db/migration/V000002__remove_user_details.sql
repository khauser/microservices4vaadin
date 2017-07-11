ALTER TABLE `acme_user`
  DROP COLUMN `first_name`,
  DROP COLUMN `last_name`,
  DROP COLUMN `last_modified_by`,
  DROP COLUMN `last_modified_date`;

ALTER TABLE `acme_user`
  CHANGE `created_date` `created_date_time` DATETIME AFTER `created_by`;

ALTER TABLE `acme_user` MODIFY `created_date_time` DATETIME NOT NULL;
