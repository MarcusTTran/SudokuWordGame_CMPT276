<b>{+Identification of the implementation of specific user stories and TDDs listed in iteration 1 will be found in the "User Stories and TDDs:" section marked with IMPLEMENTED: +}</b><br/>

<b>Iteration 2 quick list of implemented features:</b><br/>

- App randomly generates 9x9 Sudoku word puzzles.
- App prevents users from overriding generated puzzles pre-filled cells.
- Users can enter words into non-pre-filled cells
- Users can override entered words in non-pre-filled cells
- App main menu features exit button to exit app.
- Users can finish a game to determine whether their solution was valid.
- App displays how many mistakes the user made on completion of the puzzle.
- Users can select the language used to generate the Sudoku word puzzle.
- App can read in word pairs from a JSON file.
- App randomly selects word pairs to generate puzzle.
- Users can open the dictionary window to see a translation table of all word pairs featured in the puzzle.
- Users can open the rules window to read the rules of Word Sudoku.
- App highlights user-selected cell.
- App highlights the cells in the row and column of the user-selected cell.


<h1>User Stories and TDDs:</h1>

<h2>Novice User:</h2>
<h3>User Story</h3>

- <b>{+IMPLEMENTED: +}</b> As a novice player of sudoku, I want to be able to view a short description of the rules of the game so that I can refer to the rules as I play the game.
  <br/>

- <b>{+IMPLEMENTED: +}</b> As a novice player of sudoku, I want the column and row of my currently selected cell to be highlighted so that I can remember to check for any conflicting entries when I decide to enter a word in a cell.
  <br/>

<h3>TDD</h3>

- <b>{+IMPLEMENTED: +}</b> When the user is on the puzzle-solving screen and taps the question mark button (?) located on the top right of the display, a pop-up should appear displaying the rules of the game. The user should be able to tap the X button on the top right of the pop-up to close it and return back to the puzzle-solving screen.

<img src="https://i.imgur.com/MuynH6k.png"  width="24%">
<img src="https://i.imgur.com/b9WRLn8.png"  width="24%">
<img src="https://i.imgur.com/PGGg55z.png"  width="24%"><br/>

1. <i>User taps the ? button to see the rules.</i>

2. <i>User can read the rules and then can close the pop-up by tapping X</i>

3. <i>User is returned back to the puzzle screen</i>
   <br/>

<!--  <b>To Be Implemented: </b> When the user launches the app for the first time after installation, a one-time pop-up should appear that displays the rules of the games. After reading through the instructions, the user should then be able to close the pop-up rules by tapping the X button and have access to the app's main menu. 
<div>
<img src="https://i.imgur.com/O6ZL05n.png"  width="" height="375"><br />
<figcaption align = "left"><i>When the user opens the app for the first time, they will see the rules.</i></figcaption>
</div>
<br/>
<br/>
-->



- <b>{+IMPLEMENTED: +}</b> When the user selects a cell, the selected cell's column and row will be highlighted in green to remind and aid the user in checking for any conflicting entries.

<img src="https://i.imgur.com/8RPfuiM.png"  width="24%">
<img src="https://i.imgur.com/x2Va0DX.png"  width="24%">
<img src="https://i.imgur.com/GGu6w41.png"  width="24%"><br/>

1. <i>User taps on a cell to enter a word and the selected cell's column and row are highlighted in green.</i>

2. <i>User enters a word into the cell.</i>

3. <i>User taps another cell and the selected cell's column and row are also highlighted in green.</i>
   <br/>




<h2>Expert User:</h2>
<h3>User Story</h3>
<ul>
<li><b>To Be Implemented: </b> As an expert user, I want my unfinished puzzle to be savable so that I can finish more difficult time-consuming puzzles over several sessions without losing my progress. </li>
<li><b>To Be Implemented: </b> As an expert user, I want my puzzles to be timed so that I can improve my puzzle-solving performance under time constraints.</li>
</ul>

<h3>TDD</h3>

- <b>To Be Implemented: </b> The game should have saving features for recoverability (for instance when the app crashes or the user stops the app process manually) but below is an explanation of the saving feature when the user wants to leave the game.

- <b>To Be Implemented: </b> When the user taps on the Gear button, a pop-up will appear displaying 2 options (Statistics and Quit).  If the user taps the Quit button, another pop-up should appear asking to confirm whether the user wants to save the current game's progress with 2 options (YES or NO). If the user selects YES, the current game should be saved and the user will be brought back to the main menu. Users should be able to continue the saved puzzle by tapping the "Resume" button from the main menu. If the user selected NO, the puzzle progress will be deleted and there will be no "Resume" option on the main menu.

