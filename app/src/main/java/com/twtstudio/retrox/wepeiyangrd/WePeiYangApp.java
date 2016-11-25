package com.twtstudio.retrox.wepeiyangrd;

import android.app.Application;
import android.content.Context;

/**
 * Created by retrox on 2016/11/25.
 */

public class WePeiYangApp extends Application {
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
