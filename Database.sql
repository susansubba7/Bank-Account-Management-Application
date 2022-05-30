DROP DATABASE Bank;

CREATE DATABASE Bank; 

USE Bank; 

CREATE TABLE CustomerAccount(
    UserName VARCHAR(255) NOT NULL UNIQUE,
    UserPassword VARCHAR(255) NOT NULL, 
    AccountNum CHAR(8) NOT NULL UNIQUE,
    Balance FLOAT DEFAULT 0.0, 
    CONSTRAINT PK_CustomerAccount PRIMARY KEY (AccountNum) 
);

CREATE TABLE CustomerInfo(
    AccountNum CHAR(8),
    FirstName VARCHAR(255) NOT NULL,
    LastName VARCHAR(255) NOT NULL,
    HomeAddress VARCHAR(255) NOT NULL,
    SSN CHAR(9) NOT NULL UNIQUE,
    DOB DATE NOT NULL,
    PhoneNumber CHAR(10) NOT NULL,
    Email VARCHAR(255) DEFAULT 'None',
    QuestionOne VARCHAR(255) NOT NULL,
    AnswerOne VARCHAR(255) NOT NULL,
    QuestionTwo VARCHAR(255) NOT NULL,
    AnswerTwo VARCHAR(255) NOT NULL,
    CONSTRAINT PK_CustomerInfo PRIMARY KEY (SSN),
    CONSTRAINT FK_CustomerInfo_CustomerAccount FOREIGN KEY (AccountNum) REFERENCES CustomerAccount(AccountNum)
);

CREATE TABLE CustomerStatement(
    AccountNum CHAR(8),
    ActivityDate DATE NOT NULL,
    ActivityAmount FLOAT NOT NULL,
    ActivityTitle VARCHAR(255) NOT NULL
);