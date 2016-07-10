USE sms_server;

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `authentication_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expires` datetime DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id` (`user_id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON UPDATE CASCADE
) DEFAULT CHARSET=utf8;

CREATE TABLE `credentials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `api_key` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `sms_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sms_type` varchar(255) NOT NULL,
  `credentials_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_creds_id_sms_type` (`credentials_id`),
  CONSTRAINT `fk_creds_id_sms_type` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `sms_template` (
  `sms_id` int(11) NOT NULL AUTO_INCREMENT,
  `template` varchar(255) NOT NULL,
  `sms_type` int(11) NOT NULL,
  PRIMARY KEY (`sms_id`),
  KEY `fk_sms_type_id_sms_template` (`sms_type`),
  CONSTRAINT `fk_sms_type_id_sms_template` FOREIGN KEY (`sms_type`) REFERENCES `sms_type` (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `email_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `sms_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_sms_type_id_email` (`sms_type`),
  CONSTRAINT `fk_sms_type_id_email` FOREIGN KEY (`sms_type`) REFERENCES `sms_type` (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `statistics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `error` bit(1) DEFAULT NULL,
  `recipient` varchar(255) DEFAULT NULL,
  `recipient_type` varchar(255) DEFAULT NULL,
  `response` varchar(255) DEFAULT NULL,
  `sent_date` datetime DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `credentials_id` int(11) DEFAULT NULL,
  `sms_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_creds_stats` (`credentials_id`),
  KEY `fk_sms_type_id_stats` (`sms_type`),
  CONSTRAINT `fk_sms_type_id_stats` FOREIGN KEY (`sms_type`) REFERENCES `sms_type` (`id`),
  CONSTRAINT `fk_creds_stats` FOREIGN KEY (`credentials_id`) REFERENCES `credentials` (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `person` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `recipient_group` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) DEFAULT CHARSET=utf8;

CREATE TABLE `persons_have_groups` (
  `person_id` int(11) NOT NULL,
  `group_id` int(11) NOT NULL,
  KEY `fk_group_id` (`group_id`),
  KEY `fk_person_id` (`person_id`),
  CONSTRAINT `fk_person_id` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `fk_group_id` FOREIGN KEY (`group_id`) REFERENCES `recipient_group` (`id`)
) DEFAULT CHARSET=utf8;

INSERT INTO `credentials`(`username`, `sender`, `api_key`) VALUES('xinevic@gmail.com', 'Vizitka', 'WYRMVoSS0Z');
SET @creds_id = (SELECT `id` FROM credentials WHERE username='xinevic@gmail.com');
INSERT INTO `sms_type`(`sms_type`, `credentials_id`) values('InterviewSMS', @creds_id);
SET @sms_type_id = (SELECT `id` FROM `sms_type` WHERE `sms_type`='InterviewSMS');
INSERT INTO `sms_template`(`sms_type`, `template`)
  values(@sms_type_id, 'STUDENT_FIRST_NAME, COMPANY_NAME приглашает Вас на собеседование в INTERVIEW_DATE по адресу COMPANY_ADDRESS');

INSERT INTO `sms_type`(`sms_type`, `credentials_id`) VALUES('CustomSMS', @creds_id);