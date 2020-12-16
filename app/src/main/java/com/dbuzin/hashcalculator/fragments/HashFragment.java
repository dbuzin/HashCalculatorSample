package com.dbuzin.hashcalculator.fragments;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.dbuzin.tablayoutsample.R;
import com.dbuzin.hashcalculator.presenters.CalculatorPresenter;
import com.dbuzin.hashcalculator.utils.ListAlgorithms;
import com.dbuzin.hashcalculator.views.CalculatorView;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class HashFragment extends MvpAppCompatFragment implements CalculatorView {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HashFragment() {
        // Required empty public constructor
    }

    public static HashFragment newInstance(String param1, String param2) {
        HashFragment fragment = new HashFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @InjectPresenter
    CalculatorPresenter calculatorPresenter;

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
        return inflater.inflate(R.layout.fragment_hash, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        Button calculateBtn = (Button) view.findViewById(R.id.calculateBtn);
        Button savingBtn = (Button) view.findViewById(R.id.savingBtn);
        Button copyBtn = view.findViewById(R.id.copyButton);
        final EditText inputText = (EditText) view.findViewById(R.id.inputEditText);
        final TextInputEditText outputText = (TextInputEditText) view.findViewById(R.id.outputEditText);
        Spinner algorithmsSpinner = (Spinner) view.findViewById(R.id.algorithmSpinner);

        //Определение конктента выпадающего списка алгоритмов
        ArrayAdapter<String> mArrayAdapter = new ArrayAdapter<String>(super.getContext(), android.R.layout.simple_list_item_1, ListAlgorithms.values);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        algorithmsSpinner.setAdapter(mArrayAdapter);
        final String[] algorithm = new String[1];
        //Сохарнение выбранного алгоритма
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                algorithm[0] = (String)parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        algorithmsSpinner.setOnItemSelectedListener(onItemSelectedListener);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outputText.setText(calculatorPresenter.calculateOnClick(inputText.getText().toString(), algorithm[0]));
            }
        });

        //Создание и получение View для AlertDialog из xml-файла
        LayoutInflater li = LayoutInflater.from(super.getContext());
        View savingView = li.inflate(R.layout.saving_dialog_layout, null);
        //Создание Alert Dialog
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(savingView);
        //Установка текстового поля ввода в диалоге
        final EditText fileName = savingView.findViewById(R.id.fileNameText);
        //Сборка диалогового окна
        alertDialogBuilder
                .setCancelable(true)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (!TextUtils.isEmpty(fileName.getText().toString())) {
                            try {
                                //Сохарнение в файл
                                calculatorPresenter.saveToFileOnClick(outputText.getText().toString(), fileName.getText().toString(), HashFragment.super.getContext());
                                dialog.dismiss();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            Toast.makeText(HashFragment.super.getContext(), "Enter at least one character", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alertDialog = alertDialogBuilder.create();

        //Вызов AlertDialog по кнопке
        savingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.show();
            }
        });
        //Копирование в буффер
        copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculatorPresenter.copyOnClick(outputText.getText().toString(), HashFragment.this.getContext());
            }
        });
    }

    @Override
    public void onSaving(String name) {
        Toast.makeText(super.getContext(), "File " + name + " saved", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSavingError(int error) {
        Toast.makeText(super.getContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showCopied(String data) {
        Toast.makeText(super.getContext(), data, Toast.LENGTH_LONG).show();
    }
}