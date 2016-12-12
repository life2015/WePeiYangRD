package com.twtstudio.retrox.wepeiyangrd.home.tools;

import android.os.Bundle;

import com.twtstudio.retrox.wepeiyangrd.base.BaseFragment;

/**
 * Created by retrox on 2016/12/12.
 */

public class ToolsFragment extends BaseFragment {

    public static ToolsFragment newInstance() {

        Bundle args = new Bundle();

        ToolsFragment fragment = new ToolsFragment();
        fragment.setArguments(args);
        return fragment;
    }

}
