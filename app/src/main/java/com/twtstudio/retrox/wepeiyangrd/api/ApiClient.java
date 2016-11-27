package com.twtstudio.retrox.wepeiyangrd.api;


import com.twtstudio.retrox.wepeiyangrd.JniUtils;
import com.twtstudio.retrox.wepeiyangrd.support.PrefUtils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by retrox on 2016/11/25.
 */

public class ApiClient {

    private Retrofit mRetrofit;

    private Api mService;

    public ApiClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .retryOnConnectionFailure(false)
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://open.twtstudio.com/api/v1/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = mRetrofit.create(Api.class);
    }

    private static class SingletonHolder {
        private static final ApiClient INSTANCE = new ApiClient();
    }

    public static ApiClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static Api getService(){
        return SingletonHolder.INSTANCE.mService;
    }

    protected static Interceptor sRequestInterceptor = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request originRequest = chain.request();

            HttpUrl newUrl = convert(originRequest.url());

            Request.Builder builder = originRequest.newBuilder()
//                    .addHeader("User-Agent", UserAgent.generate())
                    .addHeader("Authorization", PrefUtils.getToken())
                    .url(newUrl);


            return chain.proceed(builder.build());
        }

        protected HttpUrl convert(HttpUrl originUrl) {
            Set<String> keys = originUrl.queryParameterNames();
            //获得的set无法被编辑
            //keys.add("t");
            String timestamp = String.valueOf(Calendar.getInstance().getTimeInMillis());

            List<String> keysList = new ArrayList<>(keys);
            //在这里添加
            keysList.add("t");
            Collections.sort(keysList);

            StringBuilder builder = new StringBuilder();
            builder.append(JniUtils.getInstance().getAppKey());
            for (String key : keysList) {
                if ("t".equals(key)) {
                    builder.append(key).append(timestamp);
                } else {
                    builder.append(key).append(originUrl.queryParameter(key));
                }
            }
            builder.append(JniUtils.getInstance().getAppSecret());
            String sign = new String(Hex.encodeHex(DigestUtils.sha(builder.toString()))).toUpperCase();

            return originUrl.newBuilder()
                    .addQueryParameter("t", timestamp)
                    .addQueryParameter("sign", sign)
                    .addQueryParameter("app_key", JniUtils.getInstance().getAppKey())
                    .build();
        }

    };
}
