package com.bw.framwork.base;

public interface IView {
    void showLoading();

    void hideLoading();

    void showError(String code, String msg);
}
