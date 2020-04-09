package com.example.urltest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;

public class LoadBook extends AsyncTask<Void,Void,Document> {

    @SuppressLint("StaticFieldLeak")
    Context context;
    String link;
    ProgressDialog dialog ;
    Document doc;
    boolean  connected = false;
    Bitmap bitmap;
    InputStream input;
    String title,author,year,publisher,isbn;
    public LoadBook(Context context, String link){
        this.context = context;
        this.link = link;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Loading...");
        dialog.setCancelable(true);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog.show();
        InetAddress ip = null;
        byte[] add = {93, (byte) 174,95,29};

        try {
            ip = InetAddress.getByAddress(add);
            if(ip!=null)
            {
                connected = true;
            }
            Log.v("CONN", "Connected to " + ip);
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("CONN", "HOST_RES_ERR : " + e.getMessage());
        }
    }


    @Override
    protected Document doInBackground(Void... voids) {
        if(connected) {

            Log.v("URL", link);
            try {
                doc = Jsoup.connect(link).get();
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
    protected void onPostExecute(Document document) {
        super.onPostExecute(document);

        if(document!=null)
        {
            Element image = document.select("img").first();
            String url = image.absUrl("src");
            try {
                 input = new java.net.URL(url).openStream();
            } catch (IOException e) {
                e.printStackTrace();
                dialog.dismiss();
            }
            if(input!=null)
            {bitmap = BitmapFactory.decodeStream(input);
            LoadBookDetail.cover.setImageBitmap(bitmap);}
            dialog.dismiss();
            Element h1 =  document.selectFirst("h1");
            Elements p = document.select("p");
            LoadBookDetail.downloadLink = document.select("a").first().attr("href").trim();
            Log.v("DWN_LNK",LoadBookDetail.downloadLink);

            if(p.size()>4)
            {
                author = p.get(0).text();
                author = author.replace("Author(s): ","").trim();
                year = p.get(2).text().trim();
                String[] s = year.split("Year:");
                if(s[0].isEmpty())
                {
                    isbn = p.get(1).text().trim();
                    isbn= isbn.replace("ISBN:","").trim();
                    LoadBookDetail.author.setText(author);
                    LoadBookDetail.title.setText(h1.text().trim());
                    LoadBookDetail.isbn.setText(isbn);

                    LoadBookDetail.layout.setVisibility(View.VISIBLE);
                }
                else {
                    year = s[1].trim();
                    publisher = s[0].trim();
                    publisher = publisher.replace("Publisher:", "").trim();
                    publisher = publisher.replace(",", "");
                    isbn = p.get(3).text().trim();
                    isbn = isbn.replace("ISBN:", "").trim();
                    LoadBookDetail.author.setText(author);
                    LoadBookDetail.year.setText(year);
                    LoadBookDetail.title.setText(h1.text().trim());
                    LoadBookDetail.layout.setVisibility(View.VISIBLE);
                    LoadBookDetail.publisher.setText(publisher);
                    LoadBookDetail.isbn.setText(isbn);
                }
            }
            else {
                author = p.get(0).text();
                author = author.replace("Author(s): ", "").trim();
                year = p.get(1).text().trim();
                String[] s = year.split("Year:");
                if(s[0].isEmpty())
                {
                    isbn = p.get(1).text().trim();
                    isbn= isbn.replace("ISBN:","").trim();
                    LoadBookDetail.author.setText(author);
                    LoadBookDetail.title.setText(h1.text().trim());
                    LoadBookDetail.isbn.setText(isbn);
                    LoadBookDetail.layout.setVisibility(View.VISIBLE);
                }
                else {
                    year = s[1].trim();
                    publisher = s[0].trim();
                    publisher = publisher.replace("Publisher:", "").trim();
                    publisher = publisher.replace(",", "");
                    isbn = p.get(2).text().trim();
                    isbn = isbn.replace("ISBN:", "").trim();
                    LoadBookDetail.author.setText(author);
                    LoadBookDetail.year.setText(year);
                    LoadBookDetail.title.setText(h1.text().trim());
                    LoadBookDetail.layout.setVisibility(View.VISIBLE);
                    LoadBookDetail.publisher.setText(publisher);
                    LoadBookDetail.isbn.setText(isbn);
                }
            }

            }


        else
        {
            dialog.dismiss();
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("Some error occurred, please try after some time & make sure that you have an active internet connection.").setTitle("Connectivity");
            builder.setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            builder.show();


        }

    }

}