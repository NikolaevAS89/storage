CREATE  TABLE users (
  username VARCHAR(50) NOT NULL ,
  password VARCHAR(100) NOT NULL ,
  enabled boolean DEFAULT true  NOT NULL ,
  PRIMARY KEY (username));

CREATE TABLE authorities (
  username varchar(45) NOT NULL,
  authority varchar(45) NOT NULL,
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));