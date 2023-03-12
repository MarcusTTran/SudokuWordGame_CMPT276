package com.echo.wordsudoku.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> mPuzzleLanguage = new MutableLiveData<>();
    private boolean timer;

    private boolean autoSave;
    private int difficulty;



    public LiveData<Integer> getPuzzleLanguage() {
        return mPuzzleLanguage;
    }
    public void setPuzzleLanguage(int language) {
        mPuzzleLanguage.setValue(language);
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