<img src="https://i.imgur.com/BWtlF0M.png"  width="24%">
<img src="https://i.imgur.com/jdXPAzf.png"  width="24%">
<img src="https://i.imgur.com/I9q6mkZ.png"  width="24%">
<img src="https://i.imgur.com/pcDfMsy.png"  width="24%">
<br/>

1. <i>User taps ⚙ to pause the game and see the options.</i>

2. <i>User taps Quit to exit the current game.</i>

3. <i>User is prompted to save the game. If they select NO current game will be lost.</i>

4. <i>If the user selected YES to save THE game, they have the option to RESUME their game</i>
   <br/>

- <b>To Be Implemented: </b> When the user enables the timer option in the settings page of the app, the puzzle-solving screen should feature a timer on at the top right corner of the screen. The timer must count the number of minutes and seconds users have spent on the puzzle. The timer must pause when users exit the app or close the app and resumes when users resume their puzzle. The timer ends when users complete the puzzle or quit the puzzle and the time spent on the puzzle is displayed in a pop-up at the end of the game.

<img src="https://i.imgur.com/3koBafM.png"  width="24%">
<img src="https://i.imgur.com/Gjmt42C.png"  width="24%">
<img src="https://i.imgur.com/8VetPzl.png"  width="24%">
<img src="https://i.imgur.com/ocrFfrw.png"  width="24%">
<br/>

1. <i>User taps OPTIONS to modify the game settings.</i>

2. <i>User taps TIMER to go to timer settings.</i>

3. <i>User turns the timer on by selecting ON.</i>

4. <i>With the timer on, they can see their current game's time.</i>
   <br/>
   When the user saves the game and exits to the main menu, the timer should be stopped.
   <br/>
   <img src="https://i.imgur.com/pMiPllg.png"  width="24%">
   <img src="https://i.imgur.com/3hClUkd.png"  width="24%">
   <br/>

1. <i>When the user saves current game and exits to main menu, the timer should be stopped.</i>

2. <i>If the user finishes the puzzle fast and sets a new record they will be prompted at the end of the game.</i>
   <br/>
   <h2>Beginner Language Learners:</h2>
   <h3>User Story</h3>

- <b>{+IMPLEMENTED: +}</b> As a beginner language learner, I want to be able to view a table of all corresponding word pairs so that I can avoid making too many mistakes from mistranslations.

- <b>{+IMPLEMENTED: +}</b> As a beginner language learner, I want to be able to view the number of mistakes I made at the end of a game, so I can determine how well I did on the puzzle.




<h3>TDD</h3>

- <b>{+IMPLEMENTED: +}</b> When the user taps the Book button which is located next to the Help button (the button labeled (?) that shows a short description of the game rules) a pop-up must open and show a table with two columns and ten rows (the first row will be the headings). This table lists all the word pairs used in the current puzzle. The number of times the user can open this table is limited to twice per game.


<img src="https://i.imgur.com/AEoJwxW.png"  width="24%">
<img src="https://i.imgur.com/gNwRBAv.png"  width="24%">
<img src="https://i.imgur.com/y5vCWcF.png"  width="24%">
<img src="https://i.imgur.com/kMNHyk7.png"  width="24%">
<br/>

1. <i>User taps the 🏳️ button to open the Dictionary table.</i>

2. <i>User can check the number of peeks they have left (2 per game).</i>

3. <i>User can check the words and their translations and close the pop-up by tapping X.</i>

4. <i>If the user taps the 🏳️ button to check the Dictionary table twice in a single game they will be presented with a pop-up explaining that they have hit the limit of peeks.</i>
   <br/>


- <b>{+IMPLEMENTED: +}</b> When the user has filled in all cells in the puzzle and taps the FINISH button the user will be taken to a results screen to see whether their solution to the puzzle was valid. If the user's solution to the puzzle was not valid the number of mistakes will be listed.

<img src="https://i.imgur.com/jEKIzRZ.png"  width="24%">
<img src="https://i.imgur.com/2PoeWoc.png"  width="24%">
<img src="https://i.imgur.com/vMDitKR.png"  width="24%">
<br/>

1. <i>User successfully fills in the puzzle however they make a mistake.</i>

2. <i>User taps FINISH.</i>

3. <i>User is taken to a results page where the number of mistakes is displayed.</i>
   <br/>


<h2>Intermediate Language Learners:</h2>
<h3>User Story</h3>

- <b>To Be Implemented: </b> As an intermediate learner of French, I want an option to set my game’s UI in French so that I can further immerse myself in French.

- <b>{+IMPLEMENTED: +}</b> As a beginner language learner, I want to be able to select which language the Sudoku puzzle uses so that I can practice solving puzzles with either French or English words.

- <b>To Be Implemented: </b> As a vocabulary learner taking the bus and Skytrain, I want to use my phone in landscape mode for Sudoku vocabulary practice so that longer words are displayed in a larger font than in standard portrait mode.


