package com.twtstudio.retrox.wepeiyangrd.auth.login;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twtstudio.retrox.wepeiyangrd.R;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;

/**
 * Created by retrox on 2016/11/27.
 */

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LoginViewModel viewModel = new LoginViewModel(this);
        viewModel.twtuName.set("miss976885345");
        viewModel.twtpasswd.set("JCYwin551100");
        viewModel.onLoginClickCommand.execute();
    }
}
