package com.dbuzin.hashcalculator.views;

import com.arellomobile.mvp.MvpView;

public interface CalculatorView extends MvpView {
    void onSaving(String name);
    void onSavingError(int error);
    void showCopied(String data);
}
