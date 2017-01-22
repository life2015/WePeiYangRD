package com.twtstudio.retrox.wepeiyangrd.module.gpa;

import android.databinding.ObservableField;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.messenger.Messenger;
import com.orhanobut.logger.Logger;
import com.twtstudio.retrox.wepeiyangrd.api.ApiClient;
import com.twtstudio.retrox.wepeiyangrd.api.ApiErrorHandler;
import com.twtstudio.retrox.wepeiyangrd.api.ApiResponse;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;

import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by retrox on 2017/1/17.
 */

public class GpaViewModel implements ViewModel {

    public static final String TOKEN_GPA_LOAD_FINISHED = "token_gpa_load_finished";

    //绑定生命周期用
    private BaseActivity mActivity;

    //public final ObservableField<GpaBean> obGpaBean = new ObservableField<>();

    public void getData() {
        Observable<Notification<GpaBean>> gpaObservable =
                ApiClient.getService()
                        .getGpa()
                        .subscribeOn(Schedulers.io())
                        .compose(mActivity.bindToLifecycle())
                        .map(ApiResponse::getData)
                        .materialize().share();

        gpaObservable.filter(Notification::isOnNext)
                .map(Notification::getValue)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gpaBean -> {
                    //obGpaBean.set(gpaBean);
                    Messenger.getDefault().send(gpaBean, TOKEN_GPA_LOAD_FINISHED);
                });

        ApiErrorHandler handler = new ApiErrorHandler(mActivity);

        handler.handleError(gpaObservable.filter(Notification::isOnError)
                .map(Notification::getThrowable));

    }

    public GpaViewModel(BaseActivity activity) {
        mActivity = activity;
    }

}