<h3>TDD</h3>

- <b>To Be Implemented: </b> In the OPTIONS menu, there should be a button called “Language” that allows the user to select the app's UI language. Menu options and pieces of text on-screen should turn to the selected language except for the word pairs involved in the puzzle.

<img src="https://i.imgur.com/3koBafM.png"  width="24%">
<img src="https://i.imgur.com/UPxFYa3.png"  width="24%">
<img src="https://i.imgur.com/enQcz7L.png"  width="24%">
<img src="https://i.imgur.com/ISWEM7s.png"  width="24%">
<br/>

1. <i>User taps the OPTIONS button to change game settings.</i>

2. <i>User clicks LANGUAGE to change the app language.</i>

3. <i>User selects English as the app language.</i>

4. <i>User selects French as the app language</i>
   <br/>

<!--COMMENTED OUT FOR NOW --> <!--COMMENTED OUT FOR NOW --> 
<!-- <b>To Be Implemented: </b> When a user first installs the app, the app should show a pop-up that asks the user if they want to have the user interface in French or English. After that, the user can change the language in the main menu under the OPTIONS tab as described by the TDD above.

<div>
<img src="https://i.imgur.com/Q6qlTS3.png"  width="" height="375">
<figcaption align = "left"><i> Users can select the app language when they run the app for the first time.</i></figcaption>
</div>
<br/>
<br/> -->






- <b>{+IMPLEMENTED: +}</b> When the user taps the "Puzzle Language:" button on the main menu, they can select whether the sudoku uses French or English words to generate the puzzle. If the user selects the puzzle language as English, then the user must enter French words into the puzzle - if the user selects the puzzle language as English, then the user must enter French words into the puzzle.

<img src="https://i.imgur.com/utwSdL4.png"  width="24%">
<img src="https://i.imgur.com/9VNOdeL.png"  width="24%">
<img src="https://i.imgur.com/hoYcNZd.png"  width="24%">
<img src="https://i.imgur.com/k8IpFNP.png"  width="24%">
<br>

1. <i>When the user taps the "Puzzle Language: " button on the main menu, the selected language will change to one of the 2 language options.</i>

2. <i>The Puzzle language has been set to French.</i>

3. <i>The user taps New Game.</i>

4. <i>The words the puzzle uses are now French and the user must enter words in English.</i>
   <br/>


- <b>To Be Implemented: </b> When the user flips their phone to landscape mode, the game flips accordingly to match their screen orientation. Also, longer words will be displayed in larger font relative to the new screen length. The sudoku grid and the buttons will be moved so that the grid is to the left of the list of words.

<img src="https://i.imgur.com/36AGqWb.jpg"  width="40%">
<img src="https://i.imgur.com/QpQGDNz.jpg"  width="40%">
<br>

1. <i>The main menu screen when the device is in landscape mode.</i>

2. <i>The layout of a sudoku game on a device in landscape mode.</i>
   <br>



<h2>Advanced Language Learners:</h2>
<h3>User Story</h3>

- <b>To Be Implemented: </b> As an advanced learner, I want to have access to more difficult topic-specific words so that the puzzles can challenge the range of my vocabulary.

- <b>Semi-Implemented: </b> As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice so that the words will be conveniently displayed in larger, easier-to-read fonts.

- <b>To Be Implemented: </b> As an advanced language learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4 or 4x3).


<h3>TDD</h3>

- <b>To Be Implemented: </b> When a user selects a difficulty level that is 'medium' or higher, the user can select whether subsequent puzzles are generated from random or themed (topic-specific) word pairs.
  <img src="https://i.imgur.com/3koBafM.png"  width="24%">
  <img src="https://i.imgur.com/DXoRdSe.png"  width="24%">
  <img src="https://i.imgur.com/oxpDgi6.png"  width="24%">
  <img src="https://i.imgur.com/USqPY1Q.png"  width="24%">
  <br>

1. <i>User taps OPTIONS to change game settings.</i>

2. <i>User taps DIFFICULTY to select puzzles' difficulty.</i>

3. <i>User can select the difficulty on a scale of 1 (Easy) to 5 (Hard).</i>

4. <i>Users can set the puzzles to the THEMED mode for more difficulty. </i>
   <br/>


- <b>To Be Implemented: </b> If a user loads the app on a larger screen, such as a tablet, the puzzle will be scaled to fit the screen. If the tablet is twice as large as a phone in measurements, the sudoku grid will be twice the area. Consequently, the font of the words and the buttons will also be scaled in size accordingly, allowing for increased clarity.
  <img src="https://i.imgur.com/ZDYIsy0.jpg"  width="24%">
  <img src="https://i.imgur.com/T6EgU4U.jpg"  width="40%">
  <br>

