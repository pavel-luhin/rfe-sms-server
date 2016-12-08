UPDATE `sms_template`
SET
  `template` = '${STUDENT_FIRST_NAME}, ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_TIME} по адресу ${COMPANY_ADDRESS}'
WHERE
  `sms_type` = 'InterviewSMS';
INSERT INTO `email_template` (subject, content, created_by, created_date, sms_template) VALUES (
  'Вас пригласили на собеседование',
  'Здравствуйте, ${STUDENT_FIRST_NAME} ${STUDENT_LAST_NAME},<br>Компания ${COMPANY_NAME}
  приглашает Вас на собеседование ${INTERVIEW_DATE} по адресу ${COMPANY_ADDRESS}',
  'SYSTEM',
  NOW(),
  LAST_INSERT_ID());

SET @sms_template_id = (SELECT id FROM `sms_template` WHERE `sms_type` = 'RegisterUserSMS');

UPDATE `email_template`
SET
  `content` = 'Здравствуйте, ${STUDENT_FIRST_NAME} ${STUDENT_LAST_NAME},<br>Компания ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_TIME} по адресу ${COMPANY_ADDRESS}'
WHERE
  `sms_template` = @sms_template_id;