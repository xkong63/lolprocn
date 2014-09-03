DROP DATABASE lolDB;
CREATE DATABASE lolDB;
USE lolDB;

-- CREATE TABLE playerMatchs(
-- summonerId INT UNSIGNED,
-- gameId INT UNSIGNED,
-- PRIMARY KEY(summonerId)
-- );

CREATE TABLE matches(
gameId BIGINT UNSIGNED,
createDate BIGINT UNSIGNED,
matchDuration BIGINT UNSIGNED,
gameMode VARCHAR(20),
gameType VARCHAR(20),	
invald BOOLEAN,
mapId INT UNSIGNED,
ipEarned INT UNSIGNED,
subType VARCHAR(20),
PRIMARY KEY(gameId)
);

CREATE TABLE summoner(
summonerId BIGINT UNSIGNED,
profileIcon INT UNSIGNED,
summonerName VARCHAR(30),
level TINYINT UNSIGNED,
PRIMARY KEY(summonerId)
);

CREATE TABLE  match_rawStats(
gameId BIGINT UNSIGNED,
summonerId BIGINT UNSIGNED,
championId INT UNSIGNED,
spell1Id INT UNSIGNED,
spell2Id INT UNSIGNED,
assists	INT UNSIGNED,
kills INT UNSIGNED,
minionsKilled INT UNSIGNED,
deaths INT UNSIGNED,
killSprees INT UNSIGNED,
totalDamageDealtToChampions INT UNSIGNED,
totalDamageTaken INT UNSIGNED,
item0 INT UNSIGNED,	
item1 INT UNSIGNED,	
item2 INT UNSIGNED,	
item3 INT UNSIGNED,	
item4 INT UNSIGNED,	
item5 INT UNSIGNED,
item6 INT UNSIGNED,
teamId INT UNSIGNED,
level TINYINT UNSIGNED,
goldEarned INT UNSIGNED,
win BOOLEAN,
PRIMARY KEY(gameId,summonerId),
INDEX(summonerId),
INDEX(gameId),
FOREIGN KEY(gameId) REFERENCES matches(gameId),
FOREIGN KEY(summonerId) REFERENCES summoner(summonerId)
);




