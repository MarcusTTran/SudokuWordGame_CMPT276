<b>{+Identification of the implementation of specific user stories and TDDs listed in iteration 1 will be found in the "User Stories and TDDs:" section marked with IMPLEMENTED: +}</b><br/>

<b>Previously implemented features: Iteration 2 quick list of implemented features:</b><br/>

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

<b><b>{+Iteration 3 quick list of implemented features: +}</b> </b><br/>
- Create puzzles of sizes of 4x4, 6x6, 12x12
- 4x4, 6x6, 12x12 puzzles are randomly generated
- Set difficulty of puzzles
- Create puzzles with user entered words
- Save current puzzle and load puzzle to allow users to continue where they left off
- Time puzzles
- UI immersion mode to set UI language to French
- Auto-save puzzles
- Kebab menu on puzzle page for quick app navigation
- Load in user entered words on custom words page for quick modifications
- App supports layouts for tablets


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
<li><b>{+IMPLEMENTED: +}</b> As an expert user, I want my unfinished puzzle to be savable so that I can finish more difficult time-consuming puzzles over several sessions without losing my progress. </li>
<li><b>{+IMPLEMENTED: +}</b> As an expert user, I want my puzzles to be timed so that I can improve my puzzle-solving performance under time constraints.</li>
</ul>

<h3>TDD</h3>

- <b>{+IMPLEMENTED: +}</b> The game should have saving features for recoverability (for instance when the app crashes or the user stops the app process manually) but below is an explanation of the saving feature when the user wants to leave the game.

- <b>{+IMPLEMENTED: +}</b>  When the user taps on the back button, a pop-up will appear asking if the user wishes to save their game before exiting.  If the user taps the YES button, the current game should be saved and the user will be brought back to the main menu. Users should be able to continue the saved puzzle by tapping the "Load Saved Game" button from the main menu. If the user selected NO, the puzzle progress will be deleted and the user will be brought to the main menu. If the user selects cancel the dialog is dismissed and the user can continue with their puzzle.

<img src="https://i.imgur.com/IpfntRo.png"  width="24%">
<img src="https://i.imgur.com/O9nfS8J.png"  width="24%">
<img src="https://i.imgur.com/0MQk7Gf.png"  width="24%">
<img src="https://i.imgur.com/w6u8PNI.png"  width="24%">
<br/>

1. <i>User taps ← to open the exit game save game dialog.</i>

2. <i>User taps YES to save the game and exit the puzzle.</i>

3. <i>User can then access their saved game through the Load Saved Game button.</i>

4. <i>Their previous game is loaded in </i>
   <br/>

- Alternatively the user can select the kebab button on the puzzle page to open a menu which they can use to save their puzzle progress while still remaining on the puzzle page

<img src="https://i.imgur.com/57HNdBU.png"  width="24%">
<img src="https://i.imgur.com/A7Wt8Hb.png"  width="24%">
<img src="https://i.imgur.com/wVXBJla.png"  width="24%">
<br/>
<br/>



- <b>{+IMPLEMENTED: +}</b>  When the user enables the timer option in the settings page of the app, the puzzle-solving screen should feature a timer on at the top right corner of the screen. The timer must count the number of minutes and seconds users have spent on the puzzle. The timer must pause when users exit the app or close the app and resumes when users resume their puzzle. The timer ends when users complete the puzzle or quit the puzzle and the time spent on the puzzle is displayed in a pop-up at the end of the game.

<img src="https://i.imgur.com/YV9CtSK.png"  width="24%">
<img src="https://i.imgur.com/SUhOOsG.png"  width="24%">
<img src="https://i.imgur.com/baIr7R2.png"  width="24%">
<br/>

1. <i>User taps OPTIONS button to modify the game settings.</i>

2. <i>User turns the timer on by selecting ON.</i>

3. <i>With the timer on, they can see their current game's time.</i>
   <br/>
   When the user saves the game and exits to the main menu, the timer stops.
   <br/>
   <img src="https://i.imgur.com/RmQw1aR.png"  width="24%">
   <img src="https://i.imgur.com/KeBt9Tn.png"  width="24%">
   <img src="https://i.imgur.com/0MQk7Gf.png"  width="24%">
   <img src="https://i.imgur.com/35Yc7h2.png"  width="24%">
   <br/>

1. <i>When the user saves current game and exits to main menu, the timer should be stopped.</i>

2. <i>Loading the saved game will continue their timer where they left off.</i>
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

