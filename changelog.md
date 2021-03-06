1/5/2013

<b>ALEX</b>
- Changed the auto-complete list view to wrap row height to the height of the suggestion's content
- Created functionality for users to cap the amount of suggestions auto-complete gives them
- Made the auto-complete matching algorithm case insensitive

1/4/2013

<b>ALEX</b>
- Fixed the stacking activities bug, which messed up the app exit functionality 

12/31/2013:

<b>ALEX</b>
- Implemented auto-suggest for both Card No. search and the name field of Advanced Search
- Implemented indexes upon database initialization 
- Made the Advanced Search text fields extend purely horizontally rather than both
- Made Advanced Search form data remembered when going back home as well
- Used string resources wherever I could to get rid of warnings

12/27/2013:

<b>ALEX</b>
- Now says "1 card" instead of "1 cards"
- Made the front page less derpily hardcoded so it adapts to user's screen better. Also, added padding to the image buttons to make them more even
- Created a settings page for the app (first 2 are search form retention, last one will be implemented soon)
- Implemented the search form retention functions
- Created and implemented an erase button that only appears in the action toolbar when the user is on one of the search pages

12/25/2013:

<b>ALEX</b>
- Replaced the derpy buttons with image buttons

12/24/2013:

<b>ALEX</b>
- Started implementing a settings page
- Implemented advanced search
- Implemented batch insertion upon initialization of the Weiss DB, cutting down import time by 75%
- Fixed the weird rarities issue by running sed on the cards.csv file a few times after the parser spits it out
- Added random image of the happy as hell fox to the initialization page

12/22/2013:

<b>ALEX</b>
- Created a main page with a logo at the top (as of now, it's Claptrap as a placeholder) and buttons beneath it
- Changed the random card output page to have the "Random Card" button at top instead of below card information. This way, it stops moving up and down all the time
- Changed the card number search page to simply load the card information underneath it dynamically. I took a leaf from CS144's book and realized that the option to do another search should come bundled with the output
- Created basic advanced search page
- Cleaned up keyboard behavior
- Added a "Home" button to the action bar on the right so the user can always return home, no matter how deep into the app he/she gets
