package com.soul.bonny.baselibrary;

import com.example.a94390.httpsdemo.http.callback.BaseCallback;

/**
 * Created by so on 2018/7/31.
 */
public class BasePresenter<T extends BaseContract.BaseView> implements BaseContract.BasePresenter{

    protected T mView;


    @Override
    public void attachView(BaseContract.BaseView view) {
        mView= (T) view;
    }

    @Override
    public void detachView() {
        if (mView != null) {
            mView = null;
        }
    }
}
