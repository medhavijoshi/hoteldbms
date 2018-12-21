DROP PROCEDURE IF EXISTS history;
DELIMITER %%;
CREATE PROCEDURE history
(IN time1 TIMESTAMP)
LANGUAGE SQL
DETERMINISTIC
COMMENT 'Stored procedure to send old feedbacks to archives table from the feedbacks table'
BEGIN
	START TRANSACTION;
		INSERT INTO archive_feedback(feedbackId,guest,feedback,time,updatedOn)
		SELECT feedbackId,guest,feedback,time,updatedOn
		FROM feedback
		WHERE DATE(updatedOn) <= time1;
       
		DELETE FROM FEEDBACK WHERE DATE(updatedOn) <= time1;
	COMMIT;
END;