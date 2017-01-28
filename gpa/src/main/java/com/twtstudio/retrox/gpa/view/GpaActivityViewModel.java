package com.twtstudio.retrox.gpa.view;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.kelin.mvvmlight.base.ViewModel;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.twtstudio.retrox.gpa.GpaBean;
import com.twtstudio.retrox.gpa.GpaProvider;

/**
 * Created by retrox on 2017/1/28.
 */

public class GpaActivityViewModel implements ViewModel {

    private RxAppCompatActivity mRxActivity;

    public final ObservableField<GpaBean> obGpaBean = new ObservableField<>();

    public final ObservableArrayList<ViewModel> mViewModels = new ObservableArrayList<>();



    public GpaActivityViewModel(RxAppCompatActivity rxActivity) {
        mRxActivity = rxActivity;
    }

    public void getGpaData(){
        GpaProvider.init(mRxActivity)
                .registerAction(obGpaBean::set)
                .getData();
    }


}
