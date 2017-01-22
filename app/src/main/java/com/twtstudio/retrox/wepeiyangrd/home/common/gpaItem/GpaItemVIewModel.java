package com.twtstudio.retrox.wepeiyangrd.home.common.gpaItem;

import android.content.Context;
import android.databinding.ObservableField;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.messenger.Messenger;
import com.twtstudio.retrox.wepeiyangrd.base.BaseActivity;
import com.twtstudio.retrox.wepeiyangrd.module.gpa.GpaBean;
import com.twtstudio.retrox.wepeiyangrd.module.gpa.GpaViewModel;

/**
 * Created by retrox on 2017/1/21.
 */

public class GpaItemViewModel implements ViewModel {

    private BaseActivity mContext;

    public final ObservableField<GpaBean> observableGpa = new ObservableField<>();

    public GpaItemViewModel(BaseActivity context) {
        mContext = context;
        init();
    }

    private void init() {
        Messenger.getDefault().register(mContext, GpaViewModel.TOKEN_GPA_LOAD_FINISHED,
                GpaBean.class, observableGpa::set);
        getData();
    }

    private void getData() {
        GpaViewModel gpaViewModel = new GpaViewModel(mContext);
        gpaViewModel.getData();
    }


}
