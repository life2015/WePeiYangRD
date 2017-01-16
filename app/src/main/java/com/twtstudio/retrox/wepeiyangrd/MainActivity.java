package com.twtstudio.retrox.wepeiyangrd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.twtstudio.retrox.wepeiyangrd.api.ApiClient;
import com.twtstudio.retrox.wepeiyangrd.auth.login.LoginViewModel;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;
import com.twtstudio.retrox.wepeiyangrd.base.BaseFragment;
import com.twtstudio.retrox.wepeiyangrd.home.common.oneItem.OneInfoViewModel;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class MainActivity extends BaseActivity {

    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(v -> test1());
    }

    private void test() {
        ApiClient.getService().login("miss976885345", "JCYwin551100")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tokenApiResponse -> Logger.d(tokenApiResponse.getData().token));

    }

    private void test1(){
        BaseFragment fragment = new BaseFragment();
        OneInfoViewModel viewModel = new OneInfoViewModel(fragment);
    }

}
