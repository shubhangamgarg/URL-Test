package com.example.urltest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ResultViewHolder> {
    Context context;
    int resource;
    ArrayList<SearchResult> list;
    public SearchResultAdapter(Context context, ArrayList list)
    {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_view_book,parent,false);
        return new ResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        String title = list.get(position).getBook_tile();
        String publisher = list.get(position).getPublisher();
        holder.publisher.setText(publisher);
        holder.title.setText(title);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ResultViewHolder extends  RecyclerView.ViewHolder implements ViewGroup.OnClickListener
    {
        TextView title, publisher;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            publisher = itemView.findViewById(R.id.publisher);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position= getAdapterPosition();
            Intent i =new Intent(context,LoadBookDetail.class);
            i.putExtra("link",MainActivity.links.get(position).getLink());
            context.startActivity(i);

        }
    }
}
