INSERT INTO `sms_template` (sms_type, template, uri_path, created_by, created_date) VALUES
  ('InterviewSMS',
   '${STUDENT_FIRST_NAME}, ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_DATE} по адресу ${INTERVIEW_TIME}',
   '/rest/sms/interview', 'SYSTEM', NOW());
UPDATE `sms_template`
SET
  `template` = '${STUDENT_FIRST_NAME}, ${COMPANY_NAME} приглашает Вас на собеседование ${INTERVIEW_DATE} по адресу ${COMPANY_ADDRESS}'
WHERE
  `sms_type` = 'InterviewSMS';