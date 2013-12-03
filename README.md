Weiss WebApp
============

<b>OBJECTIVE</b>: This webapp is designed to provide a user-friendly place for Weiss players to browse card information for translated cards. Apparently, this tool doesn't already exist.

<b>AS OF NOW</b>: I (Alex) have written a parser that, when given a file formatted in the way the .txt files are formatted in the translation directory underneath the src folder, will parse the file for cards and their relevant information and write this data to a .csv file, with each row representing a card. If the .csv doesn't exist, the parser creates it, and if it already exists, the parser simply appends to it. I have also created create, drop, and load .sql files for the SQL side. Lastly, I have written a 'start over' shell script that runs the parser on all of the .txt files containing the translated cards, drops and creates the cards database table, and loads the monster .csv the parser creates into the datbase. However, this script only works with the Ubuntu VM provided by CS144 at UCLA.

<b>FUTURE</b>: Since this was Justin's idea, I have no idea what he wants to the interface to look like, so the front end's future is completely up in the air. However, we need to figure out where to host the database along with the webapp itself and its corresponding architecture.