- <b>{+IMPLEMENTED: +}</b>  As an intermediate learner of French, I want an option to set my game’s UI in French so that I can further immerse myself in French.

- <b>{+IMPLEMENTED: +}</b> As a beginner language learner, I want to be able to select which language the Sudoku puzzle uses so that I can practice solving puzzles with either French or English words.

- <b>{+IMPLEMENTED: +}</b>  As a vocabulary learner taking the bus and Skytrain, I want to use my phone in landscape mode for Sudoku vocabulary practice so that longer words are displayed in a larger font than in standard portrait mode.


<h3>TDD</h3>

- <b>{+IMPLEMENTED: +}</b> If the user goes to the SETTINGS menu, they click on the “Set UI Immersion” button to change the app's UI language to either French or English. 

<img src="https://i.imgur.com/YV9CtSK.png"  width="24%">
<img src="https://i.imgur.com/Bzk8wh6.png"  width="24%">
<img src="https://i.imgur.com/SB5mFcd.png"  width="24%">
<img src="https://i.imgur.com/Jk37Zy1.png"  width="24%">
<br/>

1. <i>User taps the SETTINGS button to change game settings.</i>

2. <i>User clicks SET UI IMMERSION to change the app language.</i>

3. <i>The App is now in French.</i>

4. <i>The main menu in French</i>
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


- <b>{+IMPLEMENTED: +}</b>  When the user flips their phone to landscape mode, the game flips accordingly to match their screen orientation. The sudoku board and words are displayed in a larger font in accord to the devices screen size. The sudoku grid and the buttons is moved so that the grid is to the left of the list of words.

<img src="https://i.imgur.com/TwUixsw.png"  width="40%">
<img src="https://i.imgur.com/ic2Hde9.png"  width="40%">
<br>

1. <i>The main menu screen when the device is in landscape mode.</i>

2. <i>The layout of a sudoku game on a device in landscape mode.</i>
   <br>



<h2>Advanced Language Learners:</h2>
<h3>User Story</h3>

<!-- - <b>To Be Implemented: </b> As an advanced learner, I want to have access to more difficult topic-specific words so that the puzzles can challenge the range of my vocabulary. -->

- <b>{+IMPLEMENTED: +}</b>  As a vocabulary learner practicing at home, I want to use my tablet for Sudoku vocabulary practice so that the words will be conveniently displayed in larger, easier-to-read fonts.

- <b>{+IMPLEMENTED: +}</b>  As an advanced language learner who wants an extra challenging mode, I want a 12x12 version of Sudoku to play on my tablet. The overall grid should be divided into rectangles of 12 cells each (3x4 or 4x3).


<h3>TDD</h3>

<!-- - <b>To Be Implemented: </b> When a user selects a difficulty level that is 'medium' or higher, the user can select whether subsequent puzzles are generated from random or themed (topic-specific) word pairs.
<br>
  <img src="https://i.imgur.com/3koBafM.png"  width="24%">
  <img src="https://i.imgur.com/DXoRdSe.png"  width="24%">
  <img src="https://i.imgur.com/oxpDgi6.png"  width="24%">
  <img src="https://i.imgur.com/USqPY1Q.png"  width="24%">
  <br>

1. <i>User taps OPTIONS to change game settings.</i>

2. <i>User taps DIFFICULTY to select puzzles' difficulty.</i>

3. <i>User can select the difficulty on a scale of 1 (Easy) to 5 (Hard).</i>

4. <i>Users can set the puzzles to the THEMED mode for more difficulty. </i>
   <br/> -->


- <b>{+IMPLEMENTED: +}</b>  If a user loads the app on a larger screen, such as a tablet, the puzzle will be scaled to fit the screen. If the tablet is twice as large as a phone in measurements, the sudoku grid will be twice the area. Consequently, the font of the words and the buttons will also be scaled in size accordingly, allowing for increased clarity.
<br>
  <img src="https://i.imgur.com/CHzhZwl.png"  width="30%">
  <img src="https://i.imgur.com/3JbdrIW.png"  width="40%">
  <br>

1. <i>User creates a custom 4x4 sudoku game while on a tablet in portrait mode.</i>

2. <i>User creates a default 9x9 sudoku game while on a tablet in landscape mode.</i>
   <br>

- <b>{+IMPLEMENTED: +}</b>  Refer to <i>Language Teacher TDD #3</i> for further explained annotations and instructions on how a user would create this 12x12 sudoku grid.
  <br>

