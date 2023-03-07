package com.echo.wordsudoku.ui;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Set;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> mPuzzleLanguage = new MutableLiveData<>();
    private boolean timer;
    private boolean uiImmersion;
    private int difficulty;
    public LiveData<Integer> getPuzzleLanguage() {
        return mPuzzleLanguage;
    }
    public void setPuzzleLanguage(int language) {
        mPuzzleLanguage.setValue(language);
    }

    public void setUiImmersion(boolean UiImmersion) {
        this.uiImmersion = UiImmersion;
    }

    public boolean isUiImmersion() {
        return uiImmersion;
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





    @Override
    public void onCleared() {
        Log.d("MYTEST", "ViewModel has been destroyed");
    }

    public SettingsViewModel() {
        Log.d("MYTEST", "ViewModel has been created");
    }

}
