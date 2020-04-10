package com.example.urltest;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.example.urltest.EventBus.showTitleEvent;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;


public class Search extends AsyncTask<Void,Void,Document> {
    String key;
    String url;
    Document doc;
    Context context;
    String column;
    ProgressDialog dialog;
    ArrayList<Links>link;
    boolean connected = false;

    public Search(Context context, String key, String column)
    {
        this.context = context;
        this.key = key;
        this.column = column;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Searching...");
        dialog.setCancelable(true);
        link = new ArrayList<>();

    }
    public boolean getWebPage  = false;
    private void prepareUrl()
    {
        switch (column)
        {
            case "Default":
                column = "def";
                break;
            case "Author":
                column = "author";
                break;
            case  "ISBN":
                column = "identifier";
                break;
            case "Publisher":
                column = "publisher";
                break;
            case "Series":
                column = "series";
                break;
            case "Title":
                column = "title";
                break;
            default: column = "def";
        }
        key = key.replace(' ','+');
        url = "https://libgen.is/search.php?req="+key+"&open=0&res=100&view=simple&phrase=1&column="+column;


    }
    public Document getDocument()
    {
        if(doc!=null)
        return doc;
       else
           return null;

    }


    @Override
    protected Document doInBackground(Void... voids) {
        if(connected) {
            prepareUrl();
            Log.v("URL", url);
            try {
                doc = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
            return doc;
        }
        else
        {
            return null;
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
        InetAddress ip = null;

        try {
            ip = InetAddress.getByName("www.libgen.is");
            if(ip!=null)
            {
                connected = true;
                Log.v("CONN", "Connected to " + ip);
            }

        } catch (IOException e) {
            e.printStackTrace();
            Log.v("CONN", "HOST_RES_ERR : " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);

        if(document!=null)
        {
            Elements tables = document.select("table");
            String no_results = tables.get(1).select("tbody").select("tr").select("td").first().text().trim();
            String[] result = no_results.split(" ");
            if(result[0].equals("0"))
            {
                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("No results found.Try with some different search keywords ").setTitle("Search Result");
                builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.dismiss();
                builder.show();
            }
            else {

                PrepareSearchResult prepareSearchResult = new PrepareSearchResult(tables);
                prepareSearchResult.loadBooks();

                SearchResultAdapter adapter = new SearchResultAdapter(context,prepareSearchResult.list);
                MainActivity.searchList.setAdapter(adapter);
                MainActivity.links = prepareSearchResult.links;
                dialog.dismiss();
                try {
                    prepareSearchResult.finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
            EventBus.getDefault().postSticky(new showTitleEvent(true));
        }
        else
        {
            dialog.dismiss();
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Some error occurred, please try after some time & make sure that you have an active internet connection. ").setTitle("Connectivity");
            builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();


        }

    }
}
