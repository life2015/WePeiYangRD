package com.twtstudio.retrox.wepeiyangrd.api;

import com.twtstudio.retrox.wepeiyangrd.auth.login.Token;
import com.twtstudio.retrox.wepeiyangrd.home.common.oneItem.OneInfoBean;
import com.twtstudio.retrox.wepeiyangrd.module.gpa.GpaBean;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by retrox on 2016/11/25.
 */

public interface Api {

    /**
     * 获取微北洋token
     * @param twtuname 用户名
     * @param twtpasswd 密码
     * @return
     */
    @GET("auth/token/get")
    Observable<ApiResponse<Token>> login(@Query("twtuname") String twtuname, @Query("twtpasswd") String twtpasswd);

    @GET("gpa")
    Observable<ApiResponse<GpaBean>> getGpa();

    @GET("http://rest.wufazhuce.com/OneForWeb/one/getHpinfo")
    Observable<OneInfoBean> getOneHpInfo(@Query("strDate") String date);
}
