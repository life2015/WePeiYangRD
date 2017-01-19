package com.twtstudio.retrox.wepeiyangrd.module.gpa;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.messenger.Messenger;
import com.orhanobut.logger.Logger;
import com.twtstudio.retrox.wepeiyangrd.api.ApiClient;
import com.twtstudio.retrox.wepeiyangrd.api.ApiResponse;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by retrox on 2017/1/17.
 */

public class GpaViewModel implements ViewModel {

    //绑定生命周期用
    private BaseActivity mActivity;

    public void getData(){
        ApiClient.getService()
                .getGpa()
                .subscribeOn(Schedulers.io())
                .map(ApiResponse::getData)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(gpaBean -> {
                    Logger.d(gpaBean);
                },Throwable::printStackTrace);

        Messenger.getDefault().sendNoMsg("TOken");

    }

    public GpaViewModel(BaseActivity activity) {
        mActivity = activity;
    }
}
