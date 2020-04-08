package com.example.urltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;

public class LoadBookDetail extends AppCompatActivity {
    public static ImageView cover;
    public static TextView title;
    public static TextView year;
    public static TextView author;
    public static TextView publisher;
    public static TextView isbn;
    public static LinearLayout layout;
    public static String downloadLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_book_detail);
        layout = findViewById(R.id.book_view);
        layout.setVisibility(View.INVISIBLE);
        Intent i = getIntent();
        String link = i.getStringExtra("link");
        title = findViewById(R.id.title);
        cover= findViewById(R.id.cover);
        year = findViewById(R.id.year);
        author = findViewById(R.id.author);
        publisher = findViewById(R.id.publisher);
        isbn = findViewById(R.id.isbn);
        LoadBook l = new LoadBook(this,link);
        File directory = new File(Environment.getExternalStorageDirectory()+ File.separator+"images");
        directory.mkdirs();
        Log.v("FILE",Environment.getExternalStorageDirectory()+ File.separator+"images");
        l.execute();
    }
}
