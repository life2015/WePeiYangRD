package com.twtstudio.retrox.wepeiyangrd.home.user;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.twtstudio.retrox.wepeiyangrd.R;
import com.twtstudio.retrox.wepeiyangrd.base.BaseFragment;
import com.twtstudio.retrox.wepeiyangrd.databinding.FragmentUserBinding;

/**
 * Created by retrox on 2016/12/12.
 */

public class UserFragment extends BaseFragment {

    public static UserFragment newInstance() {

        Bundle args = new Bundle();

        UserFragment fragment = new UserFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentUserBinding binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user, container, false);


    }
}
