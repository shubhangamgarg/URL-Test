package com.example.urltest;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.urltest.EventBus.showTitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText keyword;
    Button btn_search;
    String column;
    Spinner search_by;
    public static ListView searchList;
    public static ArrayList<Links>links;
    TextView publisher;
    TextView title;
    com.example.urltest.Connection connection;
    Search s;
    DataBaseHelper db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EventBus.getDefault().register(this);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        final Context context = MainActivity.this;
        keyword = findViewById(R.id.key);
        btn_search= findViewById(R.id.fetch);
        search_by = findViewById(R.id.search_by);
        searchList = findViewById(R.id.searchList);
        publisher= findViewById(R.id.publisher);
        title=findViewById(R.id.title);
        db = new DataBaseHelper(this,null,null,1);

        links = new ArrayList<>();
        final ArrayAdapter<CharSequence> search_list = ArrayAdapter.createFromResource(this,R.array.search_by,android.R.layout.simple_spinner_item);
        search_list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search_by.setAdapter(search_list);
        search_by.setOnItemSelectedListener(this);
        connection = new com.example.urltest.Connection(this);
        try {
            connection.connectivity();
        } catch (IOException e) {
            e.printStackTrace();
        }
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(keyword.getText().toString().length()<2)
                {

                    keyword.setError("Minimum 2 characters required.");
                    keyword.requestFocus();
                    return;
                }

                else
                {
                    if(column.equals("ISBN"))
                    {
                        if(keyword.getText().toString().length()<10)
                        {
                            keyword.setError("Short ISBN");
                            keyword.requestFocus();
                            return;
                        }
                    }
                    searchList.setAdapter(null);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
                    assert imm != null;
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                    s = new Search(MainActivity.this,keyword.getText().toString().trim(),column);
                                    s.execute();
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }


            }
        });
        searchList.setClickable(true);
        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getApplicationContext(),"Clicked "+position+" value",Toast.LENGTH_LONG).show();
                Intent i =new Intent(getApplicationContext(),LoadBookDetail.class);
                i.putExtra("link",links.get(position).getLink());
                startActivity(i);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        column = parent.getItemAtPosition(position).toString();
        if(column.equals("ISBN"))
        {
            keyword.setText("");
            keyword.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        column="Default";
    }

    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void showTitle(showTitleEvent event)
    {
        if (event.isSuccess())
        {
            title.setVisibility(View.VISIBLE);
            publisher.setVisibility(View.VISIBLE);
        }
//
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }


}


