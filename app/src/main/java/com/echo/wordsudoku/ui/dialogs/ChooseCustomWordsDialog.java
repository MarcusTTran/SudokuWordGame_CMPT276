package com.echo.wordsudoku.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ChooseCustomWordsDialog extends AppCompatDialogFragment {

    private static String param1 = "CCWORDS_TITLE";
    private static String param2 = "CCWORDS_MSG";

    private String title;
    private String msg;

    public static ChooseCustomWordsDialog newInstance(String msg, String title) {
        ChooseCustomWordsDialog ccWordsFragment = new ChooseCustomWordsDialog();
        Bundle args = new Bundle();
        args.putString(param1, title);
        args.putString(param2, msg);
        ccWordsFragment.setArguments(args);
        return ccWordsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(param1);
            msg = getArguments().getString(param2);
        } else {
            title = "Create title";
            msg = "Create message";
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder errorBuilder = new AlertDialog.Builder(getActivity());
        errorBuilder.setTitle(title).setMessage(msg).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        return errorBuilder.create();
    }
}
