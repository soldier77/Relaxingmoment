package com.example.player.relaxingmoment;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private String address = "http://japi.juhe.cn/joke/content/list.from?" +
            "key=c529b2d58df8a95543215f50eb2fbc69&page=1&pagesize=20&sort=asc&time=1418745237";
    private List<Joke>jokes = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = (Button)findViewById(R.id.refurbish);
        listView = (ListView)findViewById(R.id.listview);
        sendRequestAndDisponse();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendRequestAndDisponse();
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                Joke joke = jokes.get(position);
                ClipboardManager text =(ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                text.setText(joke.getContent());
                Toast.makeText(MainActivity.this,"文本内容已复制",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
    private void sendRequestAndDisponse(){
        OkhttpConnection.sendOkhttpRequest(address,new okhttp3.Callback(){
            @Override
            public void onResponse(Call call, Response response)throws IOException{
                String responseData = response.body().string();
                responseData = responseData.substring(responseData.indexOf('['),responseData.lastIndexOf(']')+1);
                Gson gson = new Gson();
                jokes = gson.fromJson(responseData,new TypeToken<List<Joke>>(){}.getType());
                for (Joke jk:jokes){
                    Log.d("content",jk.getContent());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JokeAdapter jokeAdapter = new JokeAdapter(MainActivity.this,
                                R.layout.joke_layout,jokes);
                        listView.setAdapter(jokeAdapter);
                    }
                });
            }
            @Override
            public void onFailure(Call call,IOException e){
                e.printStackTrace();
            }
        });
    }

}
