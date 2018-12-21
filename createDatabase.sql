DROP DATABASE IF EXISTS hotelmanagement;
CREATE DATABASE hotelmanagement;
USE hotelmanagement;

DROP TABLE IF EXISTS user;
CREATE TABLE user (
	username VARCHAR(20) NOT NULL, 
	password VARCHAR(25) NOT NULL, 
	firstName VARCHAR(20) NOT NULL, 
	lastName VARCHAR(20) NOT NULL,
	age INT NOT NULL,
    userRole ENUM ('Guest', 'Manager', 'Cleaning Service'),
	PRIMARY KEY (username)
);

DROP TABLE IF EXISTS room;
CREATE TABLE room (
	roomId INT(10) NOT NULL AUTO_INCREMENT,
	pricePerNight DOUBLE(10,2) NOT NULL,
	types VARCHAR(20) NOT NULL,
	PRIMARY KEY (roomId)
);

DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations (
	resId INT(10) NOT NULL AUTO_INCREMENT,
	roomId INT(10) NOT NULL,
	guest VARCHAR(15) NOT NULL,
	startDate DATE NOT NULL,
	endDate DATE NOT NULL,
	numOfDays INT(10) NOT NULL,
	totalPrice DOUBLE(10,2),
	roomKey BOOLEAN NOT NULL DEFAULT FALSE, /* checks if the room key has been returned or not */
	canceled BOOLEAN NOT NULL DEFAULT FALSE,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE current_timestamp,
	PRIMARY KEY (resId),
	FOREIGN KEY (roomId) references room (roomId) ON DELETE CASCADE,
	FOREIGN KEY (guest) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS cleaningservice;
CREATE TABLE cleaningservice (
	cleaningId INT(10) NOT NULL AUTO_INCREMENT,
	task VARCHAR(250) NOT NULL,
	roomId INT(10) NOT NULL,
    completedBy VARCHAR(20),
	resId INT(10) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	price DOUBLE(10,2) NOT NULL,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (cleaningId),
    FOREIGN KEY (completedBy) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (roomId) references room (roomId) ON DELETE CASCADE,
	FOREIGN KEY (resId) references reservations (resId) ON DELETE CASCADE
);

DROP TABLE IF EXISTS archive_cleaningservice;
CREATE TABLE archive_cleaningservice (
	cleaningId INT(10) NOT NULL AUTO_INCREMENT,
	task VARCHAR(250) NOT NULL,
	roomId INT(10) NOT NULL,
    completedBy VARCHAR(20),
	resId INT(10) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	price DOUBLE(10,2) NOT NULL,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (cleaningId),
    FOREIGN KEY (completedBy) references user (username) ON DELETE CASCADE,
	FOREIGN KEY (roomId) references room (roomId) ON DELETE CASCADE,
	FOREIGN KEY (resId) references reservations (resId) ON DELETE CASCADE
);

DROP TABLE IF EXISTS feedback;
CREATE TABLE feedback (
	feedbackId INT(10) NOT NULL AUTO_INCREMENT,
	guest VARCHAR(12) NOT NULL,
	feedback VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (feedbackId),
	FOREIGN KEY (guest) references user (username) ON DELETE CASCADE
);

DROP TABLE IF EXISTS archive_feedback;
CREATE TABLE archive_feedback (
	feedbackId INT(10) NOT NULL AUTO_INCREMENT,
	guest VARCHAR(12) NOT NULL,
	feedback VARCHAR(100) NOT NULL,
	time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updatedOn TIMESTAMP NOT NULL DEFAULT current_timestamp ON UPDATE CURRENT_TIMESTAMP,
	PRIMARY KEY (feedbackId),
	FOREIGN KEY (guest) references user (username) ON DELETE CASCADE
);