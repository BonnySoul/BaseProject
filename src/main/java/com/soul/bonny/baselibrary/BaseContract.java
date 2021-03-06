package com.soul.bonny.baselibrary;


/**
 * @author wong
 */
public interface BaseContract {

    interface BasePresenter<T extends BaseContract.BaseView> {
        void attachView(T view);

        void detachView();
    }

    interface BaseView {
        /**
         * 显示进度中
         */
        void showLoading();

        /**
         * 隐藏进度
         */
        void hideLoading();

        /**
         * 显示请求成功
         */
        void showSuccess();

        /**
         * 失败重试
         */
        void showFailed();

        /**
         * 显示当前网络不可用
         */
        void showNoNet();

        /**
         * 重试
         */
        void onRetry();

        /**
         * 绑定生命周期
         *
         * @param <T>
         * @return
         */

    }
}
