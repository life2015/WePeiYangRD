package com.twtstudio.retrox.wepeiyangrd.home.user;

import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;

import com.kelin.mvvmlight.base.ViewModel;
import com.kelin.mvvmlight.command.ReplyCommand;
import com.twtstudio.retrox.wepeiyangrd.BR;
import com.twtstudio.retrox.wepeiyangrd.R;
import com.twtstudio.retrox.wepeiyangrd.support.HawkUtil;

/**
 * Created by retrox on 2017/1/14.
 */

public class PrefItemViewModel implements ViewModel {

    public static final int SETTINGS = 0;
    public static final int NIGHTMODE = 1;
    public static final int TJUOFFICAL = 2;


    private Context mContext;

    private int mMode = 0;

    public final ObservableBoolean preference = new ObservableBoolean(false);

    public final ObservableInt imageRes = new ObservableInt(0);

    public final ObservableField<String> title = new ObservableField<>();

    public ViewStyle viewStyle = new ViewStyle();

    private static class ViewStyle{
        public ObservableBoolean switchable = new ObservableBoolean(false);
    }

    public ReplyCommand clickCommand = new ReplyCommand(this::onClick);

    public PrefItemViewModel(Context context, int mode) {
        mContext = context;
        mMode = mode;
        init();
    }


    private void init(){

        int mode = mMode;
        if (mode == NIGHTMODE){
            imageRes.set(R.mipmap.ic_launcher);
            title.set("设置");

            viewStyle.switchable.set(true);
            preference.set(HawkUtil.getThemeMode());
        }else if (mode == TJUOFFICAL){
            // TODO: 2017/1/14 tju bind ?
        }else if (mode == SETTINGS){
            // TODO: 2017/1/14 jump to settings
        }

        preference.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                ObservableBoolean observableBoolean = (ObservableBoolean) observable;
                if (mMode == NIGHTMODE){
                    HawkUtil.setThemeMode(observableBoolean.get());
                }
            }
        });

    }

    private void onClick(){

    }

}
