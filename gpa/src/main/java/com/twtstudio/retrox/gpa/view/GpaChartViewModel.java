package com.twtstudio.retrox.gpa.view;

import android.databinding.ObservableField;

import com.kelin.mvvmlight.base.ViewModel;
import com.twtstudio.retrox.gpa.GpaBean;

/**
 * Created by retrox on 2017/1/28.
 */

public class GpaChartViewModel implements ViewModel {

    public final ObservableField<GpaBean> observableGpaBean = new ObservableField<>();

    public GpaChartViewModel(GpaBean gpaBean) {
        observableGpaBean.set(gpaBean);
    }

    /**
     * @deprecated
     */
    public GpaChartViewModel() {
    }
}
