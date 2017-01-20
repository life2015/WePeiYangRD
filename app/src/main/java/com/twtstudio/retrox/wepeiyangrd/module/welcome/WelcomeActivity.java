package com.twtstudio.retrox.wepeiyangrd.module.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twtstudio.retrox.wepeiyangrd.auth.login.LoginActivity;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;
import com.twtstudio.retrox.wepeiyangrd.home.HomeActivity;
import com.twtstudio.retrox.wepeiyangrd.support.HawkUtil;

/**
 * Created by retrox on 2017/1/20.
 */

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isLogin = HawkUtil.getIsLogin();
        if (isLogin){
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        finish();
    }
}
