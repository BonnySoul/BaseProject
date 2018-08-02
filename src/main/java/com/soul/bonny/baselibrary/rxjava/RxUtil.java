package com.soul.bonny.baselibrary.rxjava;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.internal.functions.Functions;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by so on 2018/8/2.
 */
public final class RxUtil {
    public static final ObservableTransformer THREAD_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
        }
    };
    public static final ObservableTransformer THREAD_ON_UI_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.newThread()).unsubscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        }
    };
    public static final ObservableTransformer IO_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(Schedulers.io());
        }
    };
    public static final ObservableTransformer IO_ON_UI_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };
    public static final ObservableTransformer COMPUTATION_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.computation()).unsubscribeOn(Schedulers.newThread()).observeOn(Schedulers.newThread());
        }
    };
    public static final ObservableTransformer COMPUTATION_ON_UI_TRANSFORMER = new ObservableTransformer() {
        public ObservableSource apply(Observable upstream) {
            return upstream.subscribeOn(Schedulers.computation()).unsubscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        }
    };

    private RxUtil() {
    }

    public static <T> ObservableTransformer<T, T> applySchedulers(ObservableTransformer transformer) {
        return transformer;
    }

    public static Disposable time(long delay, @NonNull Consumer<Long> onNext) {
        return time(delay, TimeUnit.MILLISECONDS, onNext);
    }

    public static Disposable time(long delay, TimeUnit unit, @NonNull Consumer onNext) {
        return Observable.timer(delay, unit).compose(applySchedulers(COMPUTATION_TRANSFORMER)).subscribe(onNext);
    }

    public static Disposable timeOnUI(long delay, TimeUnit unit, @NonNull Consumer onNext) {
        return Observable.timer(delay, unit).compose(applySchedulers(COMPUTATION_ON_UI_TRANSFORMER)).subscribe(onNext);
    }

    public static Disposable interval(long interval, @NonNull Consumer<Long> onNext) {
        return interval(interval, TimeUnit.MILLISECONDS, onNext);
    }

    public static Disposable interval(long interval, TimeUnit unit, @NonNull Consumer onNext) {
        return Observable.interval(interval, unit).compose(applySchedulers(COMPUTATION_TRANSFORMER)).subscribe(onNext);
    }

    public static Disposable run(@NonNull Action backgroundAction) {
        return Observable.empty().compose(applySchedulers(THREAD_TRANSFORMER)).subscribe(Functions.emptyConsumer(), Functions.ERROR_CONSUMER, backgroundAction);
    }

    public static <T> Disposable runOnUI(@NonNull ObservableOnSubscribe<T> backgroundSubscribe, @NonNull Consumer uiAction) {
        return Observable.create(backgroundSubscribe).compose(applySchedulers(THREAD_ON_UI_TRANSFORMER)).subscribe(uiAction);
    }
}
