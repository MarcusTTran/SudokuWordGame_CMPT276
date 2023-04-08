/*
* Description: This class is the ViewModel for the SettingsFragment. It is used to store the settings for the app.
* Settings include the language of the puzzle, the difficulty of the puzzle, whether the timer is enabled, whether the app should automatically save the game and whether the app should use Listening Comprehension Mode.
*  */

package com.echo.wordsudoku.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> mPuzzleLanguage = new MutableLiveData<>();
    private final MutableLiveData<Integer> mButtonInputLanguage = new MutableLiveData<>();
    private boolean timer;

    boolean textToSpeech;
    private boolean autoSave;
    private int difficulty;



    public LiveData<Integer> getPuzzleLanguage() {
        return mPuzzleLanguage;
    }
    public void setPuzzleLanguage(int language) {
        mPuzzleLanguage.setValue(language);
    }

    public LiveData<Integer> getButtonInputLanguage() {
        return mButtonInputLanguage;
    }
    public void setButtonInputLanguage(int language) {
        mButtonInputLanguage.setValue(language);
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setTimer(boolean timer) {
        this.timer = timer;
    }

    public void setTextToSpeech(boolean speech) { this.textToSpeech = speech; }

    public boolean getTextToSpeech() { return this.textToSpeech; }

    public boolean isTimer() {
        return timer;
    }

    public void setAutoSave(boolean autoSave) {
        this.autoSave = autoSave;
    }

    public boolean isAutoSave() {
        return autoSave;
    }

    @Override
    public void onCleared() {
//        Log.d("MYTEST", "ViewModel has been destroyed");
    }

    public SettingsViewModel() {
//        Log.d("MYTEST", "ViewModel has been created");
    }

}
