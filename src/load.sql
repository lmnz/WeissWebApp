use CS144;

LOAD DATA LOCAL INFILE 'translations/cards.csv' INTO TABLE cards
FIELDS TERMINATED BY '~~|}';
