package com.dbuzin.hashcalculator.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dbuzin.tablayoutsample.R;
import com.dbuzin.hashcalculator.presenters.PasswordGeneratorPresenter;
import com.dbuzin.hashcalculator.views.PasswordGeneratorView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PasswordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PasswordFragment extends MvpAppCompatFragment implements PasswordGeneratorView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public PasswordFragment() {
        // Required empty public constructor
    }

    public static PasswordFragment newInstance(String param1, String param2) {
        PasswordFragment fragment = new PasswordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    PasswordGeneratorPresenter passwordGeneratorPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        final Button generateButton = (Button) view.findViewById(R.id.generateButton);
        final Button copyButton = (Button) view.findViewById(R.id.copyButton);
        final EditText passwordLength = (EditText) view.findViewById(R.id.inputLength);
        final TextView outputTextView = (TextView) view.findViewById(R.id.outputTextView);
        final SwitchCompat useLower = (SwitchCompat) view.findViewById(R.id.switch_lower);
        final SwitchCompat useUpper = (SwitchCompat) view.findViewById(R.id.switch_upper);
        final SwitchCompat useNums = (SwitchCompat) view.findViewById(R.id.switch_nums);
        final SwitchCompat useSpecials = (SwitchCompat) view.findViewById(R.id.switch_special);

        //Валидация ввода
        passwordLength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                generateButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().matches("\\d+")){ //Проверка строки на использование только цифр
                    generateButton.setEnabled(false);
                    passwordLength.setError("The field can only contain digits");
                }
            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    outputTextView.setText(passwordGeneratorPresenter.generateOnClick(Integer.valueOf(passwordLength.getText().toString()),
                            useLower.isChecked(), useUpper.isChecked(), useNums.isChecked(), useSpecials.isChecked()));
                }
                catch (NumberFormatException nfe){
                    Log.d("Generation", "Choose at least one parameter");
                }
            }
        });

        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwordGeneratorPresenter.copyOnClick(outputTextView.getText().toString(), PasswordFragment.super.getContext());
            }
        });
    }

    @Override
    public void showError(String error) {
        Toast.makeText(super.getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCopied(String data) {
        Toast.makeText(super.getContext(), data, Toast.LENGTH_LONG).show();
    }
}