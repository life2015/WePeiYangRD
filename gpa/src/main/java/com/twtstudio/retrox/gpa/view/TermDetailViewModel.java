package com.twtstudio.retrox.gpa.view;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;

import com.kelin.mvvmlight.base.ViewModel;
import com.twtstudio.retrox.gpa.GpaBean;

/**
 * Created by retrox on 2017/1/28.
 */

public class TermDetailViewModel implements ViewModel {

    public final ObservableField<GpaBean.Term.TermStat> observableTermStat = new ObservableField<>();

    /**
     * bind a LinearLayout gg
     */
    public final ObservableArrayList<GpaBean.Term.Course> mObservableCourseList = new ObservableArrayList<>();



    public TermDetailViewModel(GpaBean.Term term) {
        observableTermStat.set(term.stat);
        mObservableCourseList.addAll(term.data);
    }
}
