/*
* Description: This dialog fragment is used to allow the user to choose the size of the puzzle they want to play.
* Currently, the user can choose between 4x4, 6x6, 9x9, and 12x12 puzzles.
* */

package com.echo.wordsudoku.ui.dialogs;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.echo.wordsudoku.R;

public class ChoosePuzzleSizeFragment extends DialogFragment {


    public interface OnPuzzleSizeSelectedListener{
        void onPuzzleSizeSelected(int size);
    }

    private int mPuzzleSize = 9;

    private OnPuzzleSizeSelectedListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(listener!=null) return;
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            listener = (OnPuzzleSizeSelectedListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(context
                    + " must implement NoticeDialogListener");
        }
    }

    public static String TAG = "ChoosePuzzleSizeFragment";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_choose_puzzle_size_dialog, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        RadioGroup radioGroup = view.findViewById(R.id.size_radio_group);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.choose_4x4_button:
                    mPuzzleSize = 4;
                    break;
                case R.id.choose_6x6_button:
                    mPuzzleSize = 6;
                    break;
                case R.id.choose_12x12_puzzle:
                    mPuzzleSize = 12;
                    break;
                default:
                    mPuzzleSize = 9;
                    break;
            }
        });


        Button doneButton = view.findViewById(R.id.done_button);
        Button cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(v -> dismiss());


        doneButton.setOnClickListener(v -> {
            listener.onPuzzleSizeSelected(mPuzzleSize);
            dismiss();
        });

    }
}
