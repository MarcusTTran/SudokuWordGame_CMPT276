# Controllers
**ChooseCustomWords.java**:
This fragment allows the user to enter their own words to be used in the puzzle.
It is shown when the user clicks the "Pencil" button in the MainMenuFragment or when the user wants to start a new custom words game from ChoosePuzzleModeFragment.
<br><br>
**ChoosePuzzleMode.java**:
This fragment is the first fragment that the user sees when they click the "New Game" button.
It allows the user to choose between a classic puzzle (9x9) and a custom puzzle (any size) or a custom words puzzle.
When the user clicks on Custom Sized Puzzle, a dialog fragment is displayed to allow the user to choose the size of the puzzle.
When the user clicks on Custom Words Puzzle, if they have not set the custom words, they will be navigated to the ChooseCustomWordsFragment.
<br><br>
**MainMenuFragment.java**:
This is the main menu fragment. It is the first fragment that is loaded when the app is opened.
It contains buttons that allow the user to start a new game, load a saved game, change the language, tweak the settings, enter their own custom words for puzzles and exit the app.
<br><br>
**PuzzleFragment.java**:
This is the fragment that displays the puzzle. It is shown when the user loads a previous game from MainMenuFragment or when the user starts a new game from ChoosePuzzleModeFragment.
<br><br>
**SettingsFragment.java**:
This fragment allows the user to change the settings of the app. It is shown when the user clicks the "Settings" button in the MainMenuFragment.
Some of the settings: AutoSave, Timer, App Language, etc.
<br><br>


# ViewModels:
**PuzzleViewModel.java**:
This is the view model for the PuzzleFragment. It contains the puzzle data and the logic for creating new, loading and saving puzzles.
<br><br>
**SettingsViewModel.java**:
This is the view model for the SettingsFragment. It contains the settings data and the logic for the settings.
Settings are saved in SharedPreferences.
<br><br>


# Models:
**Puzzle.java**:
This is the model for the puzzle. It contains the puzzle data and the logic for the puzzle. It is used by the PuzzleViewModel.
It contains the following methods:
* ```public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells, int difficulty) throws IllegalDimensionException, IllegalWordPairException, IllegalLanguageException, TooBigNumberException, NegativeNumberException```:<br> This is the constructor for the puzzle. It takes the word pairs, the dimension of the puzzle, the language, the number of start cells and the difficulty as parameters. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension is not a perfect square.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.
    * ```IllegalLanguageException```: This exception is thrown when the language is not valid.
    * ```TooBigNumberException```: This exception is thrown when the number of start cells is bigger than the dimension of the puzzle.
    * ```NegativeNumberException```: This exception is thrown when the number of start cells or the difficulty is negative.<br><br>
