package com.twtstudio.retrox.wepeiyangrd.home.common;

import android.os.Bundle;

import com.twtstudio.retrox.wepeiyangrd.base.BaseFragment;

/**
 * Created by retrox on 2016/12/12.
 */

public class CommonFragment extends BaseFragment {

    public static CommonFragment newInstance() {

        Bundle args = new Bundle();

        CommonFragment fragment = new CommonFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
