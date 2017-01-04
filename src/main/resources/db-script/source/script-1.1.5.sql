UPDATE `sms_template`
SET
  `template` = '${STUDENT_FIRST_NAME}, ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_TIME} по адресу ${COMPANY_ADDRESS}'
WHERE
  `sms_type` = 'InterviewSMS';

SET @sms_template_id = (SELECT id FROM `sms_template` WHERE `sms_type` = 'InterviewSMS');

UPDATE `email_template`
SET
  `content` = 'Здравствуйте, ${STUDENT_FIRST_NAME} ${STUDENT_LAST_NAME},<br>Компания ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_TIME} по адресу ${COMPANY_ADDRESS}'
WHERE
  `sms_template` = @sms_template_id;

UPDATE `sms_template`
SET
  enabled = FALSE
WHERE
  `sms_type` = 'CustomSMS';