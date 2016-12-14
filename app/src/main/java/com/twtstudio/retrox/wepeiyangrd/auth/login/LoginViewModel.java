package com.twtstudio.retrox.wepeiyangrd.auth.login;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.widget.Toast;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.twtstudio.retrox.wepeiyangrd.api.ApiClient;
import com.twtstudio.retrox.wepeiyangrd.api.ApiResponse;
import com.twtstudio.retrox.wepeiyangrd.support.HawkUtil;
import com.twtstudio.retrox.wepeiyangrd.support.PrefUtils;

import rx.Notification;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by retrox on 2016/11/27.
 */

public class LoginViewModel implements ViewModel {

    //context
    private LoginActivity mActivity;

    //model
    public final ObservableField<String> twtuName = new ObservableField<>("用户名");
    public final ObservableField<String> twtpasswd = new ObservableField<>("密码");
    public Token mToken;

    //viewStyle
    public final ViewStyle mViewStyle = new ViewStyle();

    private static class ViewStyle {
        public final ObservableBoolean isProgressRefreshing = new ObservableBoolean(false);
    }

    //command
    public ReplyCommand onLoginClickCommand = new ReplyCommand(() -> {
        login();
    });

    public ReplyCommand onRegisterClickCommand = new ReplyCommand(() -> {
        // TODO: 2016/11/27 跳转到注册页面
    });

    private void login() {
        mViewStyle.isProgressRefreshing.set(true);

        Observable<Notification<ApiResponse<Token>>> wpyToken = ApiClient.getService()
                .login(twtuName.get(), twtpasswd.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(mActivity.bindToLifecycle())
                .materialize()
                .share();

        wpyToken.filter(Notification::isOnNext)
                .map(Notification::getValue)
                .map(ApiResponse::getData)
                .doAfterTerminate(() -> {
                    Toast.makeText(mActivity, "登陆成功", Toast.LENGTH_SHORT).show();
                    mViewStyle.isProgressRefreshing.set(false);
                    // TODO: 2016/11/27 jump to home page
                })
                .subscribe(token -> {
                    HawkUtil.setToken(token.token);
                    HawkUtil.setIsLogin(true);
                });
    }
}