<img src="https://i.imgur.com/64mM4mI.png"  width="24%">
<img src="https://i.imgur.com/nQOfGwS.png"  width="24%">
<img src="https://i.imgur.com/YLjnfoH.png"  width="24%">
<br>
<img src="https://i.imgur.com/s0BxF1Y.png"  width="24%">
<img src="https://i.imgur.com/I9ncbb5.png"  width="40%">
<br>

1. <i>The 12x12 sudoku game in portrait mode on a tablet.</i>

2. <i>The 12x12 sudoku game in landscape mode on a tablet.</i>


<h2>French teacher:</h2>
<h3>User Story</h3>

- <b>{+IMPLEMENTED: +}</b>  As a French teacher, I want to be able to select the word pairs that will be used to generate the Sudoku puzzle so that I can use the app to teach my students any of the specified words that I choose.

- <b>{+IMPLEMENTED: +}</b>  As a French teacher of elementary and junior high school children, I want scaled versions of Sudoku that use 4x4 and 6x6 grids. In the 6x6 grid version, the overall grid should be divided into rectangles of six cells each (2x3 or 3x2).

- <b>{+IMPLEMENTED: +}</b>  As a French teacher in highschool, I want to be able to select the difficulty of my puzzles so I can give easier on harder puzzles depending on the age and grade of my students.


<h3>TDD</h3>

- <b>{+IMPLEMENTED: +}</b> In the main menu of the app, the user can tap on "options" and then “create custom puzzle” which will prompt the user to enter 9 different English and French word pairs. Then, these word pairs will be used in the next generated puzzle.

  <img src="https://i.imgur.com/KPEncKC.png"  width="24%">
  <img src="https://i.imgur.com/JjrfkKm.png"  width="24%">
  <img src="https://i.imgur.com/OHorbGu.png"  width="24%">
  <img src="https://i.imgur.com/Gf5A7Db.png"  width="24%">
  <br>

1. <i>Teacher clicks CUSTOM WORDS button to change game settings</i>

2. <i>Teacher selects the puzzle size they wish to enter custom words into.</i>

3. <i>Teacher enters the words for this custom puzzle and taps CONFIRM.</i>

4. <i>The teacher is then brought to the custom puzzle page where his puzzle is displayed.</i>
   <br>

- <b>{+IMPLEMENTED: +}</b>  In the main menu screen of the app, when the user clicks on the “new game” button, they will be taken to an options page where they can choose the difficulty of the puzzles as well as a “custom” difficulty where they can create sudoku puzzles of size 4x4 and 6x6.

<img src="https://i.imgur.com/iT7c5yX.png"  width="24%">
<img src="https://i.imgur.com/nQOfGwS.png"  width="24%">
<img src="https://i.imgur.com/aZd6vRM.png"  width="24%">
<br>

1. <i>In the main menu screen, the user taps on New Game.</i>

2. <i>User clicks the Custom Sized button.</i>

3. <i>User can select to create 4x4 and 6x6 puzzles.</i>
   <br>


- <b>{+IMPLEMENTED: +}</b>  In the main menu screen of the app, when the user clicks on the SETTINGS button, they will be taken to an SETTINGS page where they can choose the difficulty of the puzzle generated. Once the difficulty has been set, any newly created puzzles will be generated to use the difficulty set.

<img src="https://i.imgur.com/WVdCjEl.png"  width="24%">
<img src="https://i.imgur.com/yl0gyjt.png"  width="24%">
<img src="https://i.imgur.com/ZxGkgjm.png"  width="24%">
<img src="https://i.imgur.com/bFbUQ9f.png"  width="24%">
<br>

1. <i>In the main menu screen, the user taps the SETTINGS button.</i>

2. <i>User taps difficulty.</i>

3. <i>User the user sets the difficulty of the puzzles.</i>

4. <i>The difficulty is displayed on the puzzle page.</i>
   <br>

<!-- - <b>{+IMPLEMENTED: +}</b>  When the user taps on the “custom” difficulty button they will brought to a screen that asks the user to enter the dimension of the "custom" difficulty puzzle. Users can create custom sized puzzle with rows and columns of any value between 1 and 12, which is noted in the entry boxes. A button located at the bottom of the screen will say “Start Game”. When the user taps this after entering valid dimensions for the sudoku board, it will start the game. If incorrect values are entered, it will prompt the user to retry through a pop-up message.


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
   <br> -->
