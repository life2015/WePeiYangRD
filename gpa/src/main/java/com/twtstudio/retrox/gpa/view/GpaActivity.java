package com.twtstudio.retrox.gpa.view;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.twt.wepeiyang.commons.base.CommonBaseActivity;
import com.twtstudio.retrox.gpa.GpaProvider;
import com.twtstudio.retrox.gpa.R;
import com.twtstudio.retrox.gpa.databinding.GpaActivityMainBinding;
import com.twtstudio.retrox.gpa.databinding.GpaItemTermBinding;

/**
 * Created by retrox on 2017/1/28.
 */

public class GpaActivity extends CommonBaseActivity {

    private GpaActivityMainBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        GpaItemTermBinding binding = DataBindingUtil.setContentView(this,R.layout.gpa_item_term);
//        GpaProvider.init(this)
//                .registerAction(gpaBean -> {
//                    binding.setViewModel(new TermDetailViewModel(gpaBean.data.get(2)));
//                })
//                .getData();

        mBinding = DataBindingUtil.setContentView(this,R.layout.gpa_activity_main);
        setSupportActionBar(mBinding.toolbar);
        GpaActivityViewModel viewModel = new GpaActivityViewModel(this);
        mBinding.setViewModel(viewModel);
        viewModel.getGpaData();

    }


}