1. <i>User creates a custom 12x12 sudoku game while on a tablet in portrait mode.</i>

2. <i>User creates a default 9x9 sudoku game while on a tablet in landscape mode.</i>
   <br>

- <b>To Be Implemented: </b> Refer to <i>Language Teacher TDD #3</i> for further explained annotations and instructions on how a user would create this 12x12 sudoku grid.
  <br>

<img src="https://i.imgur.com/jfoLga8.jpg"  width="24%">
<img src="https://i.imgur.com/TotOYKH.jpg"  width="24%">
<img src="https://i.imgur.com/wIkaMqN.jpg"  width="24%">
<img src="https://i.imgur.com/lyS5jEp.png"  width="24%">
<br>
<img src="https://i.imgur.com/ZDYIsy0.jpg"  width="24%">
<img src="https://i.imgur.com/vgyC5IU.jpg"  width="40%">
<br>

1. <i>The 12x12 sudoku game in portrait mode on a tablet.</i>

2. <i>The 12x12 sudoku game in landscape mode on a tablet.</i>


<h2>French teacher:</h2>
<h3>User Story</h3>

- <b>To Be Implemented: </b> As a French teacher, I want to be able to select the word pairs that will be used to generate the Sudoku puzzle so that I can use the app to teach my students any of the specified words that I choose.

- <b>To Be Implemented: </b> As a French teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3 or 3x2).


<h3>TDD</h3>

- <b>To Be Implemented: </b> In the main menu of the app, the user can tap on "options" and then “create custom puzzle” which will prompt the user to enter 9 different English and French word pairs. Then, these word pairs will be used in the next generated puzzle.
  <img src="https://i.imgur.com/3koBafM.png"  width="24%">
  <img src="https://i.imgur.com/O9CQEZE.png"  width="24%">
  <img src="https://i.imgur.com/wooUQd3.png"  width="24%">
  <img src="https://i.imgur.com/kpZzWsE.png"  width="24%">
  <br>

1. <i>Teacher clicks OPTIONS button to change game settings</i>

2. <i>Teacher clicks CREATE CUSTOM PUZZLE to build a custom puzzle.</i>

3. <i>Teacher enters the words for this custom puzzle and taps SET WORDS.</i>

4. <i>After setting the words, the Teacher is shown a confirmation dialog to build the custom puzzle.</i>
   <br>

- <b>To Be Implemented: </b> In the main menu screen of the app, when the user clicks on the “new game” button, they will be taken to an options page where they can choose the difficulty of the puzzles as well as a “custom” difficulty where they can create sudoku puzzles of size 4x4 and 6x6.

<img src="https://i.imgur.com/jfoLga8.jpg"  width="24%">
<img src="https://i.imgur.com/TotOYKH.jpg"  width="24%">
<img src="https://i.imgur.com/STUXsQq.jpg"  width="24%">
<br>

1. <i>In the main menu screen, the user taps on New Game.</i>

2. <i>A Choose difficulty menu appears, and the user can tap on custom difficulty.</i>

3. <i>User enters 6 into both the row and column in order to make a 6x6 sudoku puzzle.</i>
   <br>

- <b>To Be Implemented: </b> When the user taps on the “custom” difficulty button they will brought to a screen that asks the user to enter the dimension of the "custom" difficulty puzzle. Users can create custom sized puzzle with rows and columns of any value between 1 and 12, which is noted in the entry boxes. A button located at the bottom of the screen will say “Start Game”. When the user taps this after entering valid dimensions for the sudoku board, it will start the game. If incorrect values are entered, it will prompt the user to retry through a pop-up message.


<img src="https://i.imgur.com/TotOYKH.jpg"  width="24%">
<img src="https://i.imgur.com/wIkaMqN.jpg"  width="24%">
<img src="https://i.imgur.com/STUXsQq.jpg"  width="24%">
<img src="https://i.imgur.com/90GJSHp.jpg"  width="24%">
<br>
<img src="https://i.imgur.com/uK4fWF5.jpg"  width="24%">
<img src="https://i.imgur.com/LyNOnMP.jpg"  width="40%">
<br>

1. <i>User taps on the custom difficulty button and is brought to the Custom Board screen that lets users enter in the size of their puzzle.</i>

2. <i>User enters valid dimensions into the entry box for the puzzle creation and then taps Start Game.</i>

3. <i>If the user enters invalid dimensions, a red warning text appears on the screen informing the user of the error.</i>

4. <i>The puzzle screen if the user taps on start game after deciding to create a 6x6 sudoku board.</i>

5. <i>The puzzle screen if the user taps on start game after deciding to create a 4x4 sudoku board.</i>
   <br>