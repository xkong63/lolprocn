DROP DATABASE lolDB;
CREATE DATABASE lolDB;
USE lolDB;

CREATE TABLE playerMatchs(
summonerId INT UNSIGNED,
gameId INT UNSIGNED,
PRIMARY KEY(summonerId)
);

CREATE TABLE matches(
gameId INT UNSIGNED,
createDate INT UNSIGNED,
summoner1_id INT UNSIGNED,
summoner2_id INT UNSIGNED,
summoner3_id INT UNSIGNED,
summoner4_id INT UNSIGNED,
summoner5_id INT UNSIGNED,
summoner6_id INT UNSIGNED,
summoner7_id INT UNSIGNED,
summoner8_id INT UNSIGNED,
summoner9_id INT UNSIGNED,
summoner10_id INT UNSIGNED,
gameMode VARCHAR(20),
mageType VARCHAR(20),
invald BOOLEAN,
mapId INT UNSIGNED,
subType VARCHAR(20),
PRIMARY KEY(gameId)
);

CREATE TABLE  match_rawStats(
gameId INT UNSIGNED,
summonerId INT UNSIGNED,
assists	INT UNSIGNED,
championsKilled INT UNSIGNED,
item0 INT UNSIGNED,	
item1 INT UNSIGNED,	
item2 INT UNSIGNED,	
item3 INT UNSIGNED,	
item4 INT UNSIGNED,	
item5 INT UNSIGNED,
item6 INT UNSIGNED,
level TINYINT UNSIGNED,
minionsKilled INT UNSIGNED,
goldEarned INT UNSIGNED,
PRIMARY KEY(gameId,summonerId)	
);


