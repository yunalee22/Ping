DROP DATABASE IF EXISTS Ping;
CREATE DATABASE Ping;
USE Ping;

CREATE TABLE Homes (
	homeID int(11) primary key not null auto_increment,
    homename varchar(100) not null,
    address varchar(100) not null
    
);

CREATE TABLE Users (
	userID int(11) primary key not null auto_increment,
    name text not null,
    username varchar(100) not null,
    password_ varchar(100) not null,
    homeID int(11) not null,
    FOREIGN KEY fk(homeID) REFERENCES Homes(homeID)
);


CREATE TABLE Events_ (
	eventID int(11) primary key not null auto_increment,
    date_ varchar(20) not null,
    description text not null,
    homeID int(11) not null,
    FOREIGN KEY fk(homeID) REFERENCES Homes(homeID)
);

CREATE TABLE Chats (
    message text not null,
    senderID int(11) not null,
    homeID int(11) not null,
    FOREIGN KEY fk1(senderID) REFERENCES Users(userID),
    FOREIGN KEY fk2(homeID) REFERENCES Homes(homeID)
);

CREATE TABLE Pings (
	message text not null,
	senderID int(11) not null,
    recipientID int(11) not null,
    FOREIGN KEY fk1(senderID) REFERENCES Users(userID),
    FOREIGN KEY fk2(recipientID) REFERENCES Users(userID)
);

INSERT INTO Homes(homename, address) VALUES("Icon 101", "1234 Loop Drive");
INSERT INTO Homes(homename, address) VALUES("Icon 102", "1235 Loop Drive");
INSERT INTO Homes(homename, address) VALUES("Icon 103", "1236 Loop Drive");
INSERT INTO Users(name, username, password_, homeID) VALUES("Guest", "guest1", "guest", 1);
INSERT INTO Users(name, username, password_, homeID) VALUES("Guest", "guest2", "guest", 2);
INSERT INTO Users(name, username, password_, homeID) VALUES("Guest", "guest3", "guest", 3);
INSERT INTO Users(name, username, password_, homeID) VALUES("Yuna Lee", "yunalee22", "poop", 1);
INSERT INTO Users(name, username, password_, homeID) VALUES("Sarah Wu", "sarahlwu", "poop", 1);
INSERT INTO Events_(date_, description, homeID) VALUES("08 08 2016", "New roommate party", 1);
INSERT INTO Chats(message, senderID, homeID) VALUES("What's up frands?", 2, 1);
INSERT INTO Chats(message, senderID, homeID) VALUES("You suck!", 3, 1);
INSERT INTO Pings(message, senderID, recipientID) VALUES("Out of toilet paper", 2, 1);