* ```public Puzzle(List<WordPair> wordPairs,int dimension, int language, int numberOfStartCells) throws IllegalWordPairException, IllegalDimensionException, IllegalLanguageException, TooBigNumberException, NegativeNumberException```: <br> This is the constructor for the puzzle. It takes the word pairs, the dimension of the puzzle, the language and the number of start cells as parameters. <br><br>
* ```public Puzzle(Puzzle puzzle)```: <br> This is the copy constructor for the puzzle. It takes the puzzle to be copied as a parameter. <br><br>
* ```public Puzzle(CellBox2DArray userBoard, CellBox2DArray solutionBoard, List<WordPair> wordPairs, PuzzleDimensions puzzleDimension, int language, int mistakes, int timer) throws IllegalDimensionException```: <br> This is the constructor for the puzzle. It takes the user board, the solution board, the word pairs, the puzzle dimension, the language, the number of mistakes and the timer as parameters. It is ideal for when loading a puzzle from a JSON file.<br><br>
* ```public CellBox2DArray getUserBoard()```: <br> This method returns the user board. <br><br>
* ```public CellBox2DArray getSolutionBoard()```: <br> This method returns the solution board.<br><br>
* ```public List<WordPair> getWordPairs()```: <br> This method returns the word pairs.<br><br>
* ```public PuzzleDimensions getPuzzleDimensions()```: <br> This method returns the puzzle dimensions.<br><br>
* ```public int getLanguage()```: <br> This method returns the language.<br><br>
* ```public int getMistakes()```: <br> This method returns the number of mistakes.<br><br>
* ```public int getTimer()```: <br> This method returns the timer.<br><br>
* ```public void setUserBoard(CellBox2DArray userBoard)```: <br> This method sets the user board.<br><br>
* ```public void setSafeUserBoard(CellBox2DArray userBoard) throws IllegalDimensionException, IllegalWordPairException```: <br> This method sets the user board. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public void setTimer(int timer) throws NegativeNumberException```: <br> This method sets the timer. It throws the following exception:
    * ```NegativeNumberException```: This exception is thrown when the timer is negative.<br><br>
* ```public void setTextToSpeechOn(boolean textToSpeechOn)```: <br> This method sets the Listening Comprehension Mode.<br><br>
* ```public boolean isTextToSpeechOn()```: <br> This method returns the Listening Comprehension Mode.<br><br>
* ```public boolean isPuzzleFilled()```: <br> This method returns true if the puzzle is filled and false otherwise.<br><br>
* ```public boolean isPuzzleSolved()```: <br> This method returns true if the puzzle is solved and false otherwise.<br><br>
* ```public GameResult getGameResult()```: <br> This method returns the game result.<br><br>
* ```public void setCell(int i, int j, WordPair word) throws IllegalDimensionException, IllegalWordPairException```: <br> This method sets the cell at the given position to the given word pair. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public void setCell(int i, int j, String word) throws IllegalWordPairException, IllegalDimensionException```: <br> This method sets the cell at the given position to the given word. <br><br>
* ```public void setCell(Dimension dimension, String word) throws IllegalWordPairException, IllegalDimensionException```: <br> This method sets the cell at the given position to the given word. <br><br>
* ```public void setCell(Dimension dimension, WordPair word) throws IllegalWordPairException, IllegalDimensionException```: <br> This method sets the cell at the given position to the given word pair. <br><br>
* ```public String[][] toStringArray()```: <br> This method returns the puzzle as a String 2D array.<br><br>
* ```private String retrieveWordStringFromCell(boolean textToSpeechOn, Cell cell)```: <br> This method returns the word string from the given cell.<br><br>
* ```public static boolean isIthRowNotContaining(CellBox2DArray cellBox2DArray , int i, WordPair wordPair) throws IllegalDimensionException, IllegalWordPairException```: <br> This method returns true if the given row does not contain the given word pair and false otherwise. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public static boolean isJthColumnNotContaining(CellBox2DArray cellBox2DArray, int j, WordPair wordPair) throws IllegalWordPairException, IllegalDimensionException```: <br> This method returns true if the given column does not contain the given word pair and false otherwise. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public static boolean isJthColumnValid(CellBox2DArray cellBox2DArray,int j) throws IllegalDimensionException```: <br> This method returns true if the given column is valid and false otherwise. It throws the following exception:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.<br><br>
* ```public static boolean isIthRowValid(CellBox2DArray cellBox2DArray,int i) throws IllegalDimensionException```: <br> This method returns true if the given row is valid and false otherwise. It throws the following exception:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.<br><br>
* ```public static boolean isCellBoxValid(CellBox cellBox)```: <br> This method returns true if the given cell box is valid and false otherwise.<br><br>
* ```public static boolean SolveBoard(CellBox2DArray board, List<WordPair> wordPairs) throws IllegalWordPairException, IllegalDimensionException```: <br> This method returns true if the given board is solved and false otherwise. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public static boolean isSudokuValid(CellBox2DArray board,List<WordPair> wordPairList) throws IllegalWordPairException, IllegalDimensionException```: <br> This method returns true if the given board is valid and false otherwise. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public static Dimension findEmptyCell(CellBox2DArray puzzle)```: <br> This method returns the position of the first empty cell.<br><br>
* ```public static CellBox2DArray getTrimmedBoard(CellBox2DArray solutionBoard,int cellsToRemove,int inputLanguage)```: <br> This method returns the trimmed board.<br><br>
* ```private void lockCells()```: <br> This method locks the cells.<br><br>
* ```public boolean isWritableCell(Dimension dimension)```: <br> This method returns true if the given cell is writable and false otherwise.<br><br>
* ```public void resetPuzzle(boolean resetTimer)```: <br> This method resets the puzzle.<br><br>
* ```public boolean isPuzzleBlank()```: <br> This method returns true if the puzzle is blank and false otherwise.<br><br>
* ```public boolean isPuzzleTotallyEmpty()```: <br> This method returns true if the puzzle is totally empty and false otherwise.<br><br>
* ```public boolean[][] getImmutabilityTable()```: <br> This method returns the immutability table.<br><br>
* ```private int getCellsToRemoveWithDifficulty(int difficulty)```: <br> This method returns the number of cells to remove with the given difficulty.<br><br>
* ```public JSONObject toJson() throws JSONException```: <br> This method returns the puzzle as a JSON object.<br><br>
* ```public boolean equals(Object o)```: <br> This method returns true if the given object is equal to the puzzle and false otherwise.<br><br>
* ```private boolean areWordPairsEqual(List<WordPair> one, List<WordPair> two)```: <br> This method returns true if the given word pairs are equal and false otherwise.<br><br>
* ```public void unlockCells()```: <br> This method unlocks the cells.<br><br>
* ```public void fillUserBoardRandomly() throws IllegalWordPairException, IllegalDimensionException```: <br> This method fills the user board randomly. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
* ```public void solve() throws IllegalWordPairException, IllegalDimensionException```: <br> This method solves the puzzle. It throws the following exceptions:
    * ```IllegalDimensionException```: This exception is thrown when the dimension of the user board is not the same as the dimension of the solution board.
    * ```IllegalWordPairException```: This exception is thrown when the word pairs are not valid.<br><br>
