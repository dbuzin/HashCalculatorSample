package com.dbuzin.hashcalculator.views;

import com.arellomobile.mvp.MvpView;

public interface PasswordGeneratorView extends MvpView {
    void showError(String error);
    void showCopied(String data);
}
