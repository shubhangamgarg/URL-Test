package com.example.urltest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


public class SearchResultAdapter extends ArrayAdapter<SearchResult> {
    Context context;
    int resource;
    ArrayList<SearchResult> list;
    public SearchResultAdapter(@NonNull Context context, int resource,@NonNull ArrayList<SearchResult> list) {
        super(context, resource,list);
        this.context = context;
        this.resource=  resource;
        this.list = list;

    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
         @SuppressLint({"ViewHolder", "InflateParams"}) View view = inflater.inflate(R.layout.card_view_book, null);
        TextView title;
        TextView publisher;
        title = view.findViewById(R.id.title);
        publisher = view.findViewById(R.id.publisher);


        SearchResult s = new SearchResult();
               s =  list.get(position);
               if(s==null)
               {
                   Log.v("BOOK_LIST",position+" is null");
               }
        title.setText(s.getBook_tile());
               title.setTextColor(Color.rgb(150,130,100));
               if(s.getPublisher().isEmpty())
               {
                   publisher.setText("--NA--");
               }
        publisher.setText(s.getPublisher());
        return view;
    }
}
