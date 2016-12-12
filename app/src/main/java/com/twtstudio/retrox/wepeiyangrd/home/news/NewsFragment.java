package com.twtstudio.retrox.wepeiyangrd.home.news;

import android.os.Bundle;

import com.twtstudio.retrox.wepeiyangrd.base.BaseFragment;

/**
 * Created by retrox on 2016/12/12.
 */

public class NewsFragment extends BaseFragment {

    public static NewsFragment newInstance() {

        Bundle args = new Bundle();

        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
