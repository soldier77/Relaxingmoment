package com.example.player.relaxingmoment;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by player on 2017/5/4.
 */

public class OkhttpConnection {
    public static void sendOkhttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
