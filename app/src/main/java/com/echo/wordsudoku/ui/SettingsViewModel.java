package com.echo.wordsudoku.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {
    private final MutableLiveData<Integer> mPuzzleLanguage = new MutableLiveData<>();
    public LiveData<Integer> getPuzzleLanguage() {
        return mPuzzleLanguage;
    }
    public void setPuzzleLanguage(int language) {
        mPuzzleLanguage.setValue(language);
    }
}
