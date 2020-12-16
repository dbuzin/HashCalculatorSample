package com.dbuzin.hashcalculator.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dbuzin.hashcalculator.utils.CalculateHash;
import com.dbuzin.hashcalculator.utils.SaveToFile;
import com.dbuzin.hashcalculator.views.CalculatorView;
import com.dbuzin.tablayoutsample.R;

import java.io.IOException;

import static androidx.core.content.ContextCompat.getSystemService;

@InjectViewState
public class CalculatorPresenter extends MvpPresenter<CalculatorView> {
    public String calculateOnClick(String raw, String algorithm){
        return (String) CalculateHash.encode(raw, algorithm);
    }
    public void saveToFileOnClick(String value, String name, Context context) throws IOException {
        boolean result = SaveToFile.saveToFile(value, name, context);
        if(result){
            getViewState().onSaving(name);
        }
        else{
            getViewState().onSavingError(R.string.saving_error);
        }
    }
    public void copyOnClick(String data, Context context){
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(context, ClipboardManager.class);
        ClipData copiedData = ClipData.newPlainText("Copied password", data);
        clipboardManager.setPrimaryClip(copiedData);
        getViewState().showCopied("Copied hash: " + data);
    }
}
