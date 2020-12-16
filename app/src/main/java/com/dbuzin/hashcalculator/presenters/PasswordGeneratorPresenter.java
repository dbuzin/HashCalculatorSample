package com.dbuzin.hashcalculator.presenters;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dbuzin.hashcalculator.utils.RandomPassword;
import com.dbuzin.hashcalculator.views.PasswordGeneratorView;

import static androidx.core.content.ContextCompat.getSystemService;

@InjectViewState
public class PasswordGeneratorPresenter extends MvpPresenter<PasswordGeneratorView> {
    private RandomPassword randomPassword;

    public String generateOnClick(int length, boolean useLower, boolean useUpper, boolean useNums, boolean useSpecials) {
        RandomPassword.RandomPasswordBuilder randomPasswordBuilder = new RandomPassword.RandomPasswordBuilder();
        randomPasswordBuilder
                .useLower(useLower)
                .useUpper(useUpper)
                .useNums(useNums)
                .useSpecial(useSpecials);
        randomPassword = randomPasswordBuilder.build();
        if(length > 0)
            return randomPassword.generatePassword(length);
        else
            getViewState().showError("Length have to be greater than 0");
            return "";
    }
    public void copyOnClick(String data, Context context){
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(context, ClipboardManager.class);
        ClipData copiedData = ClipData.newPlainText("Copied password", data);
        clipboardManager.setPrimaryClip(copiedData);
        getViewState().showCopied("Copied password: " + data);
    }
}
