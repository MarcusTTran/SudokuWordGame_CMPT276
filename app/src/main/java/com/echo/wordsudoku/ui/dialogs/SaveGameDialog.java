package com.echo.wordsudoku.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.echo.wordsudoku.R;

public class SaveGameDialog extends DialogFragment {


    public interface SaveGameDialogListener {
        void onSaveDialogYes();
        void onSaveDialogNo();
    }

    SaveGameDialogListener listener;

    public static String TAG = "SaveGameDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        try {
            listener = (SaveGameDialogListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement SaveGameDialogListener");
        }
        return new AlertDialog.Builder(requireContext()).setMessage(getString(R.string.save_game_dialog_title)).setPositiveButton(getString(R.string.yes_button), (dialog, which) -> {
            listener.onSaveDialogYes();
            //Save the game
        }).setNegativeButton(getString(R.string.no_button), (dialog, which) -> {
            listener.onSaveDialogNo();
        }).setNeutralButton(R.string.cancel_button,((dialog, which) -> {
            dismiss();
        })).create();
    }
}
