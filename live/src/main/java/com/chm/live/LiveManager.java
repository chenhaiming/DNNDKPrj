package com.chm.live;

import com.chm.live.list.LiveList;
import com.chm.live.room.Room;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Flowable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ASUS on 2018/11/14.
 */

public class LiveManager {


    private final LiveService liveService;

    private static LiveManager liveManager;

    private LiveManager() {

        OkHttpClient callClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());
                LiveList liveList = new Gson().fromJson(response.body().toString(), LiveList.class);
                return response;
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.m.panda.tv/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        liveService = retrofit.create(LiveService.class);
    }

    public static LiveManager getInstance() {
        if (liveManager == null) {
            synchronized (LiveManager.class) {
                if (liveManager == null)
                    liveManager = new LiveManager();
            }
        }
        return liveManager;
    }


    public Flowable<LiveList> getLiveList(String cat){
        return liveService.getLiveList(cat, 1, 10, "3.3.1.5978");
    }

    public Flowable<Room> getLiveRoom(String id){
        return liveService.getLiveRoom(id, "3.3.1.5978", 1, "json", "android");
    }
}
