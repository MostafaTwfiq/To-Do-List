DROP DATABASE IF EXISTS todolistdb;

-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema todoListDB
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema todoListDB
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `todoListDB` DEFAULT CHARACTER SET utf8 ;
USE `todoListDB` ;

-- -----------------------------------------------------
-- Table `todoListDB`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoListDB`.`users` (
  `user_id` SMALLINT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(50) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  `theme` ENUM('LightTheme', 'DarkTheme', 'PurkTheme') NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `todoListDB`.`tasks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoListDB`.`tasks` (
  `task_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` SMALLINT NOT NULL,
  `task_title` VARCHAR(50) NOT NULL,
  `datetime` DATETIME NOT NULL,
  `task_status` ENUM('DONE', 'NOT_DONE') NOT NULL,
  `task_priority` ENUM('IMPORTANT_AND_URGENT', 'IMPORTANT_AND_NOT_URGENT', 'NOT_IMPORTANT_AND_URGENT', 'NOT_IMPORTANT_AND_NOT_URGENT') NOT NULL,
  `is_starred` TINYINT NOT NULL,
  INDEX `fk_tasks_users_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`task_id`),
  CONSTRAINT `fk_tasks_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `todoListDB`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `todoListDB`.`tags`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoListDB`.`tags` (
  `task_id` INT NOT NULL,
  `tag` VARCHAR(50) NOT NULL,
  INDEX `fk_tags_tasks1_idx` (`task_id` ASC) VISIBLE,
  CONSTRAINT `fk_tags_tasks`
    FOREIGN KEY (`task_id`)
    REFERENCES `todoListDB`.`tasks` (`task_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `todoListDB`.`notes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoListDB`.`notes` (
  `task_id` INT NOT NULL,
  `note` VARCHAR(50) NOT NULL,
  INDEX `fk_notes_tasks1_idx` (`task_id` ASC) VISIBLE,
  CONSTRAINT `fk_notes_tasks`
    FOREIGN KEY (`task_id`)
    REFERENCES `todoListDB`.`tasks` (`task_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `todoListDB`.`users_images`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `todoListDB`.`users_images` (
  `user_id` SMALLINT NOT NULL,
  `path` VARCHAR(260) NOT NULL,
  INDEX `fk_users_images_users1_idx` (`user_id` ASC) VISIBLE,
  PRIMARY KEY (`user_id`),
  CONSTRAINT `fk_users_images_users`
    FOREIGN KEY (`user_id`)
    REFERENCES `todoListDB`.`users` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

USE todoListDB;

-- Create users table search index
-- DROP INDEX IF EXISTS idx_user_name_password ON users;
CREATE INDEX idx_user_name_password ON users(user_name, password);
-- SHOW INDEXES IN users;


/* Start of filtering data functions*/

-- Drop and create procedure to return the id number of a user if found (this procedure will be useful while logging in).
DROP PROCEDURE IF EXISTS get_user_id;
DELIMITER $$
CREATE PROCEDURE get_user_id ( user_name VARCHAR(50), password VARCHAR(100) )
BEGIN
SELECT user_id FROM users WHERE BINARY users.user_name = user_name AND BINARY users.password = password; 
END $$
DELIMITER ;


-- Drop and create procedure to return the id number of a user if found (this procedure will be useful while logging in).
DROP PROCEDURE IF EXISTS get_user_id_by_user_name;
DELIMITER $$
CREATE PROCEDURE get_user_id_by_user_name ( user_name VARCHAR(50) )
BEGIN
SELECT user_id FROM users WHERE BINARY users.user_name = user_name;
END $$
DELIMITER ;


-- Drop and create procedure to return the user name with the passed id.
DROP PROCEDURE IF EXISTS get_user_name;
DELIMITER $$
CREATE PROCEDURE get_user_name ( user_id SMALLINT )
BEGIN
SELECT user_name FROM users WHERE users.user_id = user_id; 
END $$
DELIMITER ;


-- Drop and create procedure to return the user name with the passed id.
DROP PROCEDURE IF EXISTS get_user_password;
DELIMITER $$
CREATE PROCEDURE get_user_password ( user_id SMALLINT )
BEGIN
SELECT users.password FROM users WHERE users.user_id = user_id; 
END $$
DELIMITER ;

-- This function will return the path of the user image for the passed user id.
DROP FUNCTION IF EXISTS get_user_image_path;
DELIMITER $$
CREATE FUNCTION get_user_image_path( user_id SMALLINT )
RETURNS VARCHAR(260)
READS SQL DATA
BEGIN

DECLARE image_path VARCHAR(260) DEFAULT NULL;

SELECT path INTO image_path FROM users_images ui WHERE ui.user_id = user_id;

RETURN image_path;
 
END $$
DELIMITER ;



-- This function will return the theme of the user with the passed id.
DROP FUNCTION IF EXISTS get_user_theme;
DELIMITER $$
CREATE FUNCTION get_user_theme( user_id SMALLINT )
RETURNS VARCHAR(15)
READS SQL DATA
BEGIN

DECLARE user_theme VARCHAR(15) DEFAULT NULL;

SELECT theme INTO user_theme FROM users u WHERE u.user_id = user_id;

RETURN user_theme;
 
END $$
DELIMITER ;



-- Drop and create view to view the tasks table with notes and tags tables.
DROP VIEW IF EXISTS view_full_tasks_table;

CREATE VIEW view_full_tasks_table AS
SELECT 
	* 
FROM 
	tasks t
LEFT JOIN notes n USING (task_id)
LEFT JOIN tags tg USING (task_id)
ORDER BY task_id;



-- This procedure will return ids of the tasks linked to a user id on a specific day.
DROP PROCEDURE IF EXISTS get_user_tasks_ids_by_user_id_date;
DELIMITER $$
CREATE PROCEDURE get_user_tasks_ids_by_user_id_date ( user_id SMALLINT, datetime DATETIME )
BEGIN
SELECT 
	DISTINCT task_id
FROM tasks t
WHERE 
	t.user_id = user_id 
    AND SUBSTRING(t.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);
END $$
DELIMITER ;


-- This procedure will return tasks ids for a specific user, using specific tags and day. 
DROP PROCEDURE IF EXISTS get_user_tasks_ids_by_user_id_tags_date;
DELIMITER $$
CREATE PROCEDURE get_user_tasks_ids_by_user_id_tags_date ( user_id SMALLINT, tags VARCHAR(21845), datetime DATETIME )
BEGIN

IF tags IS NULL THEN 
SET tags = '.*?';
END IF;

SELECT 
	DISTINCT tk.task_id 
FROM tasks tk 
JOIN tags tg USING (task_id) 
WHERE 
	tg.tag REGEXP CONCAT('^(' , tags , ')$')
	AND tk.user_id = user_id 
    AND SUBSTRING(tk.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);

END $$
DELIMITER ;



-- This procedure will return tasks ids for a specific user, using specific title rgx and day. 
DROP PROCEDURE IF EXISTS get_user_tasks_ids_by_user_id_title_rgx_date;
DELIMITER $$
CREATE PROCEDURE get_user_tasks_ids_by_user_id_title_rgx_date ( user_id SMALLINT, title_rgx VARCHAR(50), datetime DATETIME )
BEGIN

SELECT 
	task_id 
FROM tasks 
WHERE 
	task_title REGEXP CONCAT('^' , title_rgx , '(.*?)$')
	AND tasks.user_id = user_id 
    AND SUBSTRING(tasks.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);

END $$
DELIMITER ;



-- This procedure will return tasks ids for a specific user, using specific title rgx, tags and day. 
DROP PROCEDURE IF EXISTS get_user_tasks_ids_by_user_id_title_rgx_tags_date;
DELIMITER $$
CREATE PROCEDURE get_user_tasks_ids_by_user_id_title_rgx_tags_date ( user_id SMALLINT, title_rgx VARCHAR(50), tags VARCHAR(21845), datetime DATETIME )
BEGIN

IF tags IS NULL THEN 
SET tags = '.*?';
END IF;

SELECT 
	DISTINCT tk.task_id 
FROM tasks tk 
JOIN tags tg USING (task_id) 
WHERE 
	tg.tag REGEXP CONCAT('^(' , tags , ')$')
    AND tk.task_title REGEXP CONCAT('^', title_rgx, '(.*?)$') 
	AND tk.user_id = user_id 
    AND SUBSTRING(tk.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);

END $$
DELIMITER ;



-- This procedure will return tasks ids for a specific user, using specific title rgx, tags and day. 
DROP PROCEDURE IF EXISTS get_user_tasks_ids_full_search;
DELIMITER $$
CREATE PROCEDURE get_user_tasks_ids_full_search ( 
	user_id SMALLINT, 
	title_rgx VARCHAR(50), 
	tags VARCHAR(21845),
    statuses VARCHAR(15),
    priorities VARCHAR(120),
	datetime DATETIME,
    is_starred BOOL
)
BEGIN

IF tags IS NULL THEN 
SET tags = '.*?';
END IF;

IF statuses IS NULL THEN 
SET statuses = '.*?';
END IF;

IF priorities IS NULL THEN 
SET priorities = '.*?';
END IF;

SELECT 
	DISTINCT tk.task_id 
FROM tasks tk 
LEFT JOIN tags tg USING (task_id) 
WHERE 
	IF (tg.tag IS NULL, tags = '.*?', tg.tag REGEXP CONCAT('^(' , tags , ')$'))
    AND tk.task_status REGEXP CONCAT('^(' , statuses , ')$')
    AND tk.task_priority REGEXP CONCAT('^(' , priorities , ')$')
    AND tk.is_starred = IF(is_starred IS NOT NULL, is_starred, tk.is_starred)
    AND tk.task_title REGEXP CONCAT('^', title_rgx, '(.*?)$')
	AND tk.user_id = user_id 
    AND SUBSTRING(tk.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);

END $$
DELIMITER ;



-- This procedure will return the information of a task without it's id.
DROP PROCEDURE IF EXISTS get_task_info_by_task_id;
DELIMITER $$
CREATE PROCEDURE get_task_info_by_task_id ( task_id INT )
BEGIN
SELECT 
    task_title, 
    datetime,
    task_status, 
    task_priority,
    is_starred
FROM tasks t
WHERE t.task_id = task_id;
END $$
DELIMITER ;


-- This procedure will return a all the notes for a specific task.
DROP PROCEDURE IF EXISTS get_task_notes;
DELIMITER $$
CREATE PROCEDURE get_task_notes ( task_id INT )
BEGIN

SELECT 
	note
FROM notes n
WHERE n.task_id = task_id;
    
END $$
DELIMITER ;


-- This procedure will return all the tags for a specific task
DROP PROCEDURE IF EXISTS get_task_tags;
DELIMITER $$
CREATE PROCEDURE get_task_tags ( task_id INT )
BEGIN

SELECT 
	tag
FROM tags n
WHERE n.task_id = task_id;
    
END $$
DELIMITER ;


-- This function will return the number of tasks for a user on a specific date.
DROP FUNCTION IF EXISTS get_user_tasks_num_at_date;
DELIMITER $$
CREATE FUNCTION get_user_tasks_num_at_date (user_id SMALLINT, datetime DATETIME)
RETURNS INT
READS SQL DATA
BEGIN
DECLARE count INT DEFAULT 0;

SELECT 
	COUNT(DISTINCT task_id) INTO count
FROM view_full_tasks_table vt
WHERE 
	vt.user_id = user_id 
    AND SUBSTRING(vt.datetime, 1, 10) = SUBSTRING(datetime, 1, 10);
    
RETURN count;
END $$
DELIMITER ;

/* End of filtring data functions*/



/* Start of inserting data functions*/

-- This procedure will insert a new user in the users table.
DROP PROCEDURE IF EXISTS add_new_user;
DELIMITER $$
CREATE PROCEDURE add_new_user(user_name VARCHAR(50), pass VARCHAR(100), theme VARCHAR(15))
BEGIN

INSERT INTO users VALUES (DEFAULT, user_name, pass, theme);

END $$
DELIMITER ;


-- This procedure will insert a new user image in the users images table.
DROP PROCEDURE IF EXISTS add_new_user_image;
DELIMITER $$
CREATE PROCEDURE add_new_user_image( user_id SMALLINT, image_path VARCHAR(260) )
BEGIN

INSERT INTO users_images VALUES (user_id, image_path);

END $$
DELIMITER ;


-- This procedure will insert a new task in the tasks table.
DROP PROCEDURE IF EXISTS add_new_task;
DELIMITER $$
CREATE PROCEDURE add_new_task(user_id SMALLINT, task_title VARCHAR(50), task_datetime DATETIME, task_status VARCHAR(10), task_priority VARCHAR(30), is_starred BOOL)
BEGIN

INSERT INTO tasks VALUES (DEFAULT, user_id, task_title, task_datetime, task_status, task_priority, is_starred);

END $$
DELIMITER ;


-- This procedure will insert a new note in the notes table.
DROP PROCEDURE IF EXISTS add_new_note;
DELIMITER $$
CREATE PROCEDURE add_new_note(task_id INT, note VARCHAR(50))
BEGIN

INSERT INTO notes VALUES (task_id, note);

END $$
DELIMITER ;

-- This procedure will insert a new tag in the tags table.
DROP PROCEDURE IF EXISTS add_new_tag;
DELIMITER $$
CREATE PROCEDURE add_new_tag(task_id INT, tag VARCHAR(50))
BEGIN

INSERT INTO tags VALUES (task_id, tag);

END $$
DELIMITER ;

/* End of inserting data functions*/


/* Start of deleting data functions*/

-- This procedure will delete the user with the passed id.
DROP PROCEDURE IF EXISTS delete_user;
DELIMITER $$
CREATE PROCEDURE delete_user(user_id SMALLINT)
BEGIN

DELETE FROM users WHERE users.user_id = user_id;

END $$
DELIMITER ;


-- This procedure will delete the user image with the passed id.
DROP PROCEDURE IF EXISTS delete_user_image;
DELIMITER $$
CREATE PROCEDURE delete_user_image(user_id SMALLINT)
BEGIN

DELETE FROM users_images WHERE users_images.user_id = user_id;

END $$
DELIMITER ;


-- This procedure will delete the task with the passed id.
DROP PROCEDURE IF EXISTS delete_task;
DELIMITER $$
CREATE PROCEDURE delete_task(task_id INT)
BEGIN

DELETE FROM tasks WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will delete the passed task id and tag.
DROP PROCEDURE IF EXISTS delete_tag;
DELIMITER $$
CREATE PROCEDURE delete_tag(task_id INT, tag VARCHAR(50))
BEGIN

DELETE FROM tags WHERE tags.task_id = task_id AND tags.tag = tag;

END $$
DELIMITER ;


-- This procedure will delete the passed task id and note.
DROP PROCEDURE IF EXISTS delete_note;
DELIMITER $$
CREATE PROCEDURE delete_note(task_id INT, note VARCHAR(50))
BEGIN

DELETE FROM notes WHERE notes.task_id = task_id AND notes.note = note;

END $$
DELIMITER ;


/* End of deleting data functions*/

/* Start of updating data functions*/

-- This procedure will update the user name with the passed id.
DROP PROCEDURE IF EXISTS update_user_name;
DELIMITER $$
CREATE PROCEDURE update_user_name(user_id SMALLINT, new_user_name VARCHAR(50))
BEGIN

UPDATE users SET user_name = new_user_name WHERE users.user_id = user_id;

END $$
DELIMITER ;

-- This procedure will update the user password with the passed id.
DROP PROCEDURE IF EXISTS update_user_password;
DELIMITER $$
CREATE PROCEDURE update_user_password(user_id SMALLINT, new_pass VARCHAR(100))
BEGIN

UPDATE users SET users.password = new_pass WHERE users.user_id = user_id;

END $$
DELIMITER ;


-- This procedure will update the user theme with the passed id.
DROP PROCEDURE IF EXISTS update_user_theme;
DELIMITER $$
CREATE PROCEDURE update_user_theme(user_id SMALLINT, new_theme VARCHAR(15))
BEGIN

UPDATE users SET users.theme = new_theme WHERE users.user_id = user_id;

END $$
DELIMITER ;


-- This procedure will update the user image with the passed id.
DROP PROCEDURE IF EXISTS update_user_image;
DELIMITER $$
CREATE PROCEDURE update_user_image(user_id SMALLINT, new_path VARCHAR(260))
BEGIN

UPDATE users_images SET users_images.path = new_path WHERE users_images.user_id = user_id;

END $$
DELIMITER ;


-- This procedure will update the task title with the passed id.
DROP PROCEDURE IF EXISTS update_task_title;
DELIMITER $$
CREATE PROCEDURE update_task_title(task_id INT, new_title VARCHAR(50))
BEGIN

UPDATE tasks SET task_title = new_title WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update the task datetime with the passed id.
DROP PROCEDURE IF EXISTS update_task_datetime;
DELIMITER $$
CREATE PROCEDURE update_task_datetime(task_id INT, new_datetime DATETIME)
BEGIN

UPDATE tasks SET tasks.datetime = new_datetime WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update the task status with the passed id.
DROP PROCEDURE IF EXISTS update_task_status;
DELIMITER $$
CREATE PROCEDURE update_task_status(task_id INT, new_status VARCHAR(10))
BEGIN

UPDATE tasks SET task_status = new_status WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update the task priority with the passed id.
DROP PROCEDURE IF EXISTS update_task_priority;
DELIMITER $$
CREATE PROCEDURE update_task_priority(task_id INT, new_priority VARCHAR(30))
BEGIN

UPDATE tasks SET task_priority = new_priority WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update the task is starred boolean for the task with the passed id.
DROP PROCEDURE IF EXISTS update_task_is_starred;
DELIMITER $$
CREATE PROCEDURE update_task_is_starred(task_id INT, new_is_starred BOOL)
BEGIN

UPDATE tasks SET is_starred = new_is_starred WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update the task with the passed id.
DROP PROCEDURE IF EXISTS update_task;
DELIMITER $$
CREATE PROCEDURE update_task(task_id INT, new_title VARCHAR(50), new_datetime DATETIME, new_status VARCHAR(10), new_priority VARCHAR(30), new_is_starred BOOL)
BEGIN

UPDATE tasks SET 
	task_title = new_title, 
    tasks.datetime = new_datetime,  
    task_status = new_status,
    task_priority = new_priority,
    is_starred = new_is_starred
    WHERE tasks.task_id = task_id;

END $$
DELIMITER ;


-- This procedure will update a tag for a specific task.
DROP PROCEDURE IF EXISTS update_tag;
DELIMITER $$
CREATE PROCEDURE update_tag(task_id INT, old_tag VARCHAR(50), new_tag VARCHAR(50))
BEGIN

UPDATE tags SET tag = new_tag WHERE tags.task_id = task_id AND tag = old_tag;

END $$
DELIMITER ;


-- This procedure will update a note for a spesific task.
DROP PROCEDURE IF EXISTS update_note;
DELIMITER $$
CREATE PROCEDURE update_note(task_id INT, old_note VARCHAR(50), new_note VARCHAR(50))
BEGIN

UPDATE notes SET note = new_note WHERE notes.task_id = task_id AND note = old_note;

END $$
DELIMITER ;

/* End of updating data functions*/


/** Start check if exists functions*/

-- This function will check if the passed user id exists or not.
DROP FUNCTION IF EXISTS user_id_exists;
DELIMITER $$ 
CREATE FUNCTION user_id_exists ( user_id SMALLINT )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (SELECT us.user_id FROM users us WHERE us.user_id = user_id) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;

-- This function will check if the passed user name exisits or not.
DROP FUNCTION IF EXISTS user_name_exists;
DELIMITER $$ 
CREATE FUNCTION user_name_exists ( user_name VARCHAR(50) )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (SELECT us.user_name FROM users us WHERE BINARY us.user_name = user_name) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;

-- This function will check if the passed task id exisits or not.
DROP FUNCTION IF EXISTS task_id_exists;
DELIMITER $$ 
CREATE FUNCTION task_id_exists ( task_id INT )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (SELECT t.task_id FROM tasks t WHERE t.task_id = task_id) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;

-- This function will check if the passed task title exisits or not for the passed user id on the passed day.
DROP FUNCTION IF EXISTS task_title_exists;
DELIMITER $$ 
CREATE FUNCTION task_title_exists ( user_id SMALLINT, title VARCHAR(50), task_datetime DATETIME )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (

				SELECT t.task_title 
				FROM tasks t 
                WHERE 
					t.user_id = user_id 
                    AND t.task_title = title
                    AND SUBSTRING(t.datetime, 1, 10) = SUBSTRING(task_datetime, 1, 10)
                    
			) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;


-- This function will check if the passed note exisits or not for the passed task id.
DROP FUNCTION IF EXISTS note_exists;
DELIMITER $$ 
CREATE FUNCTION note_exists ( task_id INT, note VARCHAR(50) )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (SELECT n.task_id FROM notes n WHERE n.task_id = task_id AND n.note = note) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;


-- This function will check if the passed tag exisits or not for the passed task id.
DROP FUNCTION IF EXISTS tag_exists;
DELIMITER $$ 
CREATE FUNCTION tag_exists ( task_id INT, tag VARCHAR(50) )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE found_flag BOOL DEFAULT FALSE;
SELECT EXISTS (SELECT t.task_id FROM tags t WHERE t.task_id = task_id AND t.tag = tag) INTO found_flag;

RETURN found_flag;

END $$
DELIMITER ;


-- This function will return true if there is an image for the passed user id.
DROP FUNCTION IF EXISTS user_image_exists;
DELIMITER $$
CREATE FUNCTION user_image_exists( user_id SMALLINT )
RETURNS BOOL
READS SQL DATA
BEGIN

DECLARE exists_bool BOOL DEFAULT FALSE;

SELECT EXISTS ( SELECT user_id FROM users_images ui WHERE ui.user_id = user_id ) INTO exists_bool;

RETURN exists_bool;
 
END $$
DELIMITER ;


/** Start check if exists functions*/
CREATE USER IF NOT EXISTS 'todoListApp'@'localhost' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON *.* TO 'todoListApp'@'localhost' WITH GRANT OPTION;

CREATE USER IF NOT EXISTS 'todoListApp'@'%' IDENTIFIED BY 'pass';
GRANT ALL PRIVILEGES ON *.* TO 'todoListApp'@'%' WITH GRANT OPTION;

FLUSH PRIVILEGES;