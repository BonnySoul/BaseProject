package com.soul.bonny.baselibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.trello.rxlifecycle2.components.RxActivity;
import com.trello.rxlifecycle2.components.RxFragment;

/**
 * Created by so on 2018/8/2.
 */
public class BaseFragment<T extends BasePresenter> extends RxFragment implements BaseContract.BaseView{

    protected T mPresenter;

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showSuccess() {

    }

    @Override
    public void showFailed() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        detachView();
    }

    /**
     * 分离view
     */
    private void detachView() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