<br><br>

**WordPair.java**:
This class represents a word pair. It has the following fields:
* ```private String eng```: <br> This field represents the first word of the word pair.<br><br>
* ```private String fre```: <br> This field represents the second word of the word pair.<br><br>
It has the following methods:
* ```public WordPair(String eng, String fre)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```eng```: This parameter represents the first word of the word pair.
    * ```fre```: This parameter represents the second word of the word pair.<br><br>
* ```public String getEnglish()```: <br> This method returns the english word of the word pair.<br><br>
* ```public String getFrench()```: <br> This method returns the french word of the word pair.<br><br>
* ```public void setEnglish(String eng)```: <br> This method sets the english word of the word pair.<br><br>
* ```public void setFrench(String fre)```: <br> This method sets the french word of the word pair.<br><br>
* ```public String getEnglishOrFrench(int language)```: <br> This method returns the english or french word of the word pair depending on the given language.<br><br>
* ```public boolean isEqual(WordPair other)```: <br> This method returns true if the given word pair is equal to the word pair and false otherwise.<br><br>
* ```public boolean equals(Object o)```: <br> This method returns true if the given object is equal to the word pair and false otherwise.<br><br>
* ```public boolean doesContain(String word)```: <br> This method returns true if the given word is contained in the word pair and false otherwise.<br><br>
* ```public static boolean doesListContainRepeatingWordPairs(List<WordPair> wordPairs)```: <br> This method returns true if the given list of word pairs contains repeating word pairs and false otherwise.<br><br>
* ```public static boolean doesListContainThisWordPair(List<WordPair> wordPairs, WordPair wordPair)```: <br> This method returns true if the given list of word pairs contains the given word pair and false otherwise.<br><br>

**Cell.java**:
This class represents a cell. It has the following fields:
```
    private WordPair content = null;

    // isEmpty: whether the cell is empty or not
    private boolean isEmpty = true;

    // isEditable: whether the cell is editable or not
    private boolean isEditable = true;

    // language: the language of the cell
    private int language;
```

It has the following methods:
* ```public Cell(WordPair content, int language) throws NullPointerException```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```content```: This parameter represents the content of the cell.
    * ```language```: This parameter represents the language of the cell.<br><br>
* ```public Cell(WordPair content, boolean isEditable, int language)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```content```: This parameter represents the content of the cell.
    * ```isEditable```: This parameter represents whether the cell is editable or not.
    * ```language```: This parameter represents the language of the cell.<br><br>
* ```public Cell(WordPair content, boolean isEditable, int language, boolean isEmpty)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```content```: This parameter represents the content of the cell.
    * ```isEditable```: This parameter represents whether the cell is editable or not.
    * ```language```: This parameter represents the language of the cell.
    * ```isEmpty```: This parameter represents whether the cell is empty or not.<br><br>
* ```public Cell(Cell cell)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```cell```: This parameter represents the cell to copy.<br><br>
* ```public Cell(WordPair content)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```content```: This parameter represents the content of the cell.<br><br>
* ```public Cell(int language)```: <br> This method is the constructor of the class. It takes the following parameters:
    * ```language```: This parameter represents the language of the cell.<br><br>
* ```public Cell()```: <br> This method is the constructor of the class.<br><br>
* ```public WordPair getContent()```: <br> This method returns the content of the cell.<br><br>
* ```public void setContent(WordPair content)```: <br> This method sets the content of the cell.<br><br>
* ```public boolean isEmpty()```: <br> This method returns true if the cell is empty and false otherwise.<br><br>
* ```public boolean isEditable()```: <br> This method returns true if the cell is editable and false otherwise.<br><br>
* ```public void setEditable(boolean editable)```: <br> This method sets whether the cell is editable or not.<br><br>
* ```public int getLanguage()```: <br> This method returns the language of the cell.<br><br>
* ```public void setLanguage(int language)```: <br> This method sets the language of the cell.<br><br>
* ```public boolean isContentEqual(Cell cell) throws NullPointerException```: <br> This method returns true if the content of the given cell is equal to the content of the cell and false otherwise.<br><br>
* ```public boolean equals(Object o)```: <br> This method returns true if the given object is equal to the cell and false otherwise.<br><br>
* ```public void clear()```: <br> This method clears the cell.<br><br>
* ```public String toString()```: <br> This method returns the string representation of the cell.<br><br>