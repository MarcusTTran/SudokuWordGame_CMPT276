/*
* Description: This class is used to display a warning dialog to the user.
*  */

package com.echo.wordsudoku.ui.dialogs;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.echo.wordsudoku.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WarningDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WarningDialog extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String HEADER_KEY = "headerKey";
    private static final String BODY_KEY = "bodyKey";

    private static final String DISMISS_KEY = "dismissKey";

    // TODO: Rename and change types of parameters
    private String header;
    private String body;

    private String dismissMsg;

    public WarningDialog() {
        // Required empty public constructor
    }

    public static WarningDialog newInstance(String header, String body, String dismissMsg) {
        WarningDialog fragment = new WarningDialog();
        Bundle args = new Bundle();
        args.putString(WarningDialog.HEADER_KEY, header);
        args.putString(WarningDialog.BODY_KEY, body);
        args.putString(DISMISS_KEY, dismissMsg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            header = getArguments().getString(HEADER_KEY);
            body = getArguments().getString(BODY_KEY);
            dismissMsg = getArguments().getString(DISMISS_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_warning_dialog, container, false);
        TextView header = view1.findViewById(R.id.warningHeader);
        header.setText(this.header);

        TextView body = view1.findViewById(R.id.warningInformation);
        body.setText(this.body);

        TextView dismiss = view1.findViewById(R.id.warningDismiss);
        dismiss.setText(dismissMsg);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view1;
    }
}