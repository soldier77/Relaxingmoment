package com.example.player.relaxingmoment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by player on 2017/5/4.
 */

public class JokeAdapter extends ArrayAdapter<Joke> {
    private int resourceId;
    public JokeAdapter(Context context, int textViewResourceId, List<Joke>jokes){
        super(context,textViewResourceId,jokes);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Joke joke = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        TextView jokeContent = (TextView)view.findViewById(R.id.joke);
        jokeContent.setText(joke.getContent());
        return view;
    }
}
