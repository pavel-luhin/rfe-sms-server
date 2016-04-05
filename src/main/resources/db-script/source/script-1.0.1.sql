ALTER TABLE `email_template` MODIFY `content` VARCHAR(10000);
ALTER TABLE `sms_template` MODIFY `template` VARCHAR(1000);

SET @sms_type_id = (SELECT `id` FROM `sms_type` WHERE `sms_type`='InterviewSMS');
INSERT INTO `email_template`(`sms_type`, `subject`, `content`)
  values(@sms_type_id, 'Вас пригласили на собеседование',
         'Здравствуйте, STUDENT_LAST_NAME STUDENT_FIRST_NAME<br><br>Компания COMPANY_NAME приглашает Вас на собеседование в INTERVIEW_DATE по адресу COMPANY_ADDRESS.<br><br>С уважением, команда cv.bsu.by');